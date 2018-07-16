package edu.ucsb.boning.plotter;

import edu.ucsb.boning.display.Gui;
import edu.ucsb.boning.utilities.LogData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Plotter implements Runnable{
    public final static int WIDTH = 480;
    public final static int HEIGHT = 300;
    public static Plotter plotter;


    private double time = 0;


    private int preferredTimeStampsNum = 5000;
    private ArrayList<LogData> timeStamps = new ArrayList<>();
    private int sheepMaxNumber = 0;
    private int wolfMaxNumber = 0;

    //GUI
    private JFrame frame = new JFrame();
    private Canvas canvas = new Canvas();

    private Plotter(){
        canvas.setSize(new Dimension(WIDTH, HEIGHT));
        canvas.setBackground(Gui.GUI_BGCOLOR);
        frame.setTitle("Game data plot");
        frame.add(canvas);
        frame.pack();
        frame.setVisible(false);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void run() {
        while(true) {
            render();
        }
    }

    public void render() {
        BufferStrategy buffer = canvas.getBufferStrategy();
        if (buffer == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        renderData(g);
        buffer.show();
        g.dispose();
    }

    public void log(double dt, int sheepNum, int wolvesNum) {
        time += dt;
        timeStamps.add(new LogData(time, sheepNum, wolvesNum));
        while (timeStamps.size() > preferredTimeStampsNum) {
            timeStamps.remove(0);
        }
        sheepMaxNumber = sheepNum > sheepMaxNumber ? sheepNum : sheepMaxNumber;
        wolfMaxNumber =  wolvesNum > wolfMaxNumber ? wolvesNum : wolfMaxNumber;
    }

    public void renderData(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.clearRect(0, 0, WIDTH, HEIGHT);
        double dx = (double)WIDTH / timeStamps.size();
        for (int i = 0; i < timeStamps.size() - 2; i++) {
            plotSheep(i, g, dx);
            plotWolves(i, g, dx);
        }
    }

    public void openPlotter() {
        if (!frame.isVisible()) {
            frame.setVisible(true);
        } else {
            frame.setVisible(false);
        }
    }

    private void plotSheep(int i, Graphics2D g, double dx) {
        g.setColor(new Color(0x52CFE0));
        g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int x0 = (int)(i * dx);
        int y0 = getHeightInScale(timeStamps.get(i).getSheepNumber(), sheepMaxNumber);
        int x1 = (int)((i + 1) * dx);
        int y1 = getHeightInScale(timeStamps.get(i+1).getSheepNumber(), sheepMaxNumber);
        g.drawLine(x0, y0, x1, y1);
    }

    private void plotWolves(int i, Graphics2D g, double dx) {
        g.setColor(new Color(0xEB6B7E));
        g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int x0 = (int)(i * dx);
        int y0 = getHeightInScale(timeStamps.get(i).getWolvesNumber(), wolfMaxNumber);
        int x1 = (int)((i + 1) * dx);
        int y1 = getHeightInScale(timeStamps.get(i+1).getWolvesNumber(), wolfMaxNumber);
        g.drawLine(x0, y0, x1, y1);
    }

    private int getHeightInScale(int num, int maxNumber) {
        double dy =  HEIGHT / (maxNumber * 1.4);
        double height = dy * num;
        return HEIGHT - (int)height;
    }

    public static Plotter getInstance(){
        if (plotter == null)
            plotter = new Plotter();
        return plotter;
    }

    public static void main(String[] args) {
        Plotter plotter = getInstance();
    }
}
