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

public class HighScoreScreen  implements ScreenInterface {
    Pane screen;

    SceneManager sceneManager;

    public Pane getScreen() {
        return screen;
    }

    /** Constructor, sets HighScoreScreen.sceneManager
     * @param sceneManager The common project SceneManager
     */
    public HighScoreScreen(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }


    public void createScreen(){
        StackPane layout = new StackPane();
        layout.setStyle(backGroundColor);
        layout.setMinSize(Utils.screenWidth, Utils.screenHeight);
        layout.setAlignment(Pos.CENTER);

        Text highScoreScreenText = new Text("High Scores");
        highScoreScreenText.setStroke(Color.CHARTREUSE);
        highScoreScreenText.setTranslateY(+40 - Utils.screenHeight/2);
        layout.getChildren().add(highScoreScreenText);


        VBox highScoresBox = new VBox();

        HighScoresManager.initializeHighScores();
        HighScoresManager.readHighScores();
        if (HighScoresManager.highScores != null) {
            HighScoresManager.highScores.forEach( highScore -> {
                Text highScoreText = new Text(highScore.getScore() + " " + highScore.getPlayerName());
                highScoreText.setStroke(Color.CYAN);
                highScoresBox.getChildren().add(highScoreText);

            });
        }
        highScoresBox.setAlignment(Pos.CENTER);
        highScoresBox.setSpacing(10);
        layout.getChildren().add(highScoresBox);


        Button backButton = new Button("Back");
        backButton.setTranslateX(-400);
        backButton.setOnAction(e -> {
            sceneManager.showMainMenu();
        });
        layout.getChildren().add(backButton);

        Button clearButton = new Button("Clear High Scores");
        clearButton.setTranslateX(450);
        clearButton.setTranslateY(-40 + Utils.screenHeight/2);
        clearButton.setOnAction(e -> {
            HighScoresManager.clearHighScores();
            highScoresBox.getChildren().clear();
        });
        layout.getChildren().add(clearButton);


        screen = layout;



    }




}
