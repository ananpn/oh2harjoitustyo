package com.oh2harjoitustyo;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public boolean checkNewScore(double newScoreDouble) {
        readHighScores();
        ScoreSerialized newScore = new ScoreSerialized(
                (int) Math.round(newScoreDouble),
                "Default",
                Date.from(Instant.now())
        );
        highScores.sort(ScoreSerialized.comparator);
        if (highScores.size() < Utils.maxHighScores){
            return true;
        }
        while (highScores.size() > Utils.maxHighScores){
            highScores.removeLast();
        }
        return (highScores.getLast().getScore() < newScoreDouble);
    }

    public void saveNewScore(ScoreSerialized newScore){

        readHighScores();
        highScores.sort(ScoreSerialized.comparator);
        if (highScores.size() < Utils.maxHighScores){
            highScores.add(newScore);
            highScores.sort(ScoreSerialized.comparator);
            saveHighScores();
        }
        else{
            while (highScores.size() > Utils.maxHighScores){
                highScores.removeLast();
            }
            highScores.sort(ScoreSerialized.comparator);
            if (ScoreSerialized.comparator.compare(newScore, highScores.getLast())<0){
                highScores.removeLast();
                highScores.add(newScore);
            }
            saveHighScores();

        }

    }

    /**
     * Saves HighScoreManager.highScores to file highscores.dat located in project root.
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
        catch(Exception e){
            throw new RuntimeException(e);
        }




    }

    /**
     * Reads HighScoreManager.highScores from file highscores.dat located in project root
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
            e.printStackTrace();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    /** Checks if the file highscores.dat exists in project root
     * @return true, if highscores.dat exists in project root, false otherwise
     */
    public static boolean doesFileExist(){
        File file = new File("highscores.dat");
        return file.exists();
    }


    public static void clearHighScores(){
        highScores.clear();
        saveHighScores();
    }
}
