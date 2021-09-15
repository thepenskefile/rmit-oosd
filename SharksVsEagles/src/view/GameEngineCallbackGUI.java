package view;

import model.BoardPiece;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback {

    private MainFrame mainFrame;
    public GameEngineCallbackGUI(MainFrame mainFrame){
        this.mainFrame = mainFrame;
    }
    @Override
    public void executeMove() {
        mainFrame.getBoardPanel().updateBoard();
        mainFrame.getDetailsPanel().updateDetails();
    }

    @Override
    public void undoMove() {
        mainFrame.getDetailsPanel().appendLogText("Move undone" + "\n");
        mainFrame.getBoardPanel().updateBoard();
        mainFrame.getDetailsPanel().updateDetails();
    }

    @Override
    public void logText(String text) {
        mainFrame.getDetailsPanel().appendLogText(text + "\n");
    }

    @Override
    public void initialiseMove(BoardPiece boardPiece, Player player) {
        mainFrame.getBoardPanel().updateBoard();
        // Player cannot undo a move once they have selected a piece
        mainFrame.getDetailsPanel().getBtnUndo().setEnabled(false);
        String playerTypeString = player.getPlayerTypeString();
        logText(playerTypeString + " player selected " + boardPiece.getName());
    }

    @Override
    public void disableAlternateMode(){
        mainFrame.getDetailsPanel().getBtnMode().setSelected(false);
        mainFrame.getDetailsPanel().getBtnMode().setEnabled(false);
    }

    @Override
    public void enableAlternateMode(){
        mainFrame.getDetailsPanel().getBtnMode().setEnabled(true);
    }
}
