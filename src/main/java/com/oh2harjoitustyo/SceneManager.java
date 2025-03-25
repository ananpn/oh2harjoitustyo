package com.oh2harjoitustyo;

import com.oh2harjoitustyo.Screens.HighScoreScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private final Stage stage;
    private Scene mainMenuScene;
    private Scene gameScene;
    private Scene gameOverScene;
    private Scene highScoreScene;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void setMainMenuScene(Scene scene) {
        this.mainMenuScene = scene;
    }

    public void setGameScene(Scene scene) {
        this.gameScene = scene;
    }

    public void setGameOverScene(Scene scene) {
        this.gameOverScene = scene;
    }

    public void showMainMenu() {
        stage.setScene(mainMenuScene);
        stage.setTitle("Main Menu");
    }

    public void showGameScreen() {
        stage.setScene(gameScene);
        stage.setResizable(false);
        stage.setTitle("Game");
    }

    public void showGameOverScreen() {
        stage.setScene(gameOverScene);
        stage.setTitle("Game Over");
    }

    public void showHighScoreScreen() {
        stage.setScene(highScoreScene);
    }
}
