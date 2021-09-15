package view;

import model.interfaces.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{

    private GameEngine gameEngine;
    private final BoardPanel boardPanel;
    private final DetailsPanel detailsPanel;
    private static Dimension MAIN_FRAME_DIMENSIONS = new Dimension(800,600);

    public MainFrame(GameEngine gameEngine) {
        super();
        this.gameEngine = gameEngine;
        setSize(MAIN_FRAME_DIMENSIONS);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sharks Vs Eagles - Banana Revolvers");
        setLayout(new BorderLayout());

        final JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        boardPanel = new BoardPanel(gameEngine);
        boardPanel.setSize(600,600);
        add(boardPanel, BorderLayout.CENTER);

        detailsPanel = new DetailsPanel(gameEngine);
        add(detailsPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());

        return menuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem fileLoad = new JMenuItem("Load");
        fileLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gameEngine.loadGame();
                } catch (CloneNotSupportedException cloneNotSupportedException) {
                    cloneNotSupportedException.printStackTrace();
                }
            }
        });
        final JMenuItem fileSave = new JMenuItem("Save");
        fileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gameEngine.saveGame();
                } catch (CloneNotSupportedException cloneNotSupportedException) {
                    cloneNotSupportedException.printStackTrace();
                }
            }
        });

        fileMenu.add(fileLoad);
        fileMenu.add(fileSave);

        return fileMenu;
    }

    public DetailsPanel getDetailsPanel(){
        return detailsPanel;
    }

    public BoardPanel getBoardPanel(){
        return boardPanel;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.boardPanel.setGameEngine(gameEngine);
        this.detailsPanel.setGameEngine(gameEngine);
    }
}
