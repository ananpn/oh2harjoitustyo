package com.oh2harjoitustyo;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * High score class that is saved and read from file
 * @author Antti Puuronen
 */
public class ScoreSerialized implements Serializable {

    /**
     * Score attained as integer
     */
    private int score;

    /**
     * Name of player as string
     */
    private String playerName;

    /**
     * Date when score was saved
     */
    private Date date;

    /** Gives the name of the player
     * @return String, Name of player
     */
    public String getPlayerName() {
        return playerName;
    }

    /** Sets the name of the player in the score to be saved
     * @param playerName String, Name of player
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /** Gives the score in the saved object
     * @return int score
     */
    public int getScore() {
        return score;
    }

    /** Sets the score
     * @param score int score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /** returns the Date object associated with the score
     * @return ScoreSerialized.date
     */
    public Date getDate() {
        return date;
    }


    /** sets the date in this score
     * @param date Date date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /** Default constructor, sets all fields
     * @param score int, score attained
     * @param playerName String, name of player
     * @param date Date, date the score was attained
     */
    public ScoreSerialized(int score, String playerName, Date date) {
        this.score = score;
        this.playerName = playerName;
        this.date = date;
    }

    /** Converts Date object ScoreSerialized.date into a displayable String
     * @return String, date in display form
     */
    public String dateToDisplayString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.getHours() + ":" + date.getMinutes());
        stringBuilder.append(" ");
        stringBuilder.append(date.getDate());
        stringBuilder.append(".");
        stringBuilder.append(date.getMonth()+1);
        stringBuilder.append(".");
        stringBuilder.append(date.getYear()+1900);
        return stringBuilder.toString();
    }

    /** Converts the double GameEngine.baseScore to actual score
     * @param score double, the base score
     * @return actual score as int
     */
    public static int baseScoreToActualScore(double score) {
        return (int) (score * 400d);
    }

    /**
     * Comparator for ScoreSerialized that sorts them with the largest ScoreSerialized.score first.
     *
     * Used in HighScoreScreen.
     */
    static Comparator <ScoreSerialized> comparator = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return ((ScoreSerialized) o2).getScore()-((ScoreSerialized) o1).getScore();
        }
    };

}
