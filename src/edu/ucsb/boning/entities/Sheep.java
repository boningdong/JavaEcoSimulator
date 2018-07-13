package edu.ucsb.boning.entities;

import edu.ucsb.boning.Utilities.Point;
import edu.ucsb.boning.game.Game;

import java.awt.*;

public class Sheep {

    public static final int SIZE = 10;

    private Point position = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
    private Point destination = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
    private double speed = 30;
    private double rad = 0;

    public Sheep() {
        setDirection();
    }

    public void update(double dt) {
        if (Point.getDistance(position, destination) < Game.COLLISION_RANGE) {
            destination = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
            setDirection();
        }
        position.translate(speed * Math.cos(rad) * dt, speed * Math.sin(rad) * dt);
    }

    public void render(Graphics g) {
        g.setColor(new Color(0x5AD2CC));
        g.fillRoundRect(position.getX()-SIZE/2, position.getY()-SIZE/2, SIZE, SIZE, SIZE/2, SIZE/2);
    }


    private void setDirection() {
        double dy = destination.getY() - position.getY();
        double dx = destination.getX() - position.getX();
        rad = Math.atan2(dy, dx);
    }
}
