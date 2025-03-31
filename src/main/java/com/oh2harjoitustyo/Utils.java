package com.oh2harjoitustyo;

/**
 * Various constants and functions used globally
 * @author Antti
 */
public class Utils {

    /**
     * Color of the background
     */
    public static final String BACKGROUNDCOLOR = "-fx-background-color: #030303";


    /**
     * Color of the top and bottom bars in game screen
     */
    public static final String BACKGROUNDBARCOLOR = "-fx-background-color: #170F16";

    /**
     * Window width
     */
    public static final double SCREEN_WIDTH = 1200;
    /**
     * Window height
     */
    public static final double SCREEN_HEIGHT = 800;

    /**
     * Normal size of the player character
     */
    public static final double PLAYER_SIZE_BIG = 16;
    /**
     * Small size of the player character
     */
    public static final double PLAYER_SIZE_SMALL = 8;

    /**
     * Speed of player when normal size
     */
    public static final double PLAYER_SPEED_BIG = 550;

    /**
     * Speed of player when small
     */
    public static final double PLAYER_SPEED_SMALL = 400;

    /**
     * Maximum amount of energy
     */
    public static final double MAX_ENERGY = 2000;


    /**
     * The minimum time it takes between enemy spawns at the start of the game. This is decreased as the game progresses.
     */
    public static final double ORIGINAL_SPAWN_INTERVAL = 115;


    /**
     * Maximum number of high scores
     */
    public static final int MAX_HIGH_SCORES = 10;


    /** Clamps a value to horizontal screen bounds for restricting player movement. Size can be taken into account
     * so that the object doesn't penetrate the bounds.
     * @param value Value to clamp
     * @param size Size of the object
     * @return Clamped value
     */
    public static double clampToScreenHorizontal(double value, double size){
        return Math.clamp(value, size- SCREEN_WIDTH /2, -1.5*size+ SCREEN_WIDTH /2);
    }

    /** Clamps a value to vertical screen bounds for restricting player movement.
     * @param value Value to clamp
     * @param size Size of the object
     * @return Clamped value
     */
    public static double clampToScreenVertical(double value, double size){
        return Math.clamp(value, -SCREEN_HEIGHT /2.6, SCREEN_HEIGHT /2.6);
    }


    /** Calculates the Euclidean distance between two points (x1,y1) and (x2,y2)
     * @param x1 1st x-coordinate
     * @param y1 1st y-coordinate
     * @param x2 2nd x-coordinate
     * @param y2 2nd y-coordinate
     * @return Euclidean distance between the given points
     */
    public static double calculateDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt(calculateDistanceSquared(x1, y1, x2, y2));
    }

    /** Calculates the square of the Euclidean distance between two points (x1,y1) and (x2,y2), that is (x2-x1)^2 + (y2-y1)^2
     * @param x1 1st x-coordinate
     * @param y1 1st y-coordinate
     * @param x2 2nd x-coordinate
     * @param y2 2nd y-coordinate
     * @return Square of the Euclidean distance between the given points (x1,y1) and (x2,y2), that is (x2-x1)^2 + (y2-y1)^2
     */
    public static double calculateDistanceSquared(double x1, double y1, double x2, double y2){
        return Math.pow(x2-x1,2) + Math.pow(y2-y1,2);
    }
}
