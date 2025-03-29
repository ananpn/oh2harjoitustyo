package com.oh2harjoitustyo;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.*;

import static com.oh2harjoitustyo.Utils.maxEnergy;

public class GameEngine {
    private final SceneManager sceneManager;


    private final Pane gamePane; // The root pane
    private long lastUpdate = 0; // Last frame time in milliseconds

    private final List<Entity> enemies = new ArrayList<>();
    private final Player player;

    private double baseScore = 0;
    private SimpleStringProperty scoreTextProperty = new SimpleStringProperty();

    private AnimationTimer gameLoop;


    private SimpleDoubleProperty energy = new SimpleDoubleProperty(1);

    private double timeSinceLastSpawn = 0;
    private double spawnIntervalMillis = 100;


    // Keeps track of currently pressed keys
    private final Set<KeyCode> pressedKeys = new HashSet<>();


    /** Constructs the GameEngine.
     * @param gamePane Pane where the game is drawn.
     * @param sceneManager The SceneManager of the whole project.
     *
     *
     *
     */
    public GameEngine(Pane gamePane, SceneManager sceneManager) {
        this.player = new Player();
        this.gamePane = gamePane;
        this.gamePane.getChildren().add(player.shape);
        this.sceneManager = sceneManager;
        Text scoreText = new Text("");
        scoreText.setStroke(Color.WHITESMOKE);
        scoreText.textProperty().bind(scoreTextProperty);
        scoreText.setTranslateY(40-Utils.screenHeight/2);
        gamePane.getChildren().add(scoreText);

        ProgressBar energyBar = new ProgressBar();
        energyBar.progressProperty().bind(energy);
        energyBar.setVisible(true);
        energyBar.setTranslateY(-40+Utils.screenHeight/2);
        gamePane.getChildren().add(energyBar);
    }


    /** Adds an enemy to the game.
     * @param entity The enemy Entity to add to GameEngine.gamePane and the List<Entity> enemies.
     */
    public void addEnemy(Entity entity) {
        enemies.add(entity);
        gamePane.getChildren().add(entity.getShape()); // Add to pane
    }

    /**
     * @param scene Scene where the game is drawn.
     */
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
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double deltaTime = (now - lastUpdate) / 1_000_000.0; // Convert ns to ms
                if (deltaTime < 10) return;
                lastUpdate = now;
                baseScore += deltaTime/1000.0;
                scoreTextProperty.set(String.valueOf(ScoreSerialized.baseScoreToActualScore(baseScore)));
                double diff = calculateDifficultyCoefficient();

                timeSinceLastSpawn += deltaTime;
                if (timeSinceLastSpawn >= spawnIntervalMillis ) {
                    timeSinceLastSpawn = 0;
                    addEnemy(new Pallo(
                        25 + 30*random.nextDouble()+30*diff,
                        1.1*gamePane.getHeight()*(random.nextDouble()-0.5), 450+300*random.nextDouble()+400*diff
                    ));

                }
                enemies.removeIf(entity -> entity.isOutOfBoundsLeft());


                updatePlayerMovement(deltaTime/1000.0);
                updateEnemiesMovement(deltaTime/1000.0);
            }
        };
        gameLoop.start();
    }

    /** Checks pressed keys and updates the state of the game accordingly.
     * @param deltaTime Time since last update in milliseconds.
     */
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
            energy.set(Math.min(energy.getValue() + 0.5*moveAmount/maxEnergy, 1));
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
        if (pressedKeys.contains(KeyCode.SPACE))
            System.out.println(calculateDifficultyCoefficient());
    }


    private void updateEnemiesMovement(double deltaTime) {
        for (Entity entity : enemies) {
            entity.updateMovement(deltaTime, entity.speed, baseScore);
        }
        checkCollisions();
    }

    private void shootInAPattern(){


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
        //System.out.println("Removing old enemies");
        //System.out.println("number of enemies: " + enemies.size());
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
        return 0.75*(1-Math.exp(-baseScore/1500.0));

    }
}
