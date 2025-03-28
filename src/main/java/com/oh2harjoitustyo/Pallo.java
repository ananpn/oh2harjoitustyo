package com.oh2harjoitustyo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// enemy
public class Pallo extends Entity {

    public boolean yUp = false;


    public Pallo(double radius, double originalY, double speed){
        this.speed = speed;
        if (System.currentTimeMillis()%2 == 0){
            yUp = true;
        }

        this.shape = new Circle(radius);
        this.size.setValue(radius);
        ((Circle) shape).radiusProperty().bind(size);

        //this.setWidth(40);
        //this.setHeight(40)

        xPosition.set(Utils.screenWidth);
        yPosition.set(originalY);

        List<Color> colors = Arrays.asList(
            Color.rgb(116,238,21),
            Color.rgb(77,238,234),
            Color.rgb(240,0,255),
            Color.rgb(255,231,0),
            Color.rgb(255,0,128)
        );
        Random random = new Random();
        Color randomColor = colors.get(random.nextInt(0, colors.size()));

        shape.setFill(randomColor);
        shape.setStroke(randomColor);


        shape.translateXProperty().bind(xPosition);
        shape.translateYProperty().bind(yPosition);



    }

    @Override
    public void updateMovement(double deltaTime, double speed, double score) {
        double moveAmountX = speed * deltaTime;
        xPosition.set(xPosition.get() - moveAmountX);
        double moveAmountY = 20;
        if (yUp) moveAmountY = -20;
        yPosition.set(yPosition.get() + Math.sin(score/1000.0)*moveAmountY);
    }

}