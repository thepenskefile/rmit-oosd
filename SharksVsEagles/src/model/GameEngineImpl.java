package model;

import client.GameManager;
import commands.Move;
import history.Snapshot;
import model.decorator.BoardDecorator;
import model.decorator.ObstaclesDecorator;
import model.enums.PieceType;
import model.enums.PlayerType;
import model.enums.TileType;
import model.interfaces.Board;
import model.interfaces.BoardTile;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import utils.Utils;
import view.interfaces.GameEngineCallback;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class GameEngineImpl implements GameEngine{

    private Board board;
    private BoardDecorator obstaclesDecorator;
    private Deque<GameEngineCallback> callbacks = new ArrayDeque<>();
    private Player sharkPlayer;
    private Player eaglePlayer;
    private Player currentPlayer;
    private Move currentMove;
    private boolean isAlternateMode;
    private int timeLeft, turnLength;
    private boolean includeObstacles;
    private Snapshot snapshot;
    private boolean isUndoing;
    private GameEngine savedGame;
    private GameManager gameManager;

    public GameEngineImpl(Board board) {
        this.sharkPlayer = new PlayerImpl(PlayerType.SHARKPLAYER);
        this.eaglePlayer = new PlayerImpl(PlayerType.EAGLEPLAYER);
        this.board = board;
        obstaclesDecorator = new ObstaclesDecorator(board);
        currentPlayer = sharkPlayer;
        isAlternateMode = false;
        includeObstacles = false;
        snapshot = new Snapshot(this);
    }

    public GameEngineImpl(Board board, GameManager gameManager) {
        this.sharkPlayer = new PlayerImpl(PlayerType.SHARKPLAYER);
        this.eaglePlayer = new PlayerImpl(PlayerType.EAGLEPLAYER);
        this.board = board;
        obstaclesDecorator = new ObstaclesDecorator(board);
        currentPlayer = sharkPlayer;
        isAlternateMode = false;
        includeObstacles = false;
        snapshot = new Snapshot(this);
        this.gameManager = gameManager;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public BoardDecorator getObstaclesDecorator() {
        return obstaclesDecorator;
    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public void initialiseMove(BoardPiece boardPiece) {
        // Take snapshot of current state of game board before the move takes place
        snapshot.execute(currentMove);
        boardPiece.setIsSelected(true);
        currentMove = new Move(board.getBoardTiles()[boardPiece.getLocation().getXCoordinate()]
                [boardPiece.getLocation().getYCoordinate()], boardPiece, currentPlayer, this);
        GameEngineCallback callback;
        for (Iterator<GameEngineCallback> itr = callbacks.iterator(); itr.hasNext(); ) {
            callback = itr.next();
            callback.initialiseMove(boardPiece, currentPlayer);
        }
    }

    @Override
    public void finaliseMove(BoardTile boardTile) {
        GameEngineCallback callback;

        currentMove.setEndTile(boardTile);
        boolean currentMoveSuccessful = currentMove.execute();

        currentMove.getBoardPiece().setIsSelected(false);
        if (currentMoveSuccessful) {
            if (currentPlayer.getPlayerType() == PlayerType.EAGLEPLAYER) {
                checkEagleSittingTiles();
            }

            //Shark can capture red tile in alternate mode
            if (currentPlayer.getPlayerType() == PlayerType.SHARKPLAYER && boardTile.getTileType() == TileType.LAND) {
                boardTile.convertTileType();
            }

            if (checkPlayerWon(currentPlayer)) {
                //Current player has won and game is over
                currentPlayer.setIsCurrentPlayerWinner(true);
                return;
            }

            // Player can only undo move once per game
            if(isUndoing){
                currentPlayer.setIsUndoEligible(false);
                isUndoing = false;
            }
            // Change current player
            currentPlayer = currentPlayer == sharkPlayer ? eaglePlayer : sharkPlayer;

            //Reset timer
            timeLeft = turnLength;
        } else {
            clearCurrentMove();
            logText("Move invalid");
        }

        for (Iterator<GameEngineCallback> itr = callbacks.iterator(); itr.hasNext(); ) {
            callback = itr.next();
            callback.executeMove();
        }

    }

    private boolean checkPlayerWon(Player currentPlayer) {
        if (currentPlayer.getPlayerType() == PlayerType.SHARKPLAYER) {
            //Shark wins if there are no eagles pieces left
            if (board.getBoardPiecesType(PieceType.EAGLE) == null) {
                return true;
            }
        } else {
            //Get 75 percent of board tiles
            int percentTiles = (int) ((Utils.BOARD_ROWS * Utils.BOARD_COLUMNS) * (75.0f / 100.0f));

            int numOfRedTiles = board.getNumOfTiles(TileType.LAND);

            //Eagle wins if at least 75 percent of the board is red tiles
            if (numOfRedTiles >= percentTiles) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void logText(String text) {
        GameEngineCallback callback;
        for (Iterator<GameEngineCallback> itr = callbacks.iterator(); itr.hasNext(); ) {
            callback = itr.next();
            callback.logText(text);
        }
    }

    @Override
    public void undoMoveCallback() {
        GameEngineCallback callback;
        for(Iterator<GameEngineCallback> itr = callbacks.iterator(); itr.hasNext(); )  {
            callback = itr.next();
            callback.undoMove();
        }
    }

    @Override
    public void undoMove() {
        isUndoing = true;
        currentPlayer.incrementNumberUndoneMoves();
        // Undo the last two moves, as a single undo effects both players
        snapshot.undo();
        snapshot.undo();
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public boolean getIsAlternateMode() {
        return isAlternateMode;
    }

    @Override
    public void setIsAlternateMode(boolean isAlternateMode) {
        this.isAlternateMode = isAlternateMode;
    }

    @Override
    public void disableAlternateMode() {
        this.setIsAlternateMode(false);
        GameEngineCallback callback;
        for (Iterator<GameEngineCallback> itr = callbacks.iterator(); itr.hasNext(); ) {
            callback = itr.next();
            callback.disableAlternateMode();
        }
    }

    @Override
    public void enableAlternateMode() {
        GameEngineCallback callback;
        for (Iterator<GameEngineCallback> itr = callbacks.iterator(); itr.hasNext(); ) {
            callback = itr.next();
            callback.enableAlternateMode();
        }
    }

    @Override
    public Move getCurrentMove() {
        return currentMove;
    }

    @Override
    public void clearCurrentMove() {
        currentMove = null;
    }

    @Override
    public void addCallback(GameEngineCallback callback) {
        callbacks.add(callback);
    }

    /**
     * @param boardPiece the piece we want to respawn
     * @param tileType   the tile type of the tile we want to respawn it to
     */
    @Override
    public void respawnPiece(BoardPiece boardPiece, TileType tileType) {
        Location newLocation = getRandomUnoccupiedTileOfType(tileType);
        if (newLocation != null) {
            boardPiece.setLocation(newLocation);
            board.getBoardTiles()[newLocation.getXCoordinate()][newLocation.getYCoordinate()].setIsOccupied(true);
            board.getBoardTiles()[newLocation.getXCoordinate()][newLocation.getYCoordinate()].setOccupiedBy(boardPiece);
        }
    }

    /**
     * @param tileType of tile location we want returned
     * @return random unoccupied board tile of specified type
     */
    @Override
    public Location getRandomUnoccupiedTileOfType(TileType tileType) {
        boolean doesTileTypeExist = board.doesTileTypeExist(tileType);
        // If there are no more tiles of specified tile left on the board, stop looking
        if (!doesTileTypeExist) {
            return null;
        }

        BoardTile boardTiles[][] = board.getBoardTiles();

        while (true) {
            int newXCoordinate = ThreadLocalRandom.current().nextInt(0, Utils.BOARD_ROWS);
            int newYCoordinate = ThreadLocalRandom.current().nextInt(0, Utils.BOARD_COLUMNS);
            if (boardTiles[newXCoordinate][newYCoordinate].getTileType() == tileType &&
                    !boardTiles[newXCoordinate][newYCoordinate].getIsOccupied()) {
                return new Location(newXCoordinate, newYCoordinate);
            }
        }
    }

    /**
     * @param eaglePiece to be removed from board
     */
    @Override
    public void captureEagle(EaglePiece eaglePiece) {
        Deque<BoardPiece> boardPieces = board.getBoardPieces();
        boardPieces.remove(eaglePiece);
        board.setBoardPieces(boardPieces);
        logText(eaglePiece.getPieceTypeString() + " was captured by " + currentMove.getBoardPiece().getPieceTypeString());
    }

    /**
     * @param tileType the tileType of the tile we want to convert
     */
    @Override
    public void convertRandomTileOfType(TileType tileType) {
        BoardTile boardTiles[][] = board.getBoardTiles();
        boolean doesTileTypeExist = board.doesTileTypeExist(tileType);

        if (doesTileTypeExist) {
            boolean tileConverted = false;
            while (!tileConverted) {
                int newXCoordinate = ThreadLocalRandom.current().nextInt(0, Utils.BOARD_ROWS);
                int newYCoordinate = ThreadLocalRandom.current().nextInt(0, Utils.BOARD_COLUMNS);
                if (boardTiles[newXCoordinate][newYCoordinate].getTileType() == tileType &&
                        !boardTiles[newXCoordinate][newYCoordinate].getIsOccupied()) {
                    boardTiles[newXCoordinate][newYCoordinate].convertTileType();
                    logText("Tile " + "(" + newXCoordinate + ", " + newYCoordinate + ") was converted from " +
                            tileType + " to " + Utils.getOtherTileType(tileType));
                    tileConverted = true;
                }
            }
        }

    }

    /**
     * Loops through all the eagle pieces and checks to see if they are eligible to convert the blue tile they
     * are sitting on, depending on how many moves they have sat there for.
     */
    @Override
    public void checkEagleSittingTiles() {
        // If an eagle piece has moved, reset the number of moves it has sat on a tile for back to 0
        if (currentMove.getBoardPiece().getPieceType() == PieceType.EAGLE) {
            ((EaglePiece) currentMove.getBoardPiece()).setNumberOfMovesStayedOnTile(0);
        }
        BoardTile boardTiles[][] = board.getBoardTiles();
        // Loop through all board tiles
        for (BoardPiece boardPiece : board.getBoardPieces()) {
            Location boardPieceLocation = boardPiece.getLocation();
            // Only select those board pieces which are eagles, and are on blue tiles
            if (boardPiece.getPieceType() == PieceType.EAGLE && boardTiles[boardPieceLocation.getXCoordinate()]
                    [boardPieceLocation.getYCoordinate()].getTileType() == TileType.WATER) {
                // Increment the number of moves each eagle has stayed on their tile for
                int numberOfMovesStayedOnTile = (((EaglePiece) boardPiece).getNumberOfMovesStayedOnTile());
                ((EaglePiece) boardPiece).setNumberOfMovesStayedOnTile(++numberOfMovesStayedOnTile);
                // If the eagle piece is eligible to capture the tile it is sitting on, then convert the tile type
                if ((((EaglePiece) boardPiece).canCaptureTile())) {
                    boardTiles[boardPieceLocation.getXCoordinate()]
                            [boardPieceLocation.getYCoordinate()].convertTileType();
                    ((EaglePiece) boardPiece).setNumberOfMovesStayedOnTile(0);
                    // For every three blue tiles captured, an American Eagle can capture another blue tile
                    if (boardPiece instanceof AmericanEaglePiece &&
                            ((AmericanEaglePiece) boardPiece).getNumberOfTilesCaptured() > 0 &&
                            ((AmericanEaglePiece) boardPiece).getNumberOfTilesCaptured() % 3 == 0) {
                        convertRandomTileOfType(TileType.WATER);
                    }
                }
            }
        }
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void turnTimedOut() {
        GameEngineCallback callback;

        logText(currentPlayer.getPlayerTypeString() + " player timed out!");
        currentPlayer = currentPlayer == sharkPlayer ? eaglePlayer : sharkPlayer;
        clearCurrentMove();

        for (Iterator<GameEngineCallback> itr = callbacks.iterator(); itr.hasNext(); ) {
            callback = itr.next();
            callback.executeMove();
        }
    }

    public int getTurnLength() {
        return turnLength;
    }

    public void setTurnLength(int turnLength) {
        this.turnLength = turnLength;
    }

    @Override
    public GameEngineImpl clone() throws CloneNotSupportedException {
        GameEngineImpl cloned = new GameEngineImpl(board, gameManager);

        Board newBoard;
        if(board instanceof FourteenByFourteenBoard)
            newBoard = (FourteenByFourteenBoard) board.clone();
        else if(board instanceof TwelveByTwelveBoard)
            newBoard = (TwelveByTwelveBoard) board.clone();
        else
            newBoard = (TenByTenBoard) board.clone();
        cloned.setBoard(newBoard);

        cloned.sharkPlayer = sharkPlayer.clone();
        cloned.eaglePlayer = eaglePlayer.clone();
        if(currentPlayer.getPlayerType() == PlayerType.SHARKPLAYER)
            cloned.currentPlayer = cloned.sharkPlayer;
        else
            cloned.currentPlayer = cloned.eaglePlayer;
        cloned.currentMove = null;
        cloned.isAlternateMode = isAlternateMode;
        cloned.isUndoing = isUndoing;
        cloned.turnLength = turnLength;
        cloned.timeLeft = timeLeft;
        cloned.gameManager = gameManager;
        cloned.callbacks = callbacks;

        savedGame = cloned;
        cloned.savedGame = cloned;
        return cloned;
    }

    @Override
    public void saveGame() throws CloneNotSupportedException {
        this.savedGame = this.clone();
        logText("Game Saved");
    }

    public void loadGame() throws CloneNotSupportedException {
        if(savedGame != null) {
            gameManager.loadGame(savedGame);
            logText("Saved game was loaded");
        }
        else
            return;
    }
}
