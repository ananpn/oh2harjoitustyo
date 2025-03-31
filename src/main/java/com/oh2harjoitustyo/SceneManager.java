package com.oh2harjoitustyo;

import com.oh2harjoitustyo.Screens.GameOverScreen;
import com.oh2harjoitustyo.Screens.GameScreen;
import com.oh2harjoitustyo.Screens.HighScoreScreen;
import com.oh2harjoitustyo.Screens.MainMenuScreen;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Handles changing the scenes or screens of the app
 * @author Antti
 */
public class SceneManager {

    /**
     * The common project Stage, where various scenes are set
     */
    private final Stage stage;


    /** Constructor for SceneManager with given Stage
     * @param stage JavaFX stage
     */
    public SceneManager(Stage stage) {
        this.stage = stage;
    }


    /** Returns the current scene set on the common project stage
     * @return SceneManager.stage.getScene()
     */
    public Scene getScene() {
        return stage.getScene();
    }

    /** Creates a Scene from the given Pane newRoot, and sets the scene on SceneManager.stage
     * @param newRoot Pane to construct Ccene to set on SceneManager.stage
     */
    public void setScene(Pane newRoot) {
        Scene scene = new Scene(newRoot);
        stage.setScene(scene);
    }


    /**
     * Shows main menu and plays main menu music
     */
    public void showMainMenu() {
        MusicPlayer.playMenuMusic();
        MainMenuScreen mainMenuScreen = new MainMenuScreen(this);
        mainMenuScreen.createScreen();
        setScene(mainMenuScreen.getScreen());
        stage.setTitle("Main Menu");
    }

    /**
     * Starts the game and plays game music
     */
    public void showGameScreen() {
        MusicPlayer.playGameMusic();
        GameScreen gameScreen = new GameScreen(this);
        gameScreen.createScreen();
        setScene(gameScreen.getScreen());
        gameScreen.startGame(getScene());
        stage.setTitle("Game");
    }


    /** Shows game over screen
     * @param finalScore double, score the player attained before dying
     */
    public void showGameOverScreen(double finalScore) {
        GameOverScreen gameOverScreen = new GameOverScreen(this);
        gameOverScreen.setFinalScore(finalScore);
        gameOverScreen.createScreen();
        setScene(gameOverScreen.getScreen());
        stage.setTitle("Game Over");
    }

    /**
     * Shows the high scores screen
     */
    public void showHighScoreScreen() {
        HighScoreScreen highScoreScreen = new HighScoreScreen(this);
        highScoreScreen.createScreen();
        setScene(highScoreScreen.getScreen());
        stage.setTitle("High Scores");
    }
}
