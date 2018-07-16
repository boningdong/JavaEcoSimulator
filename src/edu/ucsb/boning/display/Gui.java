package edu.ucsb.boning.display;

import edu.ucsb.boning.game.Game;
import edu.ucsb.boning.plotter.Plotter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class Gui {

    public static final Color GUI_BGCOLOR = new Color(0x544C5C);

    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JPanel ctrlPanel = new JPanel();
    private JButton startButton = new JButton("Plotter");
    private Canvas canvas = new Canvas();
    //private Plotter plotter = Plotter.getInstance();

    public Gui(){
        canvas.setSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        frame.add(panel);
        frame.setTitle("Ecosystem Simulator");
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 15, 10, 15));
        ctrlPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        ctrlPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        panel.add(canvas, BorderLayout.CENTER);
        panel.add(ctrlPanel, BorderLayout.SOUTH);

        canvas.setBackground(GUI_BGCOLOR);
        panel.setBackground(GUI_BGCOLOR);
        ctrlPanel.setBackground(GUI_BGCOLOR);

        ctrlPanel.add(startButton);
        startButton.addActionListener(e -> Plotter.getInstance().openPlotter());
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(0x1A89A5));
        //Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(8, 14, 8, 14);
        Border compound = new CompoundBorder(null, margin);
        startButton.setBorder(compound);

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

    static public Font getDebugFont() {
        return new Font("Arial", Font.PLAIN, 8);
    }
}
