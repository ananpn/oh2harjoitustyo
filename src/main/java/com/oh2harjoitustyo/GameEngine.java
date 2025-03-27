package com.oh2harjoitustyo;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.*;

import static com.oh2harjoitustyo.Utils.maxEnergy;

public class GameEngine {
    private final SceneManager sceneManager;


    private final Pane gamePane; // The root pane
    private long lastUpdate = 0; // Last frame time in milliseconds
    private boolean isModifying = false; // To not modify lists at the same time in two different places

    private final List<Entity> enemies = new ArrayList<>();
    private final Player player;

    private double baseScore = 0;
    private SimpleStringProperty scoreTextProperty = new SimpleStringProperty();

    private AnimationTimer gameLoop;


    private SimpleDoubleProperty energy = new SimpleDoubleProperty(1);


    // Keeps track of currently pressed keys
    private final Set<KeyCode> pressedKeys = new HashSet<>();


    public GameEngine(Pane gamePane, SceneManager sceneManager) {
        this.player = new Player();
        this.gamePane = gamePane;
        this.gamePane.getChildren().add(player.shape);
        this.sceneManager = sceneManager;
        Text scoreText = new Text("");
        scoreText.textProperty().bind(scoreTextProperty);
        scoreText.setTranslateY(40-Utils.screenHeight/2);
        gamePane.getChildren().add(scoreText);

        ProgressBar energyBar = new ProgressBar();
        energyBar.progressProperty().bind(energy);
        energyBar.setVisible(true);
        energyBar.setTranslateY(-90+Utils.screenHeight/2);
        gamePane.getChildren().add(energyBar);
    }


    public void addEntity(Entity entity) {
        enemies.add(entity);
        gamePane.getChildren().add(entity.getShape()); // Add to pane
    }

    public void start(Scene scene) {
        Random random = new Random();
        scene.setOnKeyPressed(event -> {
            pressedKeys.add(event.getCode());
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.remove(event.getCode());
        });

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (random.nextDouble() > 0.99-calculateDifficultyCoefficient() && !isModifying) {
                    isModifying = true;
                    addEntity(new Pallo(
                        40 + 50*random.nextDouble(),
                        gamePane.getHeight()*(random.nextDouble()-0.5), 400+500*random.nextDouble())
                    );
                    isModifying = false;

                }
                else if (random.nextDouble() > 0.999 && !isModifying) {
                    isModifying = true;
                    removeOldEnemies();
                    isModifying = false;
                }

                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0; // Convert ns to seconds
                lastUpdate = now;
                baseScore += deltaTime;
                scoreTextProperty.set(String.valueOf(ScoreSerialized.baseScoreToActualScore(baseScore)));
                updatePlayerMovement(deltaTime);
                updateEnemiesMovement(deltaTime);
            }
        };
        gameLoop.start();
    }

    protected void updatePlayerMovement(double deltaTime) {
        double moveAmount = player.speed * deltaTime;
        if (pressedKeys.contains(KeyCode.SHIFT) && energy.get() > 0) {
            player.setSpeed(Utils.playerSpeedSmall);
            player.size.setValue(Utils.playerSizeSmall);
            energy.set(Math.max(energy.getValue() - moveAmount/maxEnergy, 0));
        }
        else {
            player.setSpeed(Utils.playerSpeedBig);
            player.size.setValue(Utils.playerSizeBig);
        }
        if (!pressedKeys.contains(KeyCode.SHIFT)) {
            player.setSpeed(Utils.playerSpeedBig);
            player.size.setValue(Utils.playerSizeBig);
            energy.set(Math.min(energy.getValue() + moveAmount/maxEnergy, 1));
        }
        if (pressedKeys.contains(KeyCode.UP))
            player.yPosition.set(
                Utils.clampToScreenVertical(player.yPosition.get() - moveAmount, Utils.playerSizeBig)
            );
        if (pressedKeys.contains(KeyCode.DOWN))
            player.yPosition.set(
                Utils.clampToScreenVertical(player.yPosition.get() + moveAmount, Utils.playerSizeBig)
            );
        if (pressedKeys.contains(KeyCode.LEFT))
            player.xPosition.set(
                Utils.clampToScreenHorizontal(player.xPosition.get() - moveAmount, Utils.playerSizeBig)
            );
        if (pressedKeys.contains(KeyCode.RIGHT))
            player.xPosition.set(
                Utils.clampToScreenHorizontal(player.xPosition.get() + moveAmount, Utils.playerSizeBig)
            );
    }


    private void updateEnemiesMovement(double deltaTime) {
        for (Entity entity : enemies) {
            entity.updateMovement(deltaTime, entity.speed);
        }
        checkCollisions();
    }





    private void checkCollisions() {
        for (Entity enemy : enemies) {
            if (Utils.calculateDistance(enemy.xPosition.getValue(), enemy.yPosition.getValue(),
                player.xPosition.getValue(), player.yPosition.getValue()) < player.size.getValue()+enemy.size.getValue()-5
            ) {
                onDeath();
            }
            /*
            if (enemy.getShape().getBoundsInParent().intersects(player.getShape().getBoundsInParent())) {
                onDeath();
                //System.out.println("Collision detected");
            }
            */
        }
    }


    /**
     * Removes enemies that are beyond the screen
     */
    private void removeOldEnemies(){
        List<Entity> enemiesToRemove = new ArrayList<>();
        for (Entity entity : enemies) {
            if (entity.xPosition.getValue() < -Utils.screenWidth){
                enemiesToRemove.add(entity);
            }
        }
        enemies.removeAll(enemiesToRemove);
        enemiesToRemove.forEach(enemy ->
                gamePane.getChildren().remove(enemy.shape)
        );
    }





    private void onDeath(){
        double finalScore = ScoreSerialized.baseScoreToActualScore(baseScore);
        gameLoop.stop();

        enemies.forEach(enemy ->
                gamePane.getChildren().remove(enemy.shape)
        );
        gamePane.getChildren().remove(player.shape);
        sceneManager.showGameOverScreen(finalScore);

    }

    private double calculateDifficultyCoefficient(){
        return 0.5*(1-Math.exp(-baseScore/1000));

    }
}
