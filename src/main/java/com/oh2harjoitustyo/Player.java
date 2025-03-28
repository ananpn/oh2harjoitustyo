package com.oh2harjoitustyo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


// Player character
public class Player extends Entity{

    public Player(){
        this.shape = new Circle(Utils.playerSizeBig);
        this.size.setValue(Utils.playerSizeBig);
        ((Circle) shape).radiusProperty().bind(size);

        this.speed = Utils.playerSpeedBig;
        this.xPosition.setValue(-300d);


        shape.setStroke(Color.DARKRED);
        shape.setFill(Color.DARKRED);


        shape.translateXProperty().bind(xPosition);
        shape.translateYProperty().bind(yPosition);
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }




}