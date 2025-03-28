package com.oh2harjoitustyo;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HighScoresManager {

    public static List<ScoreSerialized> highScores;

    public static void initializeHighScores() {
        if (!doesFileExist()){
            highScores = new ArrayList<ScoreSerialized>();
            saveHighScores();
        }
    }

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

    public static void saveHighScores() {
        try(
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("highscores.dat"))
        ){
            highScores.sort(ScoreSerialized.comparator);
            outputStream.writeObject(highScores);
            System.out.println("Highscores saved");
            //tätä ei tarvitse
            //outputStream.close();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }




    }

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

    public static boolean doesFileExist(){
        File file = new File("highscores.dat");
        return file.exists();
    }
}
