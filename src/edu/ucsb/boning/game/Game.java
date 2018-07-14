package edu.ucsb.boning.game;

import edu.ucsb.boning.Utilities.FrameTimer;
import edu.ucsb.boning.display.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    // Game Settings
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final double FPS = 120.0;
    public static final double COLLISION_RANGE = 20;

    private Gui gui = new Gui();

    private Thread gameThread = new Thread(this);
    private FrameTimer fpsTimer = new FrameTimer(1/FPS * 1000);

    private World world = new World();

    Game() {
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

    private void update() {
        world.update(fpsTimer.getDtSeconds());
    }

    private void render() {
        BufferStrategy buffer = gui.getCanvas().getBufferStrategy();
        if (buffer == null) {
            gui.getCanvas().createBufferStrategy(3);
            return ;
        }
        Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.clearRect(0, 0, WIDTH, HEIGHT);
        world.render(g);
        // RegionManager.getInstance().render(g);
        buffer.show();
        g.dispose();
    }

    public static void main(String[] args){
        Game game = new Game();
    }


}
