package com.oh2harjoitustyo;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /** Starts the app. First shows main menu.
     * @param stage Root stage
     */
    @Override
    public void start(Stage stage) {

        stage.setResizable(false);
        stage.setMaxHeight(Utils.screenHeight);
        stage.setMaxWidth(Utils.screenWidth);
        stage.setMinHeight(Utils.screenHeight);
        stage.setMinWidth(Utils.screenWidth);
        SceneManager sceneManager = new SceneManager(stage);

        sceneManager.showMainMenu();

        //stage.setTitle("");
        stage.show();


    }
}
