package com.oh2harjoitustyo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// enemy
public class Pallo extends Entity {


    public Pallo(double radius, double originalY, double speed){
        this.speed = speed;

        this.shape = new Circle(radius);
        this.size.setValue(radius);
        ((Circle) shape).radiusProperty().bind(size);

        //this.setWidth(40);
        //this.setHeight(40)

        xPosition.set(2000d);
        yPosition.set(originalY);

        shape.setFill(Color.BLACK);
        shape.setStroke(Color.BLACK);


        shape.translateXProperty().bind(xPosition);
        shape.translateYProperty().bind(yPosition);



    }

}