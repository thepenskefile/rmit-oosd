package model;

import model.enums.PieceType;

public class HammerheadPiece extends SharkPiece {

    private static String name = "HS";

    public HammerheadPiece (PieceType pieceType, Location location) {
        super(pieceType, location, name);
    }

    @Override
    public HammerheadPiece clone() throws CloneNotSupportedException {
        HammerheadPiece copy = new HammerheadPiece(this.getPieceType(), this.getLocation().clone());
        return copy;
    }

}
