package view;

import controller.TimerController;
import controller.ChangeModeController;
import controller.UndoMoveController;
import model.interfaces.GameEngine;
import javax.swing.Timer;

import javax.swing.*;
import java.awt.*;

import static java.lang.Integer.parseInt;

public class DetailsPanel extends JPanel {

    private JPanel turnPanel, controlPanel;
    private JLabel lblPlayer, lblTimer;
    private JTextArea txtLog;
    private GameEngine gameEngine;
    private JToggleButton btnMode;
    private JButton btnUndo;
    private Timer timer;
    private TimerController timerController;
    private UndoMoveController undoMoveController;
    private ChangeModeController changeModeController;

    public DetailsPanel(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE,2),
                BorderFactory.createLineBorder(Color.BLACK,1)));

        turnPanel = new JPanel();
        turnPanel.setSize(200,150);
        turnPanel.setLayout(new BorderLayout());
        turnPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK,1),
                BorderFactory.createLineBorder(Color.WHITE,2)));
        add(turnPanel, BorderLayout.NORTH);
        String turnLabel = gameEngine.getCurrentPlayer().getPlayerTypeString() + "'s turn";
        lblPlayer = new JLabel(turnLabel, SwingConstants.CENTER);
        lblPlayer.setFont(new Font(Font.SERIF, Font.BOLD,  20));
        turnPanel.add(lblPlayer, BorderLayout.CENTER);


        txtLog = new JTextArea();
        txtLog.setEditable(false);

        txtLog.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK,1),
                BorderFactory.createLineBorder(Color.WHITE,2)));
        add(txtLog, BorderLayout.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK,1),
                BorderFactory.createLineBorder(Color.WHITE,2)));

        btnMode = new JToggleButton("Enable Alternate Mode");
        changeModeController = new ChangeModeController(gameEngine);
        btnMode.addActionListener(changeModeController);
        btnMode.setEnabled(false);
        JPanel modePanel = new JPanel();
        modePanel.setLayout(new GridLayout());
        modePanel.add(btnMode);
        controlPanel.add(modePanel);

        JPanel undoPanel = new JPanel();
        undoPanel.setLayout(new GridLayout());
        btnUndo = new JButton("Undo move");
        undoMoveController = new UndoMoveController(gameEngine);
        btnUndo.addActionListener(undoMoveController);
        btnUndo.setEnabled(gameEngine.getCurrentPlayer().getIsUndoEligible());
        undoPanel.add(btnUndo);
        controlPanel.add(undoPanel);

        add(controlPanel, BorderLayout.SOUTH);

        lblTimer = new JLabel("Timer: " + 0, SwingConstants.CENTER);
        lblTimer.setFont(new Font(Font.SERIF, Font.BOLD,  16));
        turnPanel.add(lblTimer, BorderLayout.SOUTH);

        timerPrompt();
    }

    public void updateDetails(){
        lblPlayer.setText(gameEngine.getCurrentPlayer().getPlayerTypeString() + "'s turn");
        lblTimer.setText("Timer: " + gameEngine.getTimeLeft());
        btnUndo.setEnabled(gameEngine.getCurrentPlayer().getIsUndoEligible());
        revalidate();
        repaint();
    }

    public void appendLogText(String text){
        txtLog.insert(text, 0);
        revalidate();
        repaint();
    }

    public void timerPrompt() {
        boolean validTimeEntered = false;

        while(!validTimeEntered) {
            try {
                int turnLength = parseInt(JOptionPane.showInputDialog("Enter amount of time for each player turn (in seconds)"));
                while (turnLength < 10 || turnLength > 600) {
                    JOptionPane.showMessageDialog(this, "Invalid turn length\nTurn length must be between 10-600 seconds");
                    turnLength = parseInt(JOptionPane.showInputDialog("Enter amount of time for each player turn (in seconds)"));
                }

                validTimeEntered = true;

                gameEngine.setTurnLength(turnLength);
                gameEngine.setTimeLeft(turnLength);

                timerController = new TimerController(lblTimer, gameEngine);

                timer = new Timer(1000, timerController);
                timer.start();

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Invalid turn length. Must be a numeric value");
            }
        }
    }

    public JToggleButton getBtnMode(){
        return btnMode;
    }

    public JButton getBtnUndo(){
        return btnUndo;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        timerController.setGameEngine(gameEngine);
        undoMoveController.setGameEngine(gameEngine);
        changeModeController.setGameEngine(gameEngine);
        updateDetails();
    }
}
