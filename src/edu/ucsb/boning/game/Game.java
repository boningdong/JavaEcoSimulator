package edu.ucsb.boning.game;

import edu.ucsb.boning.entities.Entity;
import edu.ucsb.boning.entities.Sheep;
import edu.ucsb.boning.entities.Wolf;
import edu.ucsb.boning.plotter.Plotter;
import edu.ucsb.boning.utilities.FrameTimer;
import edu.ucsb.boning.display.Gui;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game implements Runnable{

    // Game Parameters
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final double FPS = 30.0;

    private Gui gui = new Gui();

    private Thread gameThread = new Thread(this);
    private Thread plotThread = new Thread(Plotter.getInstance());
    private FrameTimer fpsTimer = new FrameTimer(1/FPS * 1000);

    private World world = World.getInstance();

    Game() {
        gameThread.start();
        plotThread.start();
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
        Plotter.getInstance().log(fpsTimer.getDtSeconds(), world.getSheepNumbers(), world.getWolvesNumbers());
    }

    private void render() {
        renderGame();
    }

    private void renderGame() {
        BufferStrategy buffer = gui.getCanvas().getBufferStrategy();
        if (buffer == null) {
            gui.getCanvas().createBufferStrategy(3);
            return ;
        }
        Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setFont(new Font("Arial", Font.BOLD, 8));
        world.render(g);
        RegionManager.getInstance().render(g);
        buffer.show();
        g.dispose();
    }

    public static void main(String[] args){
        Game game = new Game();
    }


}
