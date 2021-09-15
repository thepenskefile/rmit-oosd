package controller;

import model.interfaces.BoardTile;
import model.interfaces.GameEngine;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardTileController extends MouseAdapter {

    private GameEngine gameEngine;
    private BoardTile boardTile;

    public BoardTileController(GameEngine gameEngine, BoardTile boardTile){
        this.gameEngine = gameEngine;
        this.boardTile = boardTile;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        // Only listen for mouse clicks on a tile if there has been a board piece selected first
        if(gameEngine.getCurrentMove() != null){
            gameEngine.finaliseMove(this.boardTile);
            gameEngine.clearCurrentMove();

            if(gameEngine.getCurrentPlayer().getIsCurrentPlayerWinner()){
                //A player has won and game is over
                JOptionPane.showMessageDialog(new JFrame(),
                        gameEngine.getCurrentPlayer().getPlayerTypeString() + " wins!",
                        "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

            //When current move is finished then exit alternate mode
            // and disable alternate mode button as piece is not selected
            gameEngine.disableAlternateMode();
        }
    }
}
