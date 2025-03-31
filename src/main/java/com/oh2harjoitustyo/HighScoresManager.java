package com.oh2harjoitustyo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages high scores and reading and writing them to and from a file
 * @author Antti Puuronen
 */
public class HighScoresManager {


    /**
     * High scores as list
     */
    public static List<ScoreSerialized> highScores;


    /**
     * Initializes the high score list in this class and creates an empty file
     */
    public static void initializeHighScores() {
        if (!doesFileExist()){
            highScores = new ArrayList<ScoreSerialized>();
            saveHighScores();
        }
    }


    /** Checks if a score can be placed in the high scores
     * @param newScoreDouble New score to check
     * @return Returns true, if the new score belongs in the high scores, and false, if not.
     */
    public static boolean checkNewScore(double newScoreDouble) {
        readHighScores();
        highScores.sort(ScoreSerialized.comparator);
        if (highScores.size() < Utils.MAX_HIGH_SCORES){
            return true;
        }
        while (highScores.size() > Utils.MAX_HIGH_SCORES){
            highScores.removeLast();
        }
        return (highScores.getLast().getScore() < newScoreDouble);
    }

    /** Saves new score to file and also adds it to HighScoreManager.highscores
     * @param newScore New score to be saved
     */
    public static void saveNewScore(ScoreSerialized newScore){
        // Read List highScore from file
        readHighScores();
        highScores.sort(ScoreSerialized.comparator);
        if (highScores.size() < Utils.MAX_HIGH_SCORES){
            highScores.add(newScore);
            highScores.sort(ScoreSerialized.comparator);
        }
        else{
            while (highScores.size() > Utils.MAX_HIGH_SCORES){
                highScores.removeLast();
            }
            highScores.sort(ScoreSerialized.comparator);
            if (ScoreSerialized.comparator.compare(newScore, highScores.getLast())<0){
                highScores.removeLast();
                highScores.add(newScore);
            }
        }
        // Write the List highScores to file
        saveHighScores();

    }

    /**
     * Saves HighScoreManager.highScores to file highscores.dat located in project root.
     * @throws RuntimeException if something goes wrong while writing to file
     */
    public static void saveHighScores() {
        try(
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("highscores.dat"))
        ){
            highScores.sort(ScoreSerialized.comparator);
            outputStream.writeObject(highScores);
            //tätä ei tarvitse
            //outputStream.close();
        }
        catch (IOException e) {
            throw new RuntimeException("File write error: unable to save high scores", e);
        }
    }

    /**
     * Reads HighScoreManager.highScores from file highscores.dat located in project root
     * @throws RuntimeException if the stored and read objects are of unknown class
     */
    public static void readHighScores() {
        initializeHighScores();
        try(
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("highscores.dat"))
        ) {
            highScores = (List<ScoreSerialized>) inputStream.readObject();
            highScores.sort(ScoreSerialized.comparator);
            //tätä ei tarvitse
            //inputStream.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: file not found, cannot read high scores");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Error: something went wrong while reading file");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Error: the stored and read objects are of unknown class", e);
        }
    }


    /** Checks if the file highscores.dat exists in project root
     * @return true, if highscores.dat exists in project root, false otherwise
     */
    public static boolean doesFileExist(){
        File file = new File("highscores.dat");
        return file.exists();
    }


    /**
     * Empties the highScores List and saves the empty list to file highscores.dat
     */
    public static void clearHighScores(){
        highScores.clear();
        saveHighScores();
    }
}
