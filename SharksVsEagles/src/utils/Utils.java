package utils;

import model.BoardPiece;
import model.enums.PieceType;
import model.enums.PlayerType;
import model.enums.TileType;

public class Utils {

    public static int BOARD_ROWS;
    public static int BOARD_COLUMNS;

    /**
     * Used to check if player is selecting their own board piece or not
     * @param pieceType
     * @param playerType
     * @return true if the piece belongs to the player
     */
    public static boolean doesPieceMatchPlayer(PieceType pieceType, PlayerType playerType) {
        if(pieceType == PieceType.SHARK && playerType == PlayerType.SHARKPLAYER){
            return true;
        }
        if(pieceType == PieceType.EAGLE && playerType == PlayerType.EAGLEPLAYER){
            return true;
        }
        return false;
    }

    /**
     *
     * @param currentPiece
     * @param occupyingPiece
     * @return true if the occupying piece is on the opposite team of the current piece
     */
    public static boolean isOpponentOnTile(BoardPiece currentPiece, BoardPiece occupyingPiece){
        return currentPiece.getPieceType() != occupyingPiece.getPieceType();
    }

    public static TileType getOtherTileType(TileType tileType){
        if(tileType == TileType.LAND){
            return TileType.WATER;
        }
        return TileType.LAND;
    }
}
