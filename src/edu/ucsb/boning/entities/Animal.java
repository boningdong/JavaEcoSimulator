package edu.ucsb.boning.entities;

import edu.ucsb.boning.Utilities.Point;
import edu.ucsb.boning.game.Game;
import edu.ucsb.boning.game.RegionManager;

import java.awt.*;

public abstract class Animal extends Entity {

    public enum State{NORMAL, MATE, HUNGER, DANGER};

    public static final int SIZE = 10;
    public static final int THR_SEX = 60;
    public static final int THR_FOOD = 60;
    public static final int MAX_VALUE = 100;

    Point destination = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
    RegionManager regionManager = RegionManager.getInstance();

    double age = 0;
    double food = 100;
    double sex = 0;
    boolean mature = false;

    int speed = 30;
    double rad = 0;
    int sightRrange = 40;

    public Animal() {
        setDirection();
        regionManager.register(this);
    }

    void setDirection() {
        double dy = destination.getY() - position.getY();
        double dx = destination.getX() - position.getX();
        rad = Math.atan2(dy, dx);
    }

    public void update(double dt) {
        regionManager.updateEntity(this);
    }
    public abstract void render(Graphics g);
}
