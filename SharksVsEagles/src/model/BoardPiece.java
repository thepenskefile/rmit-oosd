package model;

import model.enums.PieceType;
import model.interfaces.BoardTile;
import model.interfaces.GameEngine;

import java.io.Serializable;

public abstract class BoardPiece implements Serializable, Cloneable {

    private PieceType pieceType;
    private Location location;
    private String name;
    private boolean isSelected;

    public BoardPiece(PieceType pieceType, Location location, String name) {
        this.pieceType = pieceType;
        this.location = location;
        this.name = name;
        this.isSelected = false;
    }

    /**
     *
     * @return Location of board piece on board
     */
    public Location getLocation(){
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(Location location){
        this.location = location;
    }

    /**
     *
     * @return name of board piece
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @return piece type
     */
    public PieceType getPieceType(){
        return pieceType;
    }

    /**
     *
     * @return piece type as a string
     */
    public String getPieceTypeString(){
        return pieceType == PieceType.SHARK ? "shark" : "eagle";
    }

    /**
     *
     * @return isSelected
     */
    public boolean getIsSelected(){
        return isSelected;
    }

    /**
     *
     * @param isSelected
     */
    public void setIsSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

    /**
     * @param currentBoardTile Current location of piece to be moved
     * @param nextBoardTile Location to move piece to
     * @return True if move on piece is valid, otherwise false
     */
    public abstract boolean isValidMove(BoardTile currentBoardTile, BoardTile nextBoardTile, GameEngine game);

    /**
     * Template method for isValidMove
     * */
    public boolean isValidMoveTemplateMethod(BoardTile currentBoardTile, BoardTile nextBoardTile, GameEngine game)
    {
        // Behaviour common between all board piece subclasses
        // Check if tile to move to is occupied by an obstacle
        if(nextBoardTile.getIsOccupiedByObstacle()){
            return false;
        }

        //Primitive method - behaviour specific to the board piece subclass
        return isValidMove(currentBoardTile, nextBoardTile, game);
    }

    public abstract BoardPiece clone() throws CloneNotSupportedException;
}
