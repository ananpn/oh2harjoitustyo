package com.oh2harjoitustyo;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Shape;

abstract class Entity {

    double speed;



    // Position of player
    SimpleObjectProperty<Double> xPosition = new SimpleObjectProperty<>(0d);
    SimpleObjectProperty<Double> yPosition = new SimpleObjectProperty<>(0d);

    SimpleObjectProperty<Double> size = new SimpleObjectProperty<>(40d);

    protected Shape shape;

    public Shape getShape() {
        return shape;
    }

    public void updateMovement(double deltaTime, double speed, double baseScore) {

    }

    public boolean isOutOfBoundsLeft() {
        return xPosition.getValue() < -Utils.screenWidth/1.5;
    }

}
