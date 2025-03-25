package com.oh2harjoitustyo.Screens;

import com.oh2harjoitustyo.GameEngine;
import com.oh2harjoitustyo.SceneManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;



public class GameScreen extends Application {

    private double rootWidth = 1000;
    private double rootHeight = 1000;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        StackPane root = new StackPane();
        root.setMinSize(rootWidth, rootHeight);
        Scene scene = new Scene(root);


        Random random = new Random();



        GameEngine gameEngine = new GameEngine(scene, root);

        stage.setScene(scene);
        stage.setTitle("");
        stage.show();
        gameEngine.start();


    }

    public static Scene createScene(SceneManager sceneManager){
        StackPane layout = new StackPane();
        layout.setMinSize(800, 800);
        
        Scene output = new Scene(layout);

        GameEngine gameEngine = new GameEngine(output, layout);

        gameEngine.start();
        return output;





    }




}
