package com.oh2harjoitustyo;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {
    private final List<Entity> enemies = new ArrayList<>();
    private final Entity player;
    private long lastUpdate = 0; // Store last frame time
    private final Pane gamePane; // The UI layer for rendering

    public GameEngine(Scene scene, Pane gamePane) {

        this.player = new Player(scene, 1000);
        this.gamePane = gamePane;
        this.gamePane.getChildren().add(player.shape);

    }


    public void addEntity(Entity entity) {
        enemies.add(entity);
        gamePane.getChildren().add(entity.getShape()); // Add to scene
    }

    public void start() {
        Random random = new Random();
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (System.currentTimeMillis()%100 == 0) {
                    addEntity(new Pallo(50, gamePane.getHeight()*(random.nextDouble()-0.5), 300+500*random.nextDouble()));
                }
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                double deltaTime = (now - lastUpdate) / 1_000_000_000.0; // Convert ns to seconds
                lastUpdate = now;

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
                System.out.println("Collision detected!");
            }
        }
    }
}
