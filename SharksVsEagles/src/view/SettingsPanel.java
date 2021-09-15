package view;

import controller.BoardSizeController;
import controller.ObstaclesController;
import model.GameSettings;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private GameSettings gameSettings;
    private JCheckBox obstaclesChoice;
    private JComboBox<Integer> boardSizeOptions;

    public SettingsPanel(GameSettings gameSettings){
        this.gameSettings = gameSettings;

        JLabel obstaclesLbl = new JLabel("Include obstacles? ");
        obstaclesChoice = new JCheckBox();
        obstaclesChoice.addItemListener(new ObstaclesController(gameSettings));

        JLabel boardSizeLbl = new JLabel("Choose number of rows/columns of board: ");
        boardSizeOptions = new JComboBox<Integer>();
        boardSizeOptions.addItem(10);
        boardSizeOptions.addItem(12);
        boardSizeOptions.addItem(14);
        boardSizeOptions.addActionListener(new BoardSizeController());

        setLayout(new GridLayout(2, 2, 3, 10));
        add(obstaclesLbl);
        add(obstaclesChoice);
        add(boardSizeLbl);
        add(boardSizeOptions);
    }

    public JCheckBox getObstaclesChoice(){
        return obstaclesChoice;
    }

    public JComboBox<Integer> getBoardSizeOptions(){
        return boardSizeOptions;
    }
}