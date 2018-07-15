package edu.ucsb.boning.entities;

import edu.ucsb.boning.game.Parameters;
import edu.ucsb.boning.utilities.Point;
import edu.ucsb.boning.game.Game;
import edu.ucsb.boning.game.RegionManager;

import java.awt.*;

public abstract class Animal extends Entity {

    public enum State{NORMAL, MATE, HUNGER, DANGER, DEAD};

    public static final int SIZE = 10;
    public static final int THR_SEX = 60;
    public static final int THR_FOOD = 60;

    State state = State.NORMAL;
    Point destination = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
    RegionManager regionManager = RegionManager.getInstance();

    double age = 0;
    double food = 100;
    double sex = 0;
    boolean mature = false;

    int speed = 30;
    double rad = 0;


    public Animal() {
        setDirection();
        regionManager.register(this);
    }

    void updateBasicProperties(double dAge, double dFood, double dSex) {
        age += dAge;
        food = Parameters.constrainParametersInRange(food + dFood, 0, Parameters.MAX_VALUE);
        sex = Parameters.constrainParametersInRange(sex + dSex, 0, Parameters.MAX_VALUE);
    }

    void setDirection() {
        double dy = destination.getY() - position.getY();
        double dx = destination.getX() - position.getX();
        rad = Math.atan2(dy, dx);
    }

    void setDirection(Point target) {
        double dy = target.getY() - position.getY();
        double dx = target.getX() - position.getX();
        rad = Math.atan2(dy, dx);
    }

    public void setState(State state) {
        switch (state) {
            case NORMAL:
                setNormalState();
                break;
            case HUNGER:
                setHungerState();
                break;
            case MATE:
                setMateState();
                break;
            case DEAD:
                setDeadState();
                break;
            case DANGER:
                setDangerState();
                break;
            default:
                System.out.println("Invalid state, something went wrong");
        }
    }

    public State getState() {
        return state;
    }

    protected abstract void setNormalState();
    protected abstract void setMateState();
    protected abstract void setHungerState();
    protected abstract void setDangerState();
    protected abstract void setDeadState();

    public void update(double dt) {
        regionManager.updateEntity(this);
    }
    public abstract void render(Graphics g);
}
