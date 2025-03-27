package com.oh2harjoitustyo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScoresManager {

    public List<ScoreSerialized> highScores;

    public void checkNewScore(ScoreSerialized newScore){
        if (highScores == null){
            highScores = new ArrayList<ScoreSerialized>();
        }
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

    public void saveHighScores() {
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

    public void readHighScores() {
        try(
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("highscores.dat"))
        ) {
            highScores = (List<ScoreSerialized>) inputStream.readObject();
            highScores.sort(ScoreSerialized.comparator);
            //tätä ei tarvitse
            //inputStream.close();
        }
        //6.b
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
}
