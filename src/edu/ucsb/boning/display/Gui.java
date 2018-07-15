package edu.ucsb.boning.display;

import edu.ucsb.boning.game.Game;
import edu.ucsb.boning.plotter.Plotter;

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
    private Canvas plotCanvas = new Canvas();

    public Gui(){
        canvas.setSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        plotCanvas.setSize(new Dimension(Plotter.WIDTH, Plotter.HEIGHT));
        plotCanvas.setVisible(false);
        frame.add(panel);
        panel.setLayout(new BorderLayout());
        panel.add(canvas, BorderLayout.CENTER);
        panel.add(plotCanvas, BorderLayout.EAST);
        panel.add(startButton, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public Canvas getPlotCanvas(){
        return plotCanvas;
    }

    // Event Handlers
    public void setStartButtonAction(ActionListener handler) {
        startButton.addActionListener(handler);
    }

    static public Font getDebugFont() {
        return new Font("Arial", Font.PLAIN, 8);
    }
}
