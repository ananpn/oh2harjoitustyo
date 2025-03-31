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

import static com.oh2harjoitustyo.Utils.BACKGROUNDCOLOR;


/**
 * Class for handling main menu
 * @author Antti Puuronen
 */
public class MainMenuScreen implements ScreenInterface {

    /**
     * Pane for drawing main menu
     */
    Pane screen;

    /**
     * Should be the project common SceneManager
     */
    SceneManager sceneManager;

    /** Returns the Pane screen where the main menu is drawn
     * @return MainMenuScreen.screen
     */
    @Override
    public Pane getScreen() {
        return screen;
    }

    /** Constructor creates new MainMenuScreen object with given SceneManager
     * @param sceneManager the common project Scenemanager
     */
    public MainMenuScreen(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }


    /**
     * Initializes the game and creates the Pane layout for drawing the main menu
     */
    @Override
    public void createScreen(){
        HighScoresManager.initializeHighScores();
        StackPane layout = new StackPane();
        layout.setStyle(BACKGROUNDCOLOR);
        layout.setMinSize(Utils.SCREEN_WIDTH, Utils.SCREEN_HEIGHT);
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
