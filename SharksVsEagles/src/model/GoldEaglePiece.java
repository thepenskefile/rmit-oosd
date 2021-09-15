package model;

import model.enums.PieceType;
import model.interfaces.BoardTile;
import model.interfaces.GameEngine;

public class GoldEaglePiece extends EaglePiece {

    private static String name = "GE";
    private static int NUMBER_OF_LIVES = 1;
    private static int NUMBER_OF_MOVES_REQUIRED_TO_CAPTURE_TILE = 2;

    public GoldEaglePiece (PieceType pieceType, Location location) {
        super(pieceType, location, name);
        super.setNumberOfLives(NUMBER_OF_LIVES);
        super.setNumberMovesRequiredToCaptureTile(NUMBER_OF_MOVES_REQUIRED_TO_CAPTURE_TILE);
    }

    @Override
    public boolean isValidMove(BoardTile currentBoardTile, BoardTile nextBoardTile, GameEngine game) {

        Location currentLocation = currentBoardTile.getLocation();
        Location nextLocation = nextBoardTile.getLocation();

        int diffedX = nextLocation.getXCoordinate() - currentLocation.getXCoordinate();
        int diffedY = nextLocation.getYCoordinate() - currentLocation.getYCoordinate();

        if((diffedX == 1 || diffedX == -1) && (diffedY == 1 || diffedY == -1)){
            return true;
        }

        return false;
    }

    @Override
    public GoldEaglePiece clone() throws CloneNotSupportedException {
        GoldEaglePiece copy = new GoldEaglePiece(this.getPieceType(), this.getLocation().clone());
        return copy;
    }
}
