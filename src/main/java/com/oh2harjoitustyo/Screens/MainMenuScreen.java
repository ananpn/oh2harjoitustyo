package com.oh2harjoitustyo.Screens;
import com.oh2harjoitustyo.SceneManager;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuScreen {

    public static void main(String[] args){
        
    }



    public static Scene createScene(SceneManager sceneManager){
        StackPane layout = new StackPane();
        layout.setMinSize(800, 800);
        layout.setAlignment(Pos.CENTER);

        Scene output = new Scene(layout);

        VBox menuBox = new VBox();

        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(e -> {
            sceneManager.showGameScreen();
        });

        Button highScoresButton = new Button("High Scores");
        highScoresButton.setOnAction(e -> {
            sceneManager.showHighScoreScreen();
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            System.exit(0);
        });

        menuBox.getChildren().addAll(newGameButton, highScoresButton, exitButton);
        layout.getChildren().add(menuBox);

        return output;



    }

}
