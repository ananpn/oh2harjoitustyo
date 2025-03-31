package com.oh2harjoitustyo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * Class for handling the player character
 * @author Antti Puuronen
 */
public class Player extends Entity{

    /**
     * Constructor for player, sets properties to defaults
     */
    public Player(){
        this.shape = new Circle(Utils.PLAYER_SIZE_BIG);
        this.size.setValue(Utils.PLAYER_SIZE_BIG);
        ((Circle) shape).radiusProperty().bind(size);

        this.speed = Utils.PLAYER_SPEED_BIG;
        this.xPosition.setValue(-300d);


        shape.setStroke(Color.DARKRED);
        shape.setFill(Color.DARKRED);


        shape.translateXProperty().bind(xPosition);
        shape.translateYProperty().bind(yPosition);
    }

    /** Sets the speed of the player Entity
     * @param speed double, speed of the player Entity
     */
    public void setSpeed(double speed){
        this.speed = speed;
    }




}