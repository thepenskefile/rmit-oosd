package model.factories;

import model.TenByTenBoard;
import model.interfaces.Board;

public class TenByTenBoardFactory extends BoardFactory{


    @Override
    public Board createBoard() {
        return new TenByTenBoard();
    }
}
