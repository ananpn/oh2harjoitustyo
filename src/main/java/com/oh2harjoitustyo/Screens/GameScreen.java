package com.oh2harjoitustyo.Screens;

import com.oh2harjoitustyo.GameEngine;
import com.oh2harjoitustyo.MusicPlayer;
import com.oh2harjoitustyo.SceneManager;
import com.oh2harjoitustyo.Utils;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;

import static com.oh2harjoitustyo.Utils.backGroundBarColor;
import static com.oh2harjoitustyo.Utils.backGroundColor;


public class GameScreen  {

    private SceneManager sceneManager;

    public Pane screen;

    public GameScreen(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public Pane getScreen(){
        return screen;
    }


    public void createScreen(){
        StackPane layout = new StackPane();
        layout.setStyle(backGroundColor);
        StackPane topBar = new StackPane();
        topBar.setMinSize(Utils.screenWidth, 100);
        topBar.setMaxSize(Utils.screenWidth, 100);
        topBar.setStyle(backGroundBarColor);
        layout.getChildren().add(topBar);
        topBar.setTranslateY(Utils.screenHeight/2);

        StackPane bottomBar = new StackPane();
        bottomBar.setMinSize(Utils.screenWidth, 100);
        bottomBar.setMaxSize(Utils.screenWidth, 100);
        bottomBar.setStyle(backGroundBarColor);
        layout.getChildren().add(bottomBar);
        bottomBar.setTranslateY(-Utils.screenHeight/2);
        
        layout.setMinSize(Utils.screenWidth, Utils.screenHeight);
        screen = layout;

    }

    public void startGame(Scene scene){
        GameEngine gameEngine = new GameEngine(screen, sceneManager);
        gameEngine.start(scene);

    }




}
