package com.oh2harjoitustyo.Screens;

import com.oh2harjoitustyo.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.time.Instant;
import java.util.Date;


/**
 * Class for handling the game over screen
 * @author Antti Puuronen
 */
public class GameOverScreen implements ScreenInterface {
    private double finalScore;

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    /**
     * Pane for drawing the game over screen
     */
    Pane screen;

    /**
     * Should be the project common SceneManager
     */
    SceneManager sceneManager;

    /** Constructor for GameOverScreen, sets GameOverScreen.sceneManager
     * @param sceneManager Should be the project common SceneManager
     */
    public GameOverScreen(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }


    /**
     * @return
     */
    public Pane getScreen() {
        return screen;
    }


    /**
     * Creates the game over screen on a StackPane object and sets is as GameOverScreen.screen
     */
    public void createScreen(){
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: #030303");
        layout.setMinSize(Utils.SCREEN_WIDTH, Utils.SCREEN_HEIGHT);
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

        HighScoresManager.readHighScores();
        if (HighScoresManager.checkNewScore(finalScore)) {
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
            doneButton.setOnAction(e -> {
                saveScoreAndLeave(playerNameField.getText());
            });
            layout.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    saveScoreAndLeave(playerNameField.getText());
                }
            });

            nameBox.setAlignment(Pos.CENTER);
            nameBox.setTranslateY(200);

            nameBox.getChildren().addAll(playerNameField, doneButton);
            layout.getChildren().add(nameBox);
            layout.getChildren().add(givePlayerName);
            layout.getChildren().add(newScoreText);
        };

        Button backButton = new Button("Back");
        backButton.setTranslateX(-400);
        backButton.setOnAction(e -> {
            sceneManager.showMainMenu();
        });

        layout.getChildren().add(backButton);

        screen = layout;
    }

    /** Saves the score with the given name, and leaves to main menu
     * @param name Player name
     */
    private void saveScoreAndLeave(String name){
        HighScoresManager.saveNewScore(new ScoreSerialized(
            (int) Math.round(finalScore),
            name,
            Date.from(Instant.now())
        ));
        sceneManager.showMainMenu();
    }


}
