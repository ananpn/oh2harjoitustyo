package com.oh2harjoitustyo.Screens;

import javafx.scene.layout.Pane;

/**
 * Interface for the various screens in the game
 * @author Antti Puuronen
 */
interface ScreenInterface {

    /**
     * @return Pane where the screen is drawn
     */
    Pane getScreen();

    /**
     * Creates Pane
     */
    void createScreen();
}
