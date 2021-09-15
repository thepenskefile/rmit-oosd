package model.interfaces;

import model.BoardPiece;
import model.enums.PieceType;
import model.enums.TileType;

import java.io.Serializable;
import java.util.Deque;

public interface Board extends Serializable, Cloneable {

    /** Location of board pieces and tiles are initialised**/
    public abstract void initialiseBoard();

    /**
     *
     * @return all board tiles
     * @post.condition returned array will be non empty
     */
    public abstract BoardTile[][] getBoardTiles();

    public abstract void setBoardTiles(BoardTile[][] boardTiles);

    /**
     *
     * @return all board pieces currently in play on the board
     */
    public abstract Deque<BoardPiece> getBoardPieces();

    public abstract void setBoardPieces(Deque<BoardPiece> boardPieces);

    /**
     *
     * @param tileType
     * @return the board tiles of a specific type
     */
    public abstract BoardTile[][] getBoardTilesType(TileType tileType);

    /**
     *
     * @param pieceType
     * @return the board pieces of a specific type
     */
    public abstract Deque<BoardPiece> getBoardPiecesType(PieceType pieceType);

    /**Retrieve number of board tiles of given tile type
     * @param tileType Red or blue tile type*/
    public abstract int getNumOfTiles(TileType tileType);

    /**Retrieve number of board pieces of given piece type
     * @param pieceType Shark or eagle piece type */
    public abstract int getNumOfPieces(PieceType pieceType);

    public abstract boolean doesTileTypeExist(TileType tileType);

    public Board clone() throws CloneNotSupportedException;

}
