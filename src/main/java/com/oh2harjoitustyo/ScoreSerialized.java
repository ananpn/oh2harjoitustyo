package com.oh2harjoitustyo;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class ScoreSerialized implements Serializable {
    private int score;
    private String playerName;
    private Date date;

    public static Comparator<? super ScoreSerialized> comparator() {
        return null;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ScoreSerialized(int score, String playerName, Date date) {
        this.score = score;
        this.playerName = playerName;
        this.date = date;
    }

    public String dateToDisplayString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.getHours() + ":" + date.getMinutes());
        stringBuilder.append(" ");
        stringBuilder.append(date.getDate());
        stringBuilder.append(".");
        stringBuilder.append(date.getMonth());
        stringBuilder.append(".");
        stringBuilder.append(date.getYear());
        return stringBuilder.toString();
    }

    public static int baseScoreToActualScore(double score) {
        return (int) (10 * (int) (score * 10000d)/250d);
    }
    
    static Comparator <ScoreSerialized> comparator = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return ((ScoreSerialized) o2).getScore()-((ScoreSerialized) o1).getScore();
        }
    };

}
