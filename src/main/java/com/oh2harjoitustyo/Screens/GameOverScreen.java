package com.oh2harjoitustyo.Screens;

import com.oh2harjoitustyo.HighScoresManager;
import com.oh2harjoitustyo.SceneManager;
import com.oh2harjoitustyo.ScoreSerialized;
import com.oh2harjoitustyo.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.time.Instant;
import java.util.Date;

public class GameOverScreen {
    private double finalScore;

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    HighScoresManager highScoresManager = new HighScoresManager();


    public Pane createScreen(SceneManager sceneManager){
        StackPane layout = new StackPane();
        layout.setMinSize(Utils.screenWidth, Utils.screenHeight);
        layout.setAlignment(Pos.CENTER);

        Text highScoreText = new Text("Game Over");
        Text finalScoreText = new Text("Score: " + Math.round(finalScore));
        finalScoreText.setTranslateY(100);

        layout.getChildren().add(highScoreText);
        layout.getChildren().add(finalScoreText);

        SimpleStringProperty playerNameProperty = new SimpleStringProperty("Antti");

        highScoresManager.readHighScores();
        highScoresManager.checkNewScore(new ScoreSerialized(
            (int) Math.round(finalScore),
            playerNameProperty.getValue(),
            Date.from(Instant.now())
        ));

        Button backButton = new Button("Back");
        backButton.setTranslateX(-400);
        backButton.setOnAction(e -> {
            sceneManager.showMainMenu();
        });
        layout.getChildren().add(backButton);


        return layout;



    }




}
