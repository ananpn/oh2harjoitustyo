package com.oh2harjoitustyo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

// enemy
public class Enemy extends Entity {

    /**
     * Determines if the Enemy originally moves up or down in the y-direction
     */
    private boolean yUp;

    /**
     *  Determines the frequency of Enemy up-down movement
      */
    private double sinFreqCoeff;


    /** Initialized the Enemy object with random color and given radius, y-coordinate and speed.
     * @param radius Radius of the enemy ball
     * @param originalY Y-coordinate where the enemy is created
     * @param speed The speed parameter
     */
    public Enemy(double radius, double originalY, double speed){

        this.speed = speed;


        this.shape = new Circle(radius);
        this.size.setValue(radius);
        ((Circle) shape).radiusProperty().bind(size);

        //this.setWidth(40);
        //this.setHeight(40)

        xPosition.set(Utils.screenWidth);
        yPosition.set(originalY);

        List<Color> colors = Arrays.asList(
            Color.rgb(255,0,128),
            Color.rgb(240,0,255),
            Color.rgb(116,238,21),
            Color.rgb(77,238,234),
            Color.rgb(255,231,0)

        );
        Random random = new Random();
        int randomInt = random.nextInt(0, colors.size());
        // Reduce the number of light blue and yellow balls for aesthetic reasons
        if (randomInt >= 3 && random.nextDouble() < 0.4){
            randomInt = random.nextInt(0, colors.size());
        }
        Color randomColor = colors.get(randomInt);

        shape.setFill(randomColor);
        shape.setStroke(randomColor);

        // Randomize the phase of the up-down movement (either originally up or originally down)
        yUp = random.nextBoolean();

        // Gives the various colors different up-down movement behaviour
        sinFreqCoeff = switch(randomInt) {
            case 0 -> 2;
            case 1 -> 0.7;
            case 2 -> 0.4;
            case 3 -> 0.1;
            case 4 -> 1;
            default -> throw new IllegalStateException("Unexpected value: " + randomInt);
        };

        shape.translateXProperty().bind(xPosition);
        shape.translateYProperty().bind(yPosition);



    }


    /** Updates Enemy position
     * @param deltaTime Time since last update in milliseconds
     * @param score Current score to determine progress of the game
     */
    @Override
    public void updateMovement(double deltaTime, double score) {
        double moveAmountX = speed * deltaTime;
        xPosition.set(xPosition.get() - moveAmountX);
        // start y-movement after a while
        if (score > 5.0) {
            double moveAmountY = 0.2 * speed  * deltaTime * (1 - Math.exp(-(score-5)/10));
            if (yUp) moveAmountY = -0.2 * speed * deltaTime * (1 - Math.exp(-(score-5)/10));
            yPosition.set(yPosition.get() + moveAmountY * Math.sin(score * sinFreqCoeff));
        }
    }


}