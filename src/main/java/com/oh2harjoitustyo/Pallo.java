package com.oh2harjoitustyo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// enemy
public class Pallo extends Entity {
    public int id = 0;

    public boolean yUp = false;

    private double sinPhase = 1;


    public Pallo(double radius, double originalY, double speed){

        id = Utils.ballId;
        Utils.ballId++;



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
        int randomInt = random.nextInt(0, colors.size());
        Color randomColor = colors.get(randomInt);

        shape.setFill(randomColor);
        shape.setStroke(randomColor);

        sinPhase = switch(randomInt) {
            case 0 -> 0.4;
            case 1 -> 0.1;
            case 2 -> 0.7;
            case 3 -> 1;
            case 4 -> 2;
            default -> throw new IllegalStateException("Unexpected value: " + randomInt);
        };

        shape.translateXProperty().bind(xPosition);
        shape.translateYProperty().bind(yPosition);



    }

    @Override
    public void updateMovement(double deltaTime, double speed, double score) {
        double moveAmountX = speed * deltaTime;
        xPosition.set(xPosition.get() - moveAmountX);
        if (score > 5.0) {
            double moveAmountY = 0.2 * speed  * deltaTime * (1 - Math.exp(-(score-5)/10));
            if (yUp) moveAmountY = -0.2 * speed * deltaTime * (1 - Math.exp(-(score-5)/10));
            yPosition.set(yPosition.get() + moveAmountY * Math.sin(score * sinPhase));
        }
    }


}