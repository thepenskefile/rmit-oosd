package controller;

import model.BoardPiece;
import model.interfaces.GameEngine;
import utils.Utils;
import view.BoardPieceView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPieceController extends MouseAdapter {

    private GameEngine gameEngine;
    private BoardPiece boardPiece;
    private BoardPieceView boardPieceView;

    public BoardPieceController(GameEngine gameEngine, BoardPiece boardPiece, BoardPieceView boardPieceView){
        this.gameEngine = gameEngine;
        this.boardPiece = boardPiece;
        this.boardPieceView = boardPieceView;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        //Only listen for mouse clicks on a board piece if there has not been a tile selected first
        if(gameEngine.getCurrentMove() == null){
            // Only allow shark players to move shark pieces, and only allow eagle players to move eagle pieces
            if(Utils.doesPieceMatchPlayer(boardPiece.getPieceType(), gameEngine.getCurrentPlayer().getPlayerType())){
                gameEngine.initialiseMove(boardPiece);

                //When piece is selected then allow alternate mode to be enabled
                gameEngine.enableAlternateMode();
            }
            else{
                String playerTypeString = gameEngine.getCurrentPlayer().getPlayerTypeString();
                String pieceTypeString = boardPiece.getPieceTypeString();
                gameEngine.logText(playerTypeString + " player cannot move " + pieceTypeString + " piece");
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        if(!boardPiece.getIsSelected() &&
                Utils.doesPieceMatchPlayer(boardPiece.getPieceType(), gameEngine.getCurrentPlayer().getPlayerType())){
            boardPieceView.setBackground(new Color(210, 210, 210));
            boardPieceView.setCursor(new Cursor(Cursor.HAND_CURSOR));
            boardPieceView.revalidate();
            boardPieceView.repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        if(!boardPiece.getIsSelected() &&
                Utils.doesPieceMatchPlayer(boardPiece.getPieceType(), gameEngine.getCurrentPlayer().getPlayerType())){
            boardPieceView.setBackground(new Color(242, 242, 242));
            boardPieceView.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            boardPieceView.revalidate();
            boardPieceView.repaint();
        }
    }
}
