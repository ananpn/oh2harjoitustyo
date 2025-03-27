package com.oh2harjoitustyo;

public class Utils {
    public static final double screenWidth = 1200;
    public static final double screenHeight = 800;

    public static final double playerSizeBig = 40;
    public static final double playerSizeSmall = 20;

    public static final double playerSpeedBig = 800;
    public static final double playerSpeedSmall = 600;

    public static final double maxEnergy = 1000;

    public static final int maxHighScores = 10;

    public static double clampToScreen(double value){
        return Math.clamp(value, 0, screenWidth);
    }

    public static double clampToScreenHorizontal(double value, double size){
        return Math.clamp(value, size-screenWidth/2, -1.5*size+screenWidth/2);
    }

    public static double clampToScreenVertical(double value, double size){
        return Math.clamp(value, size-screenHeight/2, -1.9*size+screenHeight/2);
    }

    public static double calculateDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
    }



}
