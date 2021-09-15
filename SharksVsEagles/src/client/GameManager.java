package client;

import model.GameEngineImpl;
import model.GameSettings;
import model.factories.BoardFactory;
import model.factories.FourteenByFourteenBoardFactory;
import model.factories.TenByTenBoardFactory;
import model.factories.TwelveByTwelveBoardFactory;
import model.interfaces.Board;
import model.interfaces.GameEngine;
import utils.Utils;
import view.GameEngineCallbackGUI;
import view.MainFrame;
import view.SettingsPanel;

import javax.swing.*;

public class GameManager {

    private GameEngine gameEngine;
    private MainFrame mainFrame;

    public GameManager() {
        GameSettings gameSettings = new GameSettings();
        //Display game settings
        SettingsPanel settingsPanel = new SettingsPanel(gameSettings);
        BoardFactory boardFactory;
        Board board;
        JOptionPane.showConfirmDialog(null, settingsPanel,
                "GAME SETTINGS", JOptionPane.OK_CANCEL_OPTION);

        //checks to see what the board row/col is and creates the specific board factory
        if (Utils.BOARD_ROWS == 12) {
            boardFactory = new TwelveByTwelveBoardFactory();
            board = boardFactory.createBoard();
        } else if (Utils.BOARD_ROWS == 14) {
            boardFactory = new FourteenByFourteenBoardFactory();
            board = boardFactory.createBoard();
        } else {
            Utils.BOARD_ROWS = 10;
            Utils.BOARD_COLUMNS = 10;
            boardFactory = new TenByTenBoardFactory();
            board = boardFactory.createBoard();
        }

        gameEngine = new GameEngineImpl(board, this);

        if (gameSettings.getIncludeObstacles()) {
            //Initialise board with obstacles
            gameEngine.getObstaclesDecorator().initialiseBoard();
        } else {
            //Initialise board without obstacles
            gameEngine.getBoard().initialiseBoard();
        }
        mainFrame = new MainFrame(gameEngine);
        gameEngine.addCallback(new GameEngineCallbackGUI(mainFrame));
    }

    public void loadGame(GameEngine gameEngine) throws CloneNotSupportedException {
        //Make saved game the active game and replace saved game with a new clone
        this.gameEngine = gameEngine;
        this.mainFrame.setGameEngine(gameEngine);
        this.gameEngine.saveGame();
    }
}
