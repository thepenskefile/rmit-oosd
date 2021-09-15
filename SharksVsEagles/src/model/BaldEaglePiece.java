package model;

import model.enums.PieceType;

public class BaldEaglePiece extends EaglePiece {

    private static String name = "BE";
    private static int NUMBER_OF_LIVES = 2;
    private static int NUMBER_OF_MOVES_REQUIRED_TO_CAPTURE_TILE = 3;

    public BaldEaglePiece (PieceType pieceType, Location location) {
        super(pieceType, location, name);
        super.setNumberOfLives(NUMBER_OF_LIVES);
        super.setNumberMovesRequiredToCaptureTile(NUMBER_OF_MOVES_REQUIRED_TO_CAPTURE_TILE);
    }

    @Override
    public BaldEaglePiece clone() throws CloneNotSupportedException {
        BaldEaglePiece copy = new BaldEaglePiece(this.getPieceType(), this.getLocation().clone());
        return copy;
    }

}
