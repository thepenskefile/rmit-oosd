package model.factories;

import model.TwelveByTwelveBoard;
import model.interfaces.Board;

public class TwelveByTwelveBoardFactory extends BoardFactory{
    @Override
    public Board createBoard() {
        return new TwelveByTwelveBoard();
    }
}
