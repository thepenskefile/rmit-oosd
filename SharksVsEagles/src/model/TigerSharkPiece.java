package model;

import model.enums.PieceType;

public class TigerSharkPiece extends SharkPiece{

    public static final int NUMBER_OF_LIVES_CAN_TAKE = 2;

    private static String name = "TS";

    public TigerSharkPiece (PieceType pieceType, Location location) {
        super(pieceType, location, name);
        this.setNumberOfLivesCanTake(NUMBER_OF_LIVES_CAN_TAKE);
    }

    @Override
    public TigerSharkPiece clone() throws CloneNotSupportedException {
        TigerSharkPiece copy = new TigerSharkPiece(this.getPieceType(), this.getLocation());
        return copy;
    }
}
