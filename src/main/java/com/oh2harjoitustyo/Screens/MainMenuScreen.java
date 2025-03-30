package com.oh2harjoitustyo.Screens;
import com.oh2harjoitustyo.HighScoresManager;
import com.oh2harjoitustyo.SceneManager;
import com.oh2harjoitustyo.Utils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static com.oh2harjoitustyo.Utils.backGroundColor;

public class MainMenuScreen implements ScreenInterface {
    Pane screen;

    SceneManager sceneManager;

    public Pane getScreen() {
        return screen;
    }

    public MainMenuScreen(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }


    public void createScreen(){
        this.sceneManager = sceneManager;
        HighScoresManager.initializeHighScores();



        StackPane layout = new StackPane();
        layout.setStyle(backGroundColor);
        layout.setMinSize(Utils.screenWidth, Utils.screenHeight);
        layout.setAlignment(Pos.CENTER);

        VBox menuBox = new VBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setSpacing(10);

        Text menuText = new Text("NEON PANIC");
        menuText.setScaleX(3);
        menuText.setScaleY(3);
        menuText.setStroke(Color.DEEPPINK);
        menuBox.getChildren().add(menuText);
        menuText.setTranslateY(-100);

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
        screen = layout;



    }

}
