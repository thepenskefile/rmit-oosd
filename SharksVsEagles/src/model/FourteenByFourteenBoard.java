package model;

import model.enums.PieceType;
import model.enums.TileType;
import model.interfaces.Board;
import model.interfaces.BoardTile;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class FourteenByFourteenBoard implements Board {

    public static final int ROWS = 14;
    public static final int COLUMNS = 14;
    private BoardTile[][] boardTiles = new BoardTile[ROWS][COLUMNS];
    private Deque<BoardPiece> boardPieces = new ArrayDeque<>();

    public FourteenByFourteenBoard() {
    }

    @Override
    public void initialiseBoard() {
        initialiseTiles();
        initialisePieces();
    }

    @Override
    public BoardTile[][] getBoardTilesType(TileType tileType) {
        boolean empty = true;
        BoardTile[][] boardTilesType = new BoardTile[ROWS][COLUMNS];

        for (BoardTile[] tiles : boardTiles) {
            for (BoardTile boardTile : tiles) {
                if (boardTile.getTileType() == tileType) {
                    boardTilesType[boardTile.getLocation().getXCoordinate()][boardTile.getLocation().getYCoordinate()] = boardTile;
                    empty = false;
                }
            }
        }

        if (empty) {
            return null;
        }

        return boardTilesType;
    }

    @Override
    public int getNumOfTiles(TileType tileType) {
        int numOfTiles = 0;
        for (BoardTile[] tiles : boardTiles) {
            for (BoardTile boardTile : tiles) {
                if (boardTile.getTileType() == tileType) {
                    numOfTiles++;
                }
            }
        }
        return numOfTiles;
    }

    @Override
    public int getNumOfPieces(PieceType pieceType) {
        int numOfPieces = 0;
        for (BoardPiece boardPiece : getBoardPieces()) {
            if (pieceType == PieceType.SHARK && boardPiece instanceof SharkPiece) {
                numOfPieces++;
            } else if (pieceType == PieceType.EAGLE && boardPiece instanceof EaglePiece) {
                numOfPieces++;
            }
        }
        return numOfPieces;
    }

    @Override
    public Deque<BoardPiece> getBoardPiecesType(PieceType pieceType) {
        boolean empty = true;
        Deque<BoardPiece> boardPiecesType = new ArrayDeque<>();

        for (BoardPiece boardPiece : boardPieces) {
            if (boardPiece.getPieceType() == pieceType) {
                boardPiecesType.add(boardPiece);
                empty = false;
            }
        }

        if (empty) {
            return null;
        }

        return boardPiecesType;
    }

    /**
     * Sets boardTiles with their correct location and colour
     */
    private void initialiseTiles() {
        TileType tileType;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {

                //Second half of columns should be red
                if (j >= (COLUMNS / 2)) {
                    tileType = TileType.LAND;
                } else {
                    tileType = TileType.WATER;
                }
                final BoardTile boardTile = new BoardTileImpl(tileType, new Location(i, j));
                boardTiles[i][j] = boardTile;
            }
        }
    }

    /**
     * Positions board pieces in their correct starting positions
     */
    private void initialisePieces() {

        Location americanEagleLocation = new Location(ROWS - 1, COLUMNS - 1);
        Location baldEagleLocation = new Location(ROWS - 2, COLUMNS - 1);
        Location goldEagleLocation = new Location(ROWS - 3, COLUMNS - 1);
        Location americanEagleLocation1 = new Location(ROWS - 4, COLUMNS - 1);
        Location baldEagleLocation1 = new Location(ROWS - 5, COLUMNS - 1);
        Location goldEagleLocation1 = new Location(ROWS - 6, COLUMNS - 1);
        Location americanEagleLocation2 = new Location(ROWS - 7, COLUMNS - 1);

        Location greatWhiteSharkLocation = new Location(0, 0);
        Location hammerheadSharkLocation = new Location(1, 0);
        Location tigerSharkLocation = new Location(2, 0);
        Location greatWhiteSharkLocation1 = new Location(3, 0);
        Location hammerheadSharkLocation1 = new Location(4, 0);
        Location tigerSharkLocation1 = new Location(5, 0);
        Location greatWhiteSharkLocation2 = new Location(6, 0);

        EaglePiece AmericanEagle = new AmericanEaglePiece(PieceType.EAGLE, americanEagleLocation);
        boardPieces.add(AmericanEagle);

        EaglePiece BaldEagle = new BaldEaglePiece(PieceType.EAGLE, baldEagleLocation);
        boardPieces.add(BaldEagle);

        EaglePiece GoldEagle = new GoldEaglePiece(PieceType.EAGLE, goldEagleLocation);
        boardPieces.add(GoldEagle);

        EaglePiece AmericanEagle1 = new AmericanEaglePiece(PieceType.EAGLE, americanEagleLocation1);
        boardPieces.add(AmericanEagle1);

        EaglePiece BaldEagle1 = new AmericanEaglePiece(PieceType.EAGLE, baldEagleLocation1);
        boardPieces.add(BaldEagle1);

        EaglePiece GoldEagle1 = new GoldEaglePiece(PieceType.EAGLE, goldEagleLocation1);
        boardPieces.add(GoldEagle1);

        EaglePiece AmericanEagle2 = new AmericanEaglePiece(PieceType.EAGLE, americanEagleLocation2);
        boardPieces.add(AmericanEagle2);

        SharkPiece greatWhiteShark = new GreatWhitePiece(PieceType.SHARK, greatWhiteSharkLocation);
        boardPieces.add(greatWhiteShark);

        SharkPiece hammerheadShark = new HammerheadPiece(PieceType.SHARK, hammerheadSharkLocation);
        boardPieces.add(hammerheadShark);

        SharkPiece tigerShark = new TigerSharkPiece(PieceType.SHARK, tigerSharkLocation);
        boardPieces.add(tigerShark);

        SharkPiece greatWhiteShark1 = new GreatWhitePiece(PieceType.SHARK, greatWhiteSharkLocation1);
        boardPieces.add(greatWhiteShark1);

        SharkPiece hammerheadShark1 = new HammerheadPiece(PieceType.SHARK, hammerheadSharkLocation1);
        boardPieces.add(hammerheadShark1);

        SharkPiece tigerShark1 = new TigerSharkPiece(PieceType.SHARK, tigerSharkLocation1);
        boardPieces.add(tigerShark1);

        SharkPiece greatWhiteShark2 = new GreatWhitePiece(PieceType.SHARK, greatWhiteSharkLocation2);
        boardPieces.add(greatWhiteShark2);

        BoardPiece boardPiece;
        for (Iterator<BoardPiece> itr = boardPieces.iterator(); itr.hasNext(); ) {
            boardPiece = itr.next();
            boardTiles[boardPiece.getLocation().getXCoordinate()]
                    [boardPiece.getLocation().getYCoordinate()].setIsOccupied(true);
            boardTiles[boardPiece.getLocation().getXCoordinate()]
                    [boardPiece.getLocation().getYCoordinate()].setOccupiedBy(boardPiece);
        }
    }

    @Override
    public BoardTile[][] getBoardTiles() {
        return boardTiles;
    }

    @Override
    public void setBoardTiles(BoardTile[][] boardTiles) { this.boardTiles = boardTiles; }

    @Override
    public Deque<BoardPiece> getBoardPieces() {
        return boardPieces;
    }

    @Override
    public void setBoardPieces(Deque<BoardPiece> boardPieces) {
        this.boardPieces = boardPieces;
    }

    /**
     * @param tileType
     * @return true if there exists at least one tile of specified type on the board
     */
    @Override
    public boolean doesTileTypeExist(TileType tileType) {
        if (getBoardTilesType(tileType) == null) {
            return false;
        }
        return true;
    }

    @Override
    public Board clone() throws CloneNotSupportedException {
        Board cloned = new FourteenByFourteenBoard();

        BoardTile[][] clonedTiles = new BoardTile[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                clonedTiles[i][j] = boardTiles[i][j].clone();
            }
        }

        Deque<BoardPiece> clonedPieces = new ArrayDeque<BoardPiece>();
        boardPieces.forEach((piece) -> {
            try {
                clonedPieces.add(piece.clone());
                int x = piece.getLocation().getXCoordinate();
                int y = piece.getLocation().getYCoordinate();
                clonedTiles[x][y].setOccupiedBy(piece);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });

        cloned.setBoardTiles(clonedTiles);
        cloned.setBoardPieces(clonedPieces);

        return cloned;
    }

}
