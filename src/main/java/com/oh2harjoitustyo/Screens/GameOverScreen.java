package com.oh2harjoitustyo.Screens;

import com.oh2harjoitustyo.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
        layout.setStyle("-fx-background-color: #030303");
        layout.setMinSize(Utils.screenWidth, Utils.screenHeight);
        layout.setAlignment(Pos.CENTER);

        Text highScoreText = new Text("Game Over");
        highScoreText.setStroke(Color.CRIMSON);
        highScoreText.setScaleX(3);
        highScoreText.setScaleY(3);
        highScoreText.setTranslateY(-100);
        Text finalScoreText = new Text("Score: " + Math.round(finalScore));
        finalScoreText.setStroke(Color.CHARTREUSE);
        finalScoreText.setTranslateY(000);

        layout.getChildren().add(highScoreText);
        layout.getChildren().add(finalScoreText);

        SimpleStringProperty playerNameProperty = new SimpleStringProperty("Antti");

        highScoresManager.readHighScores();
        if (highScoresManager.checkNewScore(finalScore)) {
            Text newScoreText = new Text("New High Score!");
            newScoreText.setStroke(Color.CHARTREUSE);
            newScoreText.setTranslateY(100);

            HBox nameBox = new HBox();
            Text givePlayerName = new Text("Name:");
            givePlayerName.setStroke(Color.CHARTREUSE);
            givePlayerName.setTranslateY(150);
            TextField playerNameField = new TextField("");
            playerNameField.requestFocus();
            Button doneButton = new Button("Done");


            nameBox.setAlignment(Pos.CENTER);
            nameBox.setTranslateY(200);

            nameBox.getChildren().addAll(playerNameField, doneButton);
            layout.getChildren().add(nameBox);
            layout.getChildren().add(givePlayerName);
            layout.getChildren().add(newScoreText);

            doneButton.setOnAction(e -> {
                playerNameProperty.setValue(playerNameField.getText());
                highScoresManager.saveNewScore(new ScoreSerialized(
                    (int) Math.round(finalScore),
                    playerNameProperty.getValue(),
                    Date.from(Instant.now())
                ));
                sceneManager.showMainMenu();
            });


        };


        Button backButton = new Button("Back");
        backButton.setTranslateX(-400);
        backButton.setOnAction(e -> {
            sceneManager.showMainMenu();
        });
        layout.getChildren().add(backButton);


        return layout;



    }




}
