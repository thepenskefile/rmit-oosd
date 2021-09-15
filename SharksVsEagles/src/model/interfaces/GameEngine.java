package model.interfaces;

import model.BoardPiece;
import model.EaglePiece;
import model.Location;
import commands.Move;
import model.decorator.BoardDecorator;
import model.enums.TileType;
import view.interfaces.GameEngineCallback;

public interface GameEngine extends Cloneable {

    /**
     *
     * @return Board
     */
    public abstract Board getBoard();

    public abstract void setBoard(Board board);

    /**@return Board decorator which initialises obstacles on the board */
    public abstract BoardDecorator getObstaclesDecorator();

    /**
     *
     * @param boardPiece The selected board piece to be moved
     * @pre.condition boardPiece must be assigned a location
     * @pre.condition valid player is moving piece eg. shark player moving shark piece
     * @post.condition current move is set to location of boardPiece
     */
    public abstract void initialiseMove(BoardPiece boardPiece);

    /**
     *
     * @param boardTile The board tile to move the piece to
     * @pre.condition current move is not null
     * @post.condition current player is switched
     * @post.condition board piece is deselected
     */
    public abstract void finaliseMove(BoardTile boardTile);

    /**
     *
     * @return current move
     */
    public abstract Move getCurrentMove();

    public abstract void clearCurrentMove();

    /**
     *
     * @return currentPlayer
     */
    public abstract Player getCurrentPlayer();

    public abstract void setCurrentPlayer(Player currentPlayer);

    /**
     *
     * @return True if the board piece is in alternate mode, otherwise false
     */
    public abstract boolean getIsAlternateMode();

    /**
     *
     * @param isAlternateMode Enable or disable alternate mode
     */
    public abstract void setIsAlternateMode(boolean isAlternateMode);

    /** Disables alternate mode from being chosen **/
    public void disableAlternateMode();

    /**Enables alternate mode to be chosen*/
    public void enableAlternateMode();

    /**
     *
     * @param callback Callback to be added
     */
    public abstract void addCallback(GameEngineCallback callback);

    /**
     *
     * @param text Describes any game errors
     */
    public abstract void logText(String text);

    public abstract void undoMove();

    public abstract void undoMoveCallback();

    /**
     * Returns a random tile that does not have any board piece on it
     * @return
     */
    public abstract Location getRandomUnoccupiedTileOfType(TileType tileType);

    /**
     * Removes the eagle piece that was captured from the board
     * @param eaglePiece
     */
    public abstract void captureEagle(EaglePiece eaglePiece);

    /**
     * Respawns a piece to a random location
     * @param boardPiece
     */
    public abstract void respawnPiece(BoardPiece boardPiece, TileType tileType);

    /**
     * Converts a random tile on the board of a specified type
     */
    public abstract void convertRandomTileOfType(TileType tileType);

    /**
     * Checks all the tiles the eagle pieces are sitting on to see if they qualify for tile type conversion
     */
    public abstract void checkEagleSittingTiles();

    public abstract void turnTimedOut();

    public abstract int getTimeLeft();

    public abstract void setTimeLeft(int timeLeft);

    public abstract int getTurnLength();

    public abstract void setTurnLength(int timeLeft);

    public abstract void saveGame() throws CloneNotSupportedException;

    public abstract void loadGame() throws CloneNotSupportedException;
}
