package edu.ucsb.boning.display;

import edu.ucsb.boning.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

public class Gui {

    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JButton startButton = new JButton("Start");
    private Canvas canvas = new Canvas();

    public Gui(){
        canvas.setSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        frame.add(panel);
        panel.add(canvas);
        panel.add(startButton);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Canvas getCanvas(){
        return canvas;
    }

    // Event Handlers
    public void setStartButtonAction(ActionListener handler) {
        startButton.addActionListener(handler);
    }
}
