package com.oh2harjoitustyo;

import com.oh2harjoitustyo.Screens.GameScreen;
import com.oh2harjoitustyo.Screens.MainMenuScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Random;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        SceneManager sceneManager = new SceneManager(stage);

        sceneManager.setMainMenuScene(MainMenuScreen.createScene(sceneManager));
        sceneManager.setGameScene(GameScreen.createScene(sceneManager));
        //sceneManager.setGameOverScene(.createScene(sceneManager, root));
        //sceneManager.setHighScoreScene()
        sceneManager.showMainMenu();

        //stage.setTitle("");
        stage.show();


    }
}
