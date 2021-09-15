package view;

import controller.BoardPieceController;
import model.BoardPiece;
import model.EaglePiece;
import model.enums.PieceType;
import model.interfaces.GameEngine;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

public class BoardPieceView extends JPanel {

    private static final Color BACKGROUND_COLOUR = new Color(242, 242, 242);
    private static final Color SELECTED_BACKGROUND_COLOUR = new Color(181, 252, 174);
    private static final Color DISABLED_BACKGROUND_COLOUR = new Color(160, 160, 160);
    private static final Color DISABLED_TEXT_COLOUR = new Color(190, 190, 190);


    public BoardPieceView(BoardPiece boardPiece, GameEngine gameEngine) {
        Color backgroundColour = boardPiece.getIsSelected() ? SELECTED_BACKGROUND_COLOUR : BACKGROUND_COLOUR;
        JLabel pieceText = new JLabel(boardPiece.getName());
        JLabel numberOfLivesText = new JLabel("");

        // If the board piece is an eagle, show the number of lives
        if(boardPiece.getPieceType() == PieceType.EAGLE){
            numberOfLivesText.setText(Integer.toString(((EaglePiece) boardPiece).getNumberOfLives()));
        }

        if(!Utils.doesPieceMatchPlayer(boardPiece.getPieceType(), gameEngine.getCurrentPlayer().getPlayerType())){
            backgroundColour = DISABLED_BACKGROUND_COLOUR;
            pieceText.setForeground(DISABLED_TEXT_COLOUR);
            numberOfLivesText.setForeground(DISABLED_TEXT_COLOUR);
        }
        this.setBackground(backgroundColour);
        this.add(pieceText);
        this.add(numberOfLivesText);
        this.addMouseListener(new BoardPieceController(gameEngine, boardPiece, this));
    }
}
