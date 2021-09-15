package client;

import javax.swing.*;

public class SwingClient {

    //initialise GameEngine and mainFrame
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GameManager gameManager = new GameManager();
            }
        });
    }
}