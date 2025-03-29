package com.oh2harjoitustyo;

import javafx.scene.paint.Color;

public class Utils {
    public static final String backGroundColor = "-fx-background-color: #030303";
    public static final String backGroundBarColor = "-fx-background-color: #170F16";

    public static final double screenWidth = 1200;
    public static final double screenHeight = 800;

    public static final double playerSizeBig = 16;
    public static final double playerSizeSmall = 8;

    public static final double playerSpeedBig = 550;
    public static final double playerSpeedSmall = 400;

    public static final double maxEnergy = 2000;

    public static final double originalSpawnInterval = 115;

    public static final int maxHighScores = 10;


    public static double clampToScreen(double value){
        return Math.clamp(value, 0, screenWidth);
    }

    public static double clampToScreenHorizontal(double value, double size){
        return Math.clamp(value, size-screenWidth/2, -1.5*size+screenWidth/2);
    }

    public static double clampToScreenVertical(double value, double size){
        return Math.clamp(value, -screenHeight/2.6, screenHeight/2.6);
    }

    public static double calculateDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
    }



}
