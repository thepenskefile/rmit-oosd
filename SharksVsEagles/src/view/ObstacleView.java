package view;

import model.Obstacle;

import javax.swing.*;
import java.awt.*;

public class ObstacleView extends JPanel {

    public ObstacleView(Obstacle obstacle){
        JLabel pieceText = new JLabel(obstacle.getName());
        pieceText.setForeground (Color.RED);
        add(pieceText);
    }
}