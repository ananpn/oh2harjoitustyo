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

import static com.oh2harjoitustyo.Utils.MAX_ENERGY;

/**
 * Handles most events in the actual game
 * @author Antti Puuronen
 */
public class GameEngine {
    /**
     * Project common SceneManager
     */
    private final SceneManager sceneManager;


    /**
     * Pane where the game is drawn
     */
    private final Pane gamePane;

    /**
     * Last frame time in milliseconds
     */
    private long lastUpdate = 0;

    /**
     * List of Entity enemies
     */
    private final List<Entity> enemies = new ArrayList<>();

    /**
     * The player Entity
     */
    private final Player player;

    /**
     * double for tracking game progress and from which the score is calculated
     */
    private double baseScore = 0;

    /**
     * SimpleStringProperty for drawing the score text so that it updates in real time
     */
    private SimpleStringProperty scoreTextProperty = new SimpleStringProperty();


    /**
     * AnimationTimer for the basic game event loop (what happens in one frame)
     */
    private AnimationTimer gameLoop;


    /**
     * energy that is consumed by holding shift down
     */
    private SimpleDoubleProperty energy = new SimpleDoubleProperty(1);

    /**
     * Milliseconds since an enemy was last spawned
     */
    private double timeSinceLastSpawn = 0;
    /**
     * Current minimum spawn interval in milliseconds
     */
    private double spawnIntervalMillis = Utils.ORIGINAL_SPAWN_INTERVAL;


    /**
     * Keeps track of currently pressed keys
     */
    private final Set<KeyCode> pressedKeys = new HashSet<>();


    /** Constructs the GameEngine.
     * @param gamePane Pane where the game is drawn.
     * @param sceneManager The SceneManager of the whole project.
     */
    public GameEngine(Pane gamePane, SceneManager sceneManager) {
        this.player = new Player();
        this.gamePane = gamePane;
        this.gamePane.getChildren().add(player.shape);
        this.sceneManager = sceneManager;
        Text scoreText = new Text("");
        scoreText.setStroke(Color.WHITESMOKE);
        scoreText.textProperty().bind(scoreTextProperty);
        scoreText.setTranslateY(40-Utils.SCREEN_HEIGHT /2);
        gamePane.getChildren().add(scoreText);

        ProgressBar energyBar = new ProgressBar();
        energyBar.progressProperty().bind(energy);
        energyBar.setVisible(true);
        energyBar.setTranslateY(-40+Utils.SCREEN_HEIGHT /2);
        gamePane.getChildren().add(energyBar);
    }


    /** Adds an enemy to the game.
     * @param entity The enemy Entity to add to GameEngine.gamePane and the List<Entity> enemies.
     */
    public void addEnemy(Entity entity) {
        enemies.add(entity);
        gamePane.getChildren().add(entity.getShape()); // Add to pane
    }

    /** Starts the game and handles the gameplay-related events like keypresses and what happens every frame
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
                    addEnemy(new Enemy(
                        30 + 25*random.nextDouble()+60*diff,
                        1.15*gamePane.getHeight()*(random.nextDouble()-0.5), 380+300*random.nextDouble()+400*diff
                    ));

                }

                spawnIntervalMillis = Utils.ORIGINAL_SPAWN_INTERVAL - 200*diff;
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
            player.setSpeed(Utils.PLAYER_SPEED_SMALL);
            player.size.setValue(Utils.PLAYER_SIZE_SMALL);
            energy.set(Math.max(energy.getValue() - moveAmount/ MAX_ENERGY, 0));
        }
        else {
            player.setSpeed(Utils.PLAYER_SPEED_BIG);
            player.size.setValue(Utils.PLAYER_SIZE_BIG);
        }
        if (!pressedKeys.contains(KeyCode.SHIFT)) {
            player.setSpeed(Utils.PLAYER_SPEED_BIG);
            player.size.setValue(Utils.PLAYER_SIZE_BIG);
            energy.set(Math.min(energy.getValue() + 0.5*moveAmount/ MAX_ENERGY, 1));
        }
        if (pressedKeys.contains(KeyCode.UP))
            player.yPosition.set(
                Utils.clampToScreenVertical(player.yPosition.get() - moveAmount, Utils.PLAYER_SIZE_BIG)
            );
        if (pressedKeys.contains(KeyCode.DOWN))
            player.yPosition.set(
                Utils.clampToScreenVertical(player.yPosition.get() + moveAmount, Utils.PLAYER_SIZE_BIG)
            );
        if (pressedKeys.contains(KeyCode.LEFT))
            player.xPosition.set(
                Utils.clampToScreenHorizontal(player.xPosition.get() - moveAmount, Utils.PLAYER_SIZE_BIG)
            );
        if (pressedKeys.contains(KeyCode.RIGHT))
            player.xPosition.set(
                Utils.clampToScreenHorizontal(player.xPosition.get() + moveAmount, Utils.PLAYER_SIZE_BIG)
            );
        //for debugging and adjustments
        /*
        if (pressedKeys.contains(KeyCode.SPACE)){
            System.out.println("difficulty: " + calculateDifficultyCoefficient());
            System.out.println("spawn interval: " + spawnIntervalMillis);
            System.out.println("average size: " + (30 + 12.5+60*calculateDifficultyCoefficient()));
            System.out.println("average speed: " + (380+150+400*calculateDifficultyCoefficient()));
        }
        */
    }


    /** Updates position of all Entity objects in the List enemies
     * @param deltaTime double, milliseconds since last update
     */
    private void updateEnemiesMovement(double deltaTime) {
        for (Entity entity : enemies) {
            entity.updateMovement(deltaTime, baseScore);
        }
        checkCollisions();
    }


    /**
     * Checks if GameEngine.player intersects any of the Entity objects in the List enemies, and causes death if this happens.
     * That is, onDeath() is called if player intersects enemies
     */
    private void checkCollisions() {
        for (Entity enemy : enemies) {
            if (Utils.calculateDistanceSquared(enemy.xPosition.getValue(), enemy.yPosition.getValue(),
                player.xPosition.getValue(), player.yPosition.getValue()) < Math.pow(player.size.getValue()+enemy.size.getValue()-5, 2)
            ) {
                onDeath();
            }

            /*
            // probably less efficient
            if (Utils.calculateDistance(enemy.xPosition.getValue(), enemy.yPosition.getValue(),
                player.xPosition.getValue(), player.yPosition.getValue()) < player.size.getValue()+enemy.size.getValue()-5
            ) {
                onDeath();
            }
            */
            /*
            // probably less efficient
            if (enemy.getShape().getBoundsInParent().intersects(player.getShape().getBoundsInParent())) {
                onDeath();
                //System.out.println("Collision detected");
            }
            */
        }
    }


    /**
     * Stops the game, clears the List enemies, clears the gamePane, and moves to game over screen
     */
    private void onDeath(){
        double finalScore = ScoreSerialized.baseScoreToActualScore(baseScore);
        gameLoop.stop();

        enemies.forEach(enemy ->
                gamePane.getChildren().remove(enemy.shape)
        );
        gamePane.getChildren().remove(player.shape);
        sceneManager.showGameOverScreen(finalScore);

    }

    /** Calculates and returns a difficulty coefficient based on the double baseScore
     * @return Difficulty coefficient, between 0 and 0.75
     */
    private double calculateDifficultyCoefficient(){
        return 0.75*(1-Math.exp(-baseScore/800.0));

    }
}
