package com.oh2harjoitustyo;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

abstract class Entity {

    double speed;


    // Position of player
    SimpleObjectProperty<Double> xPosition = new SimpleObjectProperty<>(0d);
    SimpleObjectProperty<Double> yPosition = new SimpleObjectProperty<>(0d);

    protected Shape shape;

    public Shape getShape() {
        return shape;
    }

    public void updateMovement(double deltaTime, double speed) {
        double moveAmount = speed * deltaTime;
        xPosition.set(xPosition.get() - moveAmount);
    }

}
