package edu.ucsb.boning.utilities;


import edu.ucsb.boning.game.Game;

import java.util.Random;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public static Point getRandomPoint(int xRange, int yRange) {
        double x = new Random().nextInt(xRange);
        double y = new Random().nextInt(yRange);
        return new Point(x, y);
    }

    public static Point constrainInFrame(Point p) {
        int x = p.getX();
        int y = p.getY();
        x = x >= Game.WIDTH ? Game.WIDTH - 1 : x;
        x = x < 0 ? 0 : x;
        y = y >= Game.HEIGHT ? Game.HEIGHT - 1 : y;
        y = y < 0 ? 0 : y;
        return  new Point(x, y);
    }

    public static boolean isOutOfBoundary(Point p, int x0, int y0, int x1, int y1) {
        if (p.getX() < x0 || p.getX() >= x1)
            return true;
        if (p.getY() < y0 || p.getY() >= y1)
            return true;
        return false;
    }

    public static double getDistance(Point p1, Point p2) {
        double dx2 = Math.pow(p1.x - p2.x, 2);
        double dy2 = Math.pow(p1.y - p2.y, 2);
        return Math.sqrt(dx2 + dy2);
    }

    public static Point getRandomPointNear(Point p1, double r) {
        double rad = new Random().nextFloat() * 2 * Math.PI;
        double radius = (new Random().nextGaussian() + 1) * r;
        return new Point(p1.x + radius * Math.cos(rad), p1.y + radius * Math.sin(rad));
    }
}
