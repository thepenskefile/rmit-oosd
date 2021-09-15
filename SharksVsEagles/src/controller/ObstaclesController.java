package controller;

import model.GameSettings;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ObstaclesController implements ItemListener {

    private GameSettings gameSettings;

    public ObstaclesController(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            //If checkbox is ticked then initialise board with obstacles
            gameSettings.setIncludeObstacles(true);
        }
    }
}
