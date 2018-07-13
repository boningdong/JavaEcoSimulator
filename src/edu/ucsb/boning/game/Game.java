package edu.ucsb.boning.game;

import edu.ucsb.boning.Utilities.FrameTimer;
import edu.ucsb.boning.display.Display;
import edu.ucsb.boning.entities.Sheep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game implements Runnable{

    // Game Settings
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final double FPS = 60.0;
    public static final double COLLISION_RANGE = 20;


    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JButton startButton = new JButton("Start");
    private Canvas canvas = new Canvas();

    private Thread gameThread = new Thread(this);
    private FrameTimer fpsTimer = new FrameTimer(1/FPS * 1000);
    private FrameTimer renderTimer = new FrameTimer(1/FPS * 1000);
    // TODO: Delete this part and move to world

    private ArrayList<Sheep> sheep = new ArrayList<>();

    Game() {
        canvas.setSize(new Dimension(Game.WIDTH, Game.HEIGHT));

        frame.add(panel);
        panel.add(canvas);
        panel.add(startButton);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for(int i = 0; i < 100; i++) {
            sheep.add(new Sheep());
        }
        gameThread.start();
    }

    @Override
    public void run() {
        while(true) {
            if (fpsTimer.tick())
                update();
            render();
        }
    }

    public void update() {
        for (Sheep s: sheep)
            s.update(fpsTimer.getDtSeconds());
    }

    public void render() {
        BufferStrategy buffer = canvas.getBufferStrategy();
        if (buffer == null) {
            canvas.createBufferStrategy(3);
            return ;
        }

        Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.clearRect(0, 0, WIDTH, HEIGHT);
        //g.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
        for (Sheep s : sheep)
            s.render(g);
        buffer.show();
        g.dispose();
    }

    public static void main(String[] args){
        Game game = new Game();
    }


}
