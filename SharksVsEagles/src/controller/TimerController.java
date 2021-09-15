package controller;

import model.interfaces.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerController implements ActionListener{

    private JLabel lblTimer;
    private GameEngine gameEngine;

    public TimerController (JLabel lblTimer, GameEngine gameEngine) {
        this.lblTimer = lblTimer;
        this.gameEngine = gameEngine;
    }

    public void actionPerformed(ActionEvent e) {
        lblTimer.setText("Timer: " + gameEngine.getTimeLeft());

        if(gameEngine.getTimeLeft() > 0)
            gameEngine.setTimeLeft(gameEngine.getTimeLeft() - 1);
        else if(gameEngine.getTimeLeft() == 0) {
            gameEngine.turnTimedOut();
            gameEngine.setTimeLeft(gameEngine.getTurnLength());
        }
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
}
