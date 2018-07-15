package edu.ucsb.boning.plotter;

import edu.ucsb.boning.utilities.LogData;

import java.awt.*;
import java.util.ArrayList;

public class Plotter {
    public final static int WIDTH = 1300;
    public final static int HEIGHT = 600;
    public static Plotter plotter;

    private double time = 0;
    private int preferredTimeStampsNum = 5000;
    private double dx = WIDTH / preferredTimeStampsNum;
    private ArrayList<LogData> timeStamps = new ArrayList<>();
    private int sheepMaxNumber = 0;
    private int wolfMaxNumber = 0;

    private Plotter(){
        timeStamps.add(new LogData(0, 0, 0));
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

    public void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.clearRect(0, 0, WIDTH, HEIGHT);
        for (int i = 0; i < timeStamps.size() - 1; i++) {
            plotSheep(i, g);
            plotWolves(i, g);
        }
    }

    private void plotSheep(int i, Graphics2D g) {
        g.setColor(new Color(0x3B98A5));
        g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int x0 = (int)(i * dx);
        int y0 = getHeightInScale(timeStamps.get(i).getSheepNumber(), sheepMaxNumber);
        int x1 = (int)((i + 1) * dx);
        int y1 = getHeightInScale(timeStamps.get(i+1).getSheepNumber(), sheepMaxNumber);
        g.drawLine(x0, y0, x1, y1);
    }

    private void plotWolves(int i, Graphics2D g) {
        g.setColor(new Color(0xA34C55));
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

}
