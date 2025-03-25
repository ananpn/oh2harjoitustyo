package com.oh2harjoitustyo;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

// enemy
public class Pallo extends Entity {


    // This is needed for syncing the movement with framerate
    private long lastUpdate = 0;

    public Pallo(double radius, double originalY, double speed){
        this.speed = speed;

        this.shape = new Circle(radius);

        //this.setWidth(40);
        //this.setHeight(40)

        xPosition.set(2000d);
        yPosition.set(originalY);

        shape.setFill(Color.BLACK);
        shape.setStroke(Color.BLACK);

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
            updateMovement(deltaTime, Pallo.this.speed);
            }
        };
        gameLoop.start();

        shape.translateXProperty().bind(xPosition);
        shape.translateYProperty().bind(yPosition);



    }

}