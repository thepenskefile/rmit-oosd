package model;

import model.enums.PieceType;
import model.enums.TileType;
import model.interfaces.BoardTile;
import model.interfaces.GameEngine;

public abstract class SharkPiece extends BoardPiece {

    public static final int NUMBER_OF_LIVES_CAN_TAKE = 1;

    private int numberOfLivesCanTake;

    public SharkPiece(PieceType pieceType, Location location, String name) {
        super(pieceType, location, name);
        // Represents the number of eagle lives a shark piece can take when they land on the same tile as an eagle.
        this.numberOfLivesCanTake = NUMBER_OF_LIVES_CAN_TAKE;
    }

    public int getNumberOfLivesCanTake(){
        return numberOfLivesCanTake;
    }

    public void setNumberOfLivesCanTake(int numberOfLivesCanTake){
        this.numberOfLivesCanTake = numberOfLivesCanTake;
    }

    @Override
    public boolean isValidMove(BoardTile currentBoardTile, BoardTile nextBoardTile, GameEngine game){

        Location currentLocation = currentBoardTile.getLocation();
        Location nextLocation = nextBoardTile.getLocation();

        int diffedX = nextLocation.getXCoordinate() - currentLocation.getXCoordinate();
        int diffedY = nextLocation.getYCoordinate() - currentLocation.getYCoordinate();

        // Board pieces can only move to adjacent squares
        if((diffedX == 1 || diffedX == -1 || diffedX == 0) && (diffedY == 1 || diffedY == -1 || diffedY == 0)){
            //Shark can move on red or blue square in alternate mode
            if(game.getIsAlternateMode()){
                return true;
            }

            // Shark pieces can only move on blue tiles in regular mode
            if(nextBoardTile.getTileType() == TileType.WATER){
                return true;
            }
        }
        return false;
    }

}
