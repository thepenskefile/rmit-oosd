package model.interfaces;

import model.enums.PlayerType;

public interface Player extends Cloneable {

    /**
     *
     * @return shark or eagle player type
     */
    public abstract PlayerType getPlayerType();

    /**
     *
     * @param playerType The type of player ie. Shark or Eagle
     */
    public abstract void setPlayerType(PlayerType playerType);

    /**
     *
     * @return shark or eagle player type string
     */
    public abstract String getPlayerTypeString();

    /**Returns true if the current player has won the game, otherwise false */
    public abstract boolean getIsCurrentPlayerWinner();

    /** Set to true if the current player has won the game, otherwise false */
    public abstract void setIsCurrentPlayerWinner(boolean isCurrentPlayerWinner);

    /**
     *
     * @param isUndoEligible
     */
    public abstract void setIsUndoEligible(boolean isUndoEligible);

    /**
     *
     * @return isUndoEligible
     */
    public abstract boolean getIsUndoEligible();

    /**
     *
     * @return numberUndoneMoves
     */
    public abstract int getNumberUndoneMoves();

    public abstract void setNumberUndoneMoves(int numberUndoneMoves);

    /**
     * Increments the number of undone moves by one
     */
    public abstract void incrementNumberUndoneMoves();

    public abstract Player clone();

}
