package controller;

import model.interfaces.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeModeController implements ActionListener {

    private GameEngine gameEngine;

    public ChangeModeController(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(((JToggleButton)e.getSource()).isSelected()){
            JOptionPane.showMessageDialog(new JFrame(), "Enabled alternate mode");
            gameEngine.setIsAlternateMode(true);
        }
        else {
            JOptionPane.showMessageDialog(new JFrame(),"Disabled alternate mode");
            gameEngine.setIsAlternateMode(false);
        }
    }

    public void setGameEngine(GameEngine gameEngine) { this.gameEngine = gameEngine; }
}
