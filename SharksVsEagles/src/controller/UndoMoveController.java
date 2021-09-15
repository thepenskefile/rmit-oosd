package controller;

import model.interfaces.GameEngine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoMoveController implements ActionListener {

    private GameEngine gameEngine;

    public UndoMoveController(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        gameEngine.undoMove();
    }

    public void setGameEngine(GameEngine gameEngine) { this.gameEngine = gameEngine; }
}
