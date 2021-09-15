package model;

import model.enums.PieceType;
import model.interfaces.BoardTile;
import model.interfaces.GameEngine;

public abstract class EaglePiece extends BoardPiece{

    private int numberOfLives;
    private int numberOfMovesStayedOnTile;
    private int numberOfMovesRequiredToCaptureTile;

    public EaglePiece(PieceType pieceType, Location location, String name) {
        super(pieceType, location, name);
        this.numberOfMovesStayedOnTile = 0;
    }

    @Override
    public boolean isValidMove(BoardTile currentBoardTile, BoardTile nextBoardTile, GameEngine game){

        Location currentLocation = currentBoardTile.getLocation();
        Location nextLocation = nextBoardTile.getLocation();

        int diffedX = nextLocation.getXCoordinate() - currentLocation.getXCoordinate();
        int diffedY = nextLocation.getYCoordinate() - currentLocation.getYCoordinate();

        // Board pieces can only move to adjacent squares
        if((diffedX == 1 || diffedX == -1 || diffedX == 0) && (diffedY == 1 || diffedY == -1 || diffedY == 0)){
            return true;
        }

        return false;
    }

    public int getNumberOfLives(){
        return numberOfLives;
    }

    public void setNumberOfLives(int numberOfLives){
        this.numberOfLives = numberOfLives;
    }

    public int getNumberOfMovesStayedOnTile(){
        return numberOfMovesStayedOnTile;
    }

    public void setNumberOfMovesStayedOnTile(int numberOfMovesStayedOnTile) {
        this.numberOfMovesStayedOnTile = numberOfMovesStayedOnTile;
    }

    public int getNumberOfMovesRequiredToCaptureTile(){
        return numberOfMovesRequiredToCaptureTile;
    }

    public void setNumberMovesRequiredToCaptureTile(int numberOfMovesRequiredToCaptureTile){
        this.numberOfMovesRequiredToCaptureTile = numberOfMovesRequiredToCaptureTile;
    }

    public boolean canCaptureTile(){
        if(numberOfMovesStayedOnTile >= numberOfMovesRequiredToCaptureTile){
            return true;
        }
        return false;
    }


}
