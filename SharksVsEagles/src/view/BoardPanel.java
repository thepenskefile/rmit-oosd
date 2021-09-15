package view;

import model.BoardPiece;
import model.Obstacle;
import model.decorator.ObstaclesDecorator;
import model.interfaces.BoardTile;
import model.interfaces.GameEngine;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Deque;
import java.util.Iterator;

public class BoardPanel extends JPanel{
    private GameEngine gameEngine;
    private static final int ROWS = Utils.BOARD_ROWS;
    private static final int COLUMNS = Utils.BOARD_COLUMNS;

    private BoardTile[][] boardTiles;
    private BoardTileView[][] boardTilesView;

    private Deque<BoardPiece> boardPieces;
    private Deque<Obstacle> obstacles;

    public BoardPanel(GameEngine gameEngine) {
        super(new GridLayout(ROWS,COLUMNS));
        this.gameEngine = gameEngine;
        this.boardTiles = gameEngine.getBoard().getBoardTiles();
        this.boardTilesView = new BoardTileView[ROWS][COLUMNS];
        this.boardPieces = gameEngine.getBoard().getBoardPieces();
        this.obstacles = ((ObstaclesDecorator) gameEngine.getObstaclesDecorator()).getObstacles();

        addTiles();
        setSize(360,90);
        addPieces();
        addObstacles();
        validate();
    }

    private void addTiles(){
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                final BoardTileView boardTile = new BoardTileView(boardTiles[i][j], gameEngine);
                boardTilesView[i][j] = boardTile;
                add(boardTile);
            }
        }
    }

    private void addPieces() {
        BoardPiece boardPiece;
        for(Iterator<BoardPiece> itr = boardPieces.iterator(); itr.hasNext();)  {
            boardPiece = itr.next();
            boardTilesView[boardPiece.getLocation().getXCoordinate()][boardPiece.getLocation().getYCoordinate()]
                    .add(new BoardPieceView(boardPiece, gameEngine));
        }
    }

    private void addObstacles(){
        Obstacle obstacle;
        for(Iterator<Obstacle> itr = obstacles.iterator(); itr.hasNext();)  {
            obstacle = itr.next();
            boardTilesView[obstacle.getLocation().getXCoordinate()][obstacle.getLocation().getYCoordinate()]
                    .add(new ObstacleView(obstacle));
        }
    }

    public void updateBoard(){
        removeAll();
        boardTiles = gameEngine.getBoard().getBoardTiles();
        boardTilesView = new BoardTileView[ROWS][COLUMNS];
        boardPieces = gameEngine.getBoard().getBoardPieces();
        addTiles();
        addPieces();
        addObstacles();
        revalidate();
        repaint();
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        updateBoard();
    }

}
