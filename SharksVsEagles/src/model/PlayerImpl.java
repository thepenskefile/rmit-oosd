package model;

import model.enums.PlayerType;
import model.interfaces.Player;

public class PlayerImpl implements Player {

    private static int NUMBER_OF_UNDO_MOVES = 3;

    private PlayerType playerType;
    private boolean isCurrentPlayerWinner;
    private boolean isUndoEligible;
    private int numberUndoneMoves;

    public PlayerImpl(PlayerType playerType){
        this.playerType = playerType;
        isCurrentPlayerWinner = false;
        isUndoEligible = true;
        numberUndoneMoves = 0;
    }

    @Override
    public PlayerType getPlayerType() {
        return playerType;
    }

    @Override
    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    @Override
    public String getPlayerTypeString() {
        return playerType == PlayerType.SHARKPLAYER ? "Shark" : "Eagle";
    }

    @Override
    public boolean getIsCurrentPlayerWinner(){
        return isCurrentPlayerWinner;
    }

    @Override
    public void setIsCurrentPlayerWinner(boolean isCurrentPlayerWinner){
        this.isCurrentPlayerWinner = isCurrentPlayerWinner;
    }

    @Override
    public void setIsUndoEligible(boolean isUndoEligible) {
        this.isUndoEligible = isUndoEligible;
    }

    @Override
    public boolean getIsUndoEligible() {
        return isUndoEligible;
    }

    @Override
    public int getNumberUndoneMoves() {
        return numberUndoneMoves;
    }

    @Override
    public void setNumberUndoneMoves(int numberUndoneMoves) { this.numberUndoneMoves = numberUndoneMoves; }

    @Override
    public void incrementNumberUndoneMoves() {
        numberUndoneMoves = numberUndoneMoves + 1;
        // Player can only undo up to three moves in one go
        if(numberUndoneMoves >= NUMBER_OF_UNDO_MOVES){
            isUndoEligible = false;
        }
    }

    @Override
    public Player clone(){
        Player cloned = new PlayerImpl(playerType);
        cloned.setIsCurrentPlayerWinner(isCurrentPlayerWinner);
        cloned.setIsUndoEligible(isUndoEligible);
        cloned.setNumberUndoneMoves(numberUndoneMoves);
        return cloned;
    }

}
