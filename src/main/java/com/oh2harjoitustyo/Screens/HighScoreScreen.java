package com.oh2harjoitustyo.Screens;
import com.oh2harjoitustyo.HighScoresManager;
import com.oh2harjoitustyo.SceneManager;
import com.oh2harjoitustyo.Utils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HighScoreScreen  {

    HighScoresManager highScoresManager = new HighScoresManager();


    public Pane createScreen(SceneManager sceneManager){
        StackPane layout = new StackPane();
        layout.setMinSize(Utils.screenWidth, Utils.screenHeight);
        layout.setAlignment(Pos.CENTER);

        Text highScoreScreenText = new Text("High Scores");
        highScoreScreenText.setTranslateY(+40 - Utils.screenHeight/2);
        layout.getChildren().add(highScoreScreenText);


        VBox highScoresBox = new VBox();

        highScoresManager.readHighScores();
        highScoresManager.highScores.forEach( highScore -> {
            Text highScoreText = new Text(highScore.getScore() + " " + highScore.getPlayerName());
            highScoresBox.getChildren().add(highScoreText);

        });
        highScoresBox.setAlignment(Pos.CENTER);
        highScoresBox.setSpacing(10);
        layout.getChildren().add(highScoresBox);


        Button backButton = new Button("Back");
        backButton.setTranslateX(-400);
        backButton.setOnAction(e -> {
            sceneManager.showMainMenu();
        });
        layout.getChildren().add(backButton);


        return layout;



    }




}
