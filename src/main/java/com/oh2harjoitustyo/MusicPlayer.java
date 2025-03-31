package com.oh2harjoitustyo;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.Random;

/**
 * Handles music in the app
 * @author Antti Puuronen
 */
public class MusicPlayer {

    /**
     * Path of menu music
     */
    private static final String MENU_MUSIC_PATH = "src/main/resources/com/oh2harjoitustyo/MenuMusic.mp3";

    /**
     * Project common mediaplayer
     */
    private static MediaPlayer mediaPlayer;

    /**
     * Path of currently playing music file relative to project root
     */
    private static String currentlyPlayingPath = "";

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
        mediaPlayer.setVolume(0.4);
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
        if (currentlyPlayingPath.equals(MENU_MUSIC_PATH)){
            return;
        }
        stopMusic();
        currentlyPlayingPath = MENU_MUSIC_PATH;
        Media media = new Media(new File(currentlyPlayingPath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.4);
        mediaPlayer.play();
        // Restarts playback when song ends
        mediaPlayer.setOnEndOfMedia(() -> {
                currentlyPlayingPath = "";
                playMenuMusic();
            }
        );
    }

}
