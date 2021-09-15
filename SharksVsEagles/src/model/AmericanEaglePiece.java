package model;

import model.enums.PieceType;

public class AmericanEaglePiece extends EaglePiece {

    private static String name = "AE";
    private static int NUMBER_OF_LIVES = 1;
    private static int NUMBER_OF_MOVES_REQUIRED_TO_CAPTURE_TILE = 2;

    private int numberOfTilesCaptured = 0;

    public AmericanEaglePiece (PieceType pieceType, Location location) {
        super(pieceType, location, name);
        super.setNumberOfLives(NUMBER_OF_LIVES);
        super.setNumberMovesRequiredToCaptureTile(NUMBER_OF_MOVES_REQUIRED_TO_CAPTURE_TILE);
    }

    public int getNumberOfTilesCaptured(){
        return numberOfTilesCaptured;
    }

    @Override
    public boolean canCaptureTile() {
        boolean canCaptureTile = super.canCaptureTile();
        if(canCaptureTile){
            numberOfTilesCaptured++;
        }
        return canCaptureTile;
    }

    @Override
    public AmericanEaglePiece clone() throws CloneNotSupportedException {
        AmericanEaglePiece copy = new AmericanEaglePiece(this.getPieceType(), this.getLocation().clone());
        return copy;
    }
}
