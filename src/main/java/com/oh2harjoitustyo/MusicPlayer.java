package com.oh2harjoitustyo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Random;
//import javafx.scene.media.MediaPlayer;

public class MusicPlayer {

    private static final String menuMusicPath = "src/main/resources/com/oh2harjoitustyo/MenuMusic.mp3";

    static MediaPlayer mediaPlayer;

    static String currentlyPlayingPath = "";

    /**
     * Starts playing a random game music file
     */
    public static void playGameMusic(){
        stopMusic();
        Random random = new Random();
        int randomNum = random.nextInt(0,3);
        String path = "src/main/resources/com/oh2harjoitustyo/GameMusic";
        path += randomNum + ".mp3";
        currentlyPlayingPath = path;
        Media media = new Media(new File(currentlyPlayingPath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
        // Restarts playback when song ends
        mediaPlayer.setOnEndOfMedia(() ->
            playGameMusic()
        );
    }

    /**
     * Stops playback of audio files.
     */
    public static void stopMusic(){
        if (mediaPlayer != null){
            currentlyPlayingPath = "";
            mediaPlayer.stop();
        }

    }


    /**
     * Starts playing the menu music file
     */
    public static void playMenuMusic(){
        // This is to prevent music stopping when returning from the High Scores screen
        if (currentlyPlayingPath.equals(menuMusicPath)){
            return;
        }
        stopMusic();
        currentlyPlayingPath = menuMusicPath;
        Media media = new Media(new File(currentlyPlayingPath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
        // Restarts playback when song ends
        mediaPlayer.setOnEndOfMedia(() ->
            playMenuMusic()
        );
    }

}
