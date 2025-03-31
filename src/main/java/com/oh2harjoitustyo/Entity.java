package com.oh2harjoitustyo;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Shape;

/**
 * Abstract class for the game entities, that is player and the enemis
 * @author Antti Puuronen
 */
abstract class Entity {

    /**
     * Speed of the Entity
     */
    double speed;


    /**
     * x-coordinate of the Entity position
     */
    SimpleObjectProperty<Double> xPosition = new SimpleObjectProperty<>(0d);
    /**
     * y-coordinate of the Entity position
     */
    SimpleObjectProperty<Double> yPosition = new SimpleObjectProperty<>(0d);

    /**
     * size of the Entity
     */
    SimpleObjectProperty<Double> size = new SimpleObjectProperty<>(40d);


    /**
     * Actual drawn Shape of the entity
     */
    protected Shape shape;


    /** returns the actual drawn Shape of the entity
     * @return Entity.shape
     */
    public Shape getShape() {
        return shape;
    }

    /** Updates entity movement based on time since last update and the current game progress
     * @param deltaTime double, Time since last update
     * @param baseScore double, current score in the game to know current progress
     */
    public void updateMovement(double deltaTime, double baseScore) {

    }

    /** Tells if Entity is far off the screen to the left
     * @return true, if Entity is far off the screen to the left, false otherwise
     */
    public boolean isOutOfBoundsLeft() {
        return xPosition.getValue() < -Utils.SCREEN_WIDTH /1.5;
    }

}
