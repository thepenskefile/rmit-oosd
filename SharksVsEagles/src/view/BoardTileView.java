package view;

import controller.BoardTileController;
import model.enums.TileType;
import model.interfaces.BoardTile;
import model.interfaces.GameEngine;

import javax.swing.*;
import java.awt.*;

public class BoardTileView extends JPanel {

    private static final Color LAND = Color.RED;
    private static final Color WATER = Color.BLUE;

    BoardTileView(BoardTile boardTile, GameEngine gameEngine) {
        this.addMouseListener(new BoardTileController(gameEngine, boardTile));
        this.setBackground(boardTile.getTileType() == TileType.LAND ? LAND : WATER);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        validate();
    }

}
