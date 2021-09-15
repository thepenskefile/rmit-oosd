package model.decorator;

import model.BoardPiece;
import model.Location;
import model.Obstacle;
import model.enums.PieceType;
import model.enums.TileType;
import model.interfaces.Board;
import model.interfaces.BoardTile;
import utils.Utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ThreadLocalRandom;

public class ObstaclesDecorator extends BoardDecorator{

    private static final int NUM_OBSTACLES = 6;
    private Deque<Obstacle> obstacles = new ArrayDeque<>();
    private BoardTile[][] boardTiles;

    public ObstaclesDecorator(Board board){
        super(board);
    }

    @Override
    public void initialiseBoard(){
        super.initialiseBoard();
        initialiseObstacles();
    }

    /**Initialise obstacles at random board tile positions
     * (excluding the first and last column where board pieces are initialised) */
    private void initialiseObstacles(){
        boardTiles = super.getBoardTiles();

        while(obstacles.size() < NUM_OBSTACLES){
            int x = ThreadLocalRandom.current().nextInt(1, Utils.BOARD_ROWS - 1);
            int y = ThreadLocalRandom.current().nextInt(1, Utils.BOARD_COLUMNS - 1);

            if(!boardTiles[x][y].getIsOccupied()) {
                Location obstacleLocation = new Location(x, y);
                Obstacle obstacle = new Obstacle(obstacleLocation);
                obstacles.add(obstacle);

                boardTiles[obstacle.getLocation().getXCoordinate()]
                        [obstacle.getLocation().getYCoordinate()].setIsOccupied(true);
                boardTiles[obstacle.getLocation().getXCoordinate()]
                        [obstacle.getLocation().getYCoordinate()].setIsOccupiedByObstacle(true);
            }
        }
    }

    public Deque<Obstacle> getObstacles(){
        return obstacles;
    }

    @Override
    public BoardTile[][] getBoardTiles() {
        return new BoardTile[0][];
    }

    @Override
    public void setBoardTiles(BoardTile[][] boardTiles) {
        this.boardTiles = boardTiles;
    }

    @Override
    public Deque<BoardPiece> getBoardPieces() {
        return null;
    }

    @Override
    public void setBoardPieces(Deque<BoardPiece> boardPieces) {

    }

    @Override
    public BoardTile[][] getBoardTilesType(TileType tileType) {
        return new BoardTile[0][];
    }

    @Override
    public Deque<BoardPiece> getBoardPiecesType(PieceType pieceType) {
        return null;
    }

    @Override
    public int getNumOfTiles(TileType tileType) {
        return 0;
    }

    @Override
    public int getNumOfPieces(PieceType pieceType) {
        return 0;
    }

    @Override
    public boolean doesTileTypeExist(TileType tileType) {
        return false;
    }
}
