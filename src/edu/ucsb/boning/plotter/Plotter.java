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

    // Plotter appearance style setting
    public final static int xLabelNum = 15;
    public final static double vPadding = 0.1;
    public final static double scaleFactor = 1.4;
    public final Font labelFont = new Font("Arial", Font.BOLD, 10);
    public static Plotter plotter;


    private double time = 0;


    private int preferredTimeStampsNum = 10000;
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

    public void openPlotter() {
        if (!frame.isVisible()) {
            frame.setVisible(true);
        } else {
            frame.setVisible(false);
        }
    }

    @Override
    public void run() {
        while(true) {
            render();
        }
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

    public void render() {
        BufferStrategy buffer = canvas.getBufferStrategy();
        if (buffer == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.clearRect(0, 0, WIDTH, HEIGHT);
        // Start Render
        renderLegend(g);
        renderLabel(g);
        renderData(g);
        // End Render
        buffer.show();
        g.dispose();
    }

    public void renderLegend(Graphics2D g) {
        double totalTime =  timeStamps.get(timeStamps.size() - 1).getTime();
        g.setColor(new Color(0xD5A8D6));
        g.setFont(labelFont);
        g.drawString(String.format("Total time: %.3f s", totalTime), (int)(WIDTH * vPadding), (int)(HEIGHT * vPadding));
    }

    public void renderLabel(Graphics2D g){
        double dx = (double)WIDTH / timeStamps.size();
        for (int i = 0; i < timeStamps.size() - 2; i += timeStamps.size() / xLabelNum) {
            System.out.println(i);
            g.setColor(new Color(0x615969));
            g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, 0, new float[]{3}, 0));
            g.drawLine((int)(i * dx), 5, (int)(i * dx), (int)(HEIGHT * (1-vPadding)));

            g.setColor(new Color(0x887460));
            g.setFont(labelFont);
            g.drawString(String.format("%.2f", timeStamps.get(i).getTime()), (int)(i * dx), (int)(HEIGHT * (1 - vPadding/2)));
        }
    }

    public void renderData(Graphics2D g) {

        double dx = (double)WIDTH / timeStamps.size();
        for (int i = 0; i < timeStamps.size() - 2; i++) {
            plotSheep(i, g, dx);
            plotWolves(i, g, dx);
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
        double dy =  HEIGHT / (maxNumber * scaleFactor);
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
