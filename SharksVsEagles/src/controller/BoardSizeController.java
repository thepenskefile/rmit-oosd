package controller;

import utils.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardSizeController implements ActionListener {

    public BoardSizeController() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        int boardSizeSelected = (int) cb.getSelectedItem();

        Utils.BOARD_ROWS = boardSizeSelected;
        Utils.BOARD_COLUMNS = boardSizeSelected;

    }
}