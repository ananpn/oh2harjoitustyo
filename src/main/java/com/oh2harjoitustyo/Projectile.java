package com.oh2harjoitustyo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Projectile extends Entity {
    double speedX;
    double speedY;


    public Projectile(double radius, double originalX, double originalY, double speedX, double speedY) {

        this.speedX = speedX;
        this.speedY = speedY;

        this.shape = new Circle(radius);
        this.size.setValue(radius);
        ((Circle) shape).radiusProperty().bind(size);


        xPosition.set(originalX);
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

        shape.setFill(Color.ORANGERED);
        shape.setStroke(Color.ORANGE);

        shape.translateXProperty().bind(xPosition);
        shape.translateYProperty().bind(yPosition);



    }

    @Override
    public void updateMovement(double deltaTime, double score) {
        double moveAmountX = speedX * deltaTime;
        double moveAmountY = speedY * deltaTime;
        xPosition.set(xPosition.get() + moveAmountX);
        yPosition.set(yPosition.get() + moveAmountY);

    }


}