package com.oh2harjoitustyo;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
    private final Pane gamePane; // The root pane
    private long lastUpdate = 0; // Last frame time in milliseconds
    private boolean isModifying = false; // To not modify lists at the same time in two different places

    private final List<Entity> enemies = new ArrayList<>();
    private final Entity player;

    private double baseScore = 0;
    private SimpleStringProperty scoreTextProperty = new SimpleStringProperty();


    public GameEngine(Scene scene, Pane gamePane) {
        this.player = new Player(scene, 1000);
        this.gamePane = gamePane;
        this.gamePane.getChildren().add(player.shape);
        Text scoreText = new Text("");
        scoreText.textProperty().bind(scoreTextProperty);
        gamePane.getChildren().add(scoreText);
    }


    public void addEntity(Entity entity) {
        enemies.add(entity);
        gamePane.getChildren().add(entity.getShape()); // Add to pane
    }

    public void start() {
        Random random = new Random();
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (random.nextDouble() > 0.99 && !isModifying) {
                    isModifying = true;
                    addEntity(new Pallo(
                        40 + 50*random.nextDouble(),
                        gamePane.getHeight()*(random.nextDouble()-0.5), 200+500*random.nextDouble())
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

                updateGame(deltaTime);
            }
        };
        gameLoop.start();
    }

    private void updateGame(double deltaTime) {
        for (Entity entity : enemies) {
            entity.updateMovement(deltaTime, entity.speed);
        }
        checkCollisions();
    }

    private void checkCollisions() {
        for (Entity enemy : enemies) {
            if (enemy.getShape().getBoundsInParent().intersects(player.getShape().getBoundsInParent())) {

                System.out.println("Collision detected");
            }
        }
    }

    private void removeOldEnemies(){
        List<Entity> enemiesToRemove = new ArrayList<>();
        for (Entity entity : enemies) {
            if (entity.xPosition.getValue() < -100){
                enemiesToRemove.add(entity);
            }
        }
        enemies.removeAll(enemiesToRemove);
        gamePane.getChildren().removeAll(enemiesToRemove);
    }

    private void onDeath(){
        double finalScore = ScoreSerialized.baseScoreToActualScore(baseScore);

    }
}
