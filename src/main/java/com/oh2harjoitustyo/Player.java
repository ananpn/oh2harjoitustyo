package com.oh2harjoitustyo;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashSet;
import java.util.Set;

// Player character, temporarily using Rectangle
public class Player extends Entity{



    // Keeps track of currently pressed keys
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    // This is needed for syncing the movement with framerate
    private long lastUpdate = 0;


    public Player(Scene scene, double speed){
        this.shape = new Circle(40);

        this.speed = speed;
        this.xPosition.setValue(-300d);


        shape.setStroke(Color.BLACK);
        shape.setFill(Color.BLACK);

        //this.setWidth(40);
        //this.setHeight(40);

        // Handles the keypresses
        scene.setOnKeyPressed(event -> {
            pressedKeys.add(event.getCode());
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.remove(event.getCode());
        });

        // This handles movement
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
            if (lastUpdate == 0) {
                lastUpdate = now;
                return;
            }
            double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
            lastUpdate = now;
            updatePlayerMovement(deltaTime);
            }
        };
        gameLoop.start();

        shape.translateXProperty().bind(xPosition);
        shape.translateYProperty().bind(yPosition);
    }



    protected void updatePlayerMovement(double deltaTime) {
        double moveAmount = speed * deltaTime;
        if (pressedKeys.contains(KeyCode.UP)) yPosition.set(yPosition.get() - moveAmount);
        if (pressedKeys.contains(KeyCode.DOWN)) yPosition.set(yPosition.get() + moveAmount);
        if (pressedKeys.contains(KeyCode.LEFT)) xPosition.set(xPosition.get() - moveAmount);
        if (pressedKeys.contains(KeyCode.RIGHT)) xPosition.set(xPosition.get() + moveAmount);
    }

}