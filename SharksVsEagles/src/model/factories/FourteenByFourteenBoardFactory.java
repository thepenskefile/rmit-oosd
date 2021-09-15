package model.factories;

import model.FourteenByFourteenBoard;
import model.interfaces.Board;

public class FourteenByFourteenBoardFactory extends BoardFactory {

    @Override
    public Board createBoard() {
        return new FourteenByFourteenBoard();
    }
}
