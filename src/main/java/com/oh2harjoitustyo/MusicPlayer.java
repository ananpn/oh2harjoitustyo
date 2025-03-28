package com.oh2harjoitustyo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Random;
//import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
    static MediaPlayer mediaPlayer;

    public static void playGameMusic(){
        stopMusic();
        Random random = new Random();
        int randomNum = random.nextInt(0,3);
        String path = "src/main/resources/com/oh2harjoitustyo/GameMusic";
        path += randomNum + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void stopMusic(){
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }

    }

    public static void playMenuMusic(){
        stopMusic();
        Media media = new Media(new File("src/main/resources/com/oh2harjoitustyo/MenuMusic.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static boolean isPlaying(){
        return mediaPlayer != null;
    }

}
