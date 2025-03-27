package com.oh2harjoitustyo.Screens;
import com.oh2harjoitustyo.SceneManager;
import com.oh2harjoitustyo.Utils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenuScreen {


    public Pane createScreen(SceneManager sceneManager){
        StackPane layout = new StackPane();
        layout.setMinSize(Utils.screenWidth, Utils.screenHeight);
        layout.setAlignment(Pos.CENTER);

        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);

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

        return layout;



    }

}
