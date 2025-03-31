package com.oh2harjoitustyo.Screens;

import com.oh2harjoitustyo.GameEngine;
import com.oh2harjoitustyo.SceneManager;
import com.oh2harjoitustyo.Utils;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import static com.oh2harjoitustyo.Utils.BACKGROUNDBARCOLOR;
import static com.oh2harjoitustyo.Utils.BACKGROUNDCOLOR;



/**
 * Class for handling the game screen
 * @author Antti Puuronen
 */
public class GameScreen implements ScreenInterface {


    /**
     * SceneManager, should be the common project SceneManager
     */
    private SceneManager sceneManager;


    /**
     * Screen for drawing the game
     */
    public Pane screen;

    /** Constructor, sets GameScreen.sceneManager
     * @param sceneManager The common project SceneManager
     */
    public GameScreen(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }


    /** Returns the Pane where the game is drawn
     * @return returns GameScreen.screen
     */
    @Override
    public Pane getScreen(){
        return screen;
    }


    /**
     * Creates the StackPane for drawing the game
     */
    public void createScreen(){

        StackPane layout = new StackPane();
        layout.setStyle(BACKGROUNDCOLOR);

        // top and bottom bar to show bounds
        StackPane topBar = new StackPane();
        topBar.setMinSize(Utils.SCREEN_WIDTH, 140);
        topBar.setMaxSize(Utils.SCREEN_WIDTH, 140);
        topBar.setStyle(BACKGROUNDBARCOLOR);
        layout.getChildren().add(topBar);
        topBar.setTranslateY(Utils.SCREEN_HEIGHT /2);

        StackPane bottomBar = new StackPane();
        bottomBar.setMinSize(Utils.SCREEN_WIDTH, 140);
        bottomBar.setMaxSize(Utils.SCREEN_WIDTH, 140);
        bottomBar.setStyle(BACKGROUNDBARCOLOR);
        layout.getChildren().add(bottomBar);
        bottomBar.setTranslateY(-Utils.SCREEN_HEIGHT /2);
        
        layout.setMinSize(Utils.SCREEN_WIDTH, Utils.SCREEN_HEIGHT);
        screen = layout;

    }

    /** Starts the GameEngine
     * @param scene Scene object, where the game is drawn
     */
    public void startGame(Scene scene){
        GameEngine gameEngine = new GameEngine(screen, sceneManager);
        gameEngine.start(scene);

    }




}
