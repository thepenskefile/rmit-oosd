package commands;

import model.BoardPiece;
import model.EaglePiece;
import model.HammerheadPiece;
import model.SharkPiece;
import model.enums.PieceType;
import model.enums.TileType;
import model.interfaces.BoardTile;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import utils.Utils;

public class Move implements Command {

    private BoardTile startTile;
    private BoardTile endTile;
    private BoardPiece boardPiece;
    private Player player;
    private GameEngine gameEngine;

    /**
     *
     * @param startTile Starting tile of the board piece
     * @param boardPiece The board piece to be moved
     * @param player The player moving the board piece
     */
    public Move(BoardTile startTile, BoardPiece boardPiece, Player player, GameEngine gameEngine){
        this.startTile = startTile;
        this.boardPiece = boardPiece;
        this.player = player;
        this.gameEngine = gameEngine;
    }

    /**
     *
     * @param endTile The tile the board piece is moved to
     */
    public void setEndTile (BoardTile endTile){
        this.endTile = endTile;
    }

    /**
     * @pre.condition The move is valid for the selected board piece
     * @return returns true if the move is a valid and successful one
     */
    public boolean execute(){

        // Check if the move is valid for the piece
        if(!boardPiece.isValidMoveTemplateMethod(startTile, endTile, gameEngine)){
            return false;
        }

        // Piece cannot move on tile that is occupied by the same team
        if(endTile.getIsOccupied() && !Utils.isOpponentOnTile(boardPiece, endTile.getOccupiedBy())){
            return false;
        }

        boardPiece.setLocation(endTile.getLocation());

        // Checks if the tile the piece moved on is occupied by the opponent
        if(endTile.getIsOccupied() && Utils.isOpponentOnTile(boardPiece, endTile.getOccupiedBy())){
            // Find which board piece is the eagle
            BoardPiece eaglePiece = boardPiece.getPieceType() == PieceType.EAGLE ? boardPiece :
                    endTile.getOccupiedBy();

            // Find which board piece is the shark
            BoardPiece sharkPiece = boardPiece.getPieceType() == PieceType.SHARK ? boardPiece :
                    endTile.getOccupiedBy();

            // Remove a life from the eagle
            int currentLives = ((EaglePiece) eaglePiece).getNumberOfLives();
            int numberOfLivesTaken = ((SharkPiece) sharkPiece).getNumberOfLivesCanTake();
            if(!gameEngine.getIsAlternateMode()) {  // Sharks can't take eagle lives in alternate mode
                ((EaglePiece) eaglePiece).setNumberOfLives(currentLives - numberOfLivesTaken);
            }

            // If the eagle has no lives left, it will be captured and removed from the board
            if(((EaglePiece) eaglePiece).getNumberOfLives() <= 0){
                gameEngine.captureEagle(((EaglePiece) eaglePiece));
                // If the piece is a hammerhead shark, convert a random red tile to a blue tile
                if(boardPiece instanceof HammerheadPiece){
                    gameEngine.convertRandomTileOfType(TileType.LAND);
                }
            }

            // Respawn the eagle to a random, unoccupied red board tile
            gameEngine.respawnPiece(eaglePiece, TileType.LAND);
        }

        startTile.setIsOccupied(false);
        startTile.setOccupiedBy(null);
        endTile.setIsOccupied(true);
        endTile.setOccupiedBy(boardPiece);
        return true;
    }

    /**
     *
     * @return boardPiece The board piece being moved
     */
    public BoardPiece getBoardPiece(){
        return boardPiece;
    }

}
