package model;

import model.enums.PieceType;
import model.enums.TileType;
import model.interfaces.BoardTile;
import model.interfaces.GameEngine;

public class GreatWhitePiece extends SharkPiece {

    private static String name = "GWS";

    public GreatWhitePiece (PieceType pieceType, Location location) {
        super(pieceType, location, name);
    }

    @Override
    public boolean isValidMove(BoardTile currentBoardTile, BoardTile nextBoardTile, GameEngine game) {

        // Sharks can only move on blue tiles (unless in alternate mode), and so any further validation is not necessary
        if(nextBoardTile.getTileType() != TileType.WATER){
            return false;
        }

        Location currentLocation = currentBoardTile.getLocation();
        Location nextLocation = nextBoardTile.getLocation();

        int diffedX = nextLocation.getXCoordinate() - currentLocation.getXCoordinate();
        int diffedY = nextLocation.getYCoordinate() - currentLocation.getYCoordinate();

        if((diffedX == 1 && diffedY == -2) || (diffedX == -1 && diffedY == -2) || (diffedX == -2 && diffedY == -1) ||
                (diffedX == -2 && diffedY == 1) || (diffedX == -1 && diffedY == 2) || (diffedX == 1 && diffedY == 2) ||
                (diffedX == 2 && diffedY == 1) || (diffedX == 2 && diffedY == -1)){
            return true;
        }

        return false;
    }

    @Override
    public GreatWhitePiece clone() throws CloneNotSupportedException {
        GreatWhitePiece copy = new GreatWhitePiece(this.getPieceType(), this.getLocation().clone());
        return copy;
    }
}
