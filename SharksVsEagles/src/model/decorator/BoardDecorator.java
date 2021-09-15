package model.decorator;

import model.interfaces.Board;
import model.interfaces.BoardTile;

public abstract class BoardDecorator implements Board {

    private Board board;

    /**@pre.condition Board is not null */
    public BoardDecorator(Board board){
        if(board == null){
            throw new IllegalArgumentException("Board can not be null");
        }
        this.board = board;
    }

    @Override
    public void initialiseBoard(){
        board.initialiseBoard();
    }

    @Override
    public BoardTile[][] getBoardTiles() {
        return board.getBoardTiles();
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        return board;
    }
}
