package com.oh2harjoitustyo;

import com.oh2harjoitustyo.Screens.GameOverScreen;
import com.oh2harjoitustyo.Screens.GameScreen;
import com.oh2harjoitustyo.Screens.HighScoreScreen;
import com.oh2harjoitustyo.Screens.MainMenuScreen;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneManager {
    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return stage.getScene();
    }

    public void setScene(Pane newRoot) {
        Scene scene = new Scene(newRoot);
        stage.setScene(scene);
    }

    public void showMainMenu() {
        MusicPlayer.playMenuMusic();
        setScene(new MainMenuScreen().createScreen(this));
        stage.setTitle("Main Menu");
    }

    public void showGameScreen() {
        MusicPlayer.playGameMusic();
        GameScreen gameScreen = new GameScreen(this);
        gameScreen.createScreen();
        setScene(gameScreen.getScreen());
        gameScreen.startGame(getScene());
        stage.setTitle("Game");
    }

    public void showGameOverScreen(double finalScore) {
        GameOverScreen gameOverScreen = new GameOverScreen();
        gameOverScreen.setFinalScore(finalScore);
        setScene(gameOverScreen.createScreen(this));
        stage.setTitle("Game Over");
    }

    public void showHighScoreScreen() {
        setScene(new HighScoreScreen().createScreen(this));
        stage.setTitle("High Scores");
    }
}
