package view.interfaces;

import model.BoardPiece;
import model.interfaces.Player;

public interface GameEngineCallback {

    /** @param boardPiece The board piece to be moved
     * @param player The player moving the board piece
     * @post.condition Logged the current game play**/
    public abstract void initialiseMove(BoardPiece boardPiece, Player player);

    /** Executes the current move
     *
     * @post.condition The board piece view is updated to reflect the current move
     * @post.condition The current player turn is updated on the view**/
    public abstract void executeMove();

    /**Logs game errors */
    public abstract void logText(String text);

    /** Disables alternate mode from being chosen*/
    public void disableAlternateMode();

    /** Enables alternate mode to be chosen*/
    public void enableAlternateMode();

    public void undoMove();
}
