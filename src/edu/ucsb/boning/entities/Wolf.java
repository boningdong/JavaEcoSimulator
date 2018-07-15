package edu.ucsb.boning.entities;

import edu.ucsb.boning.game.Parameters;
import edu.ucsb.boning.game.World;
import edu.ucsb.boning.utilities.Point;
import edu.ucsb.boning.game.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Wolf extends Animal {

    public static final int FILL_COLOR = 0xD44B4C;

    private boolean debugRender = true;
    private double speed = Parameters.INIT_SPEED_WOLF;
    private int sightRrange = 60;
    private Sheep huntTarget = null;
    private Wolf mateTarget = null;
    private Wolf wolfBaby = null;

    public Wolf() {
        super();
    }

    public Wolf(Wolf w1, Wolf w2) {
        super();
        food = (w1.food + w2.food) / 2;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if(state == State.NORMAL){
            if (Point.getDistance(position, destination) < Parameters.COLLISION_RANGE) {
                destination = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
            }
            setDirection();
            position.translate(speed * Math.cos(rad) * dt, speed * Math.sin(rad) * dt);
            updateBasicProperties(+1 * dt, -Parameters.FOOD_DROP_RATE_WOLF * dt, Parameters.SEX_INCREASE_RATE_WOLF * dt);
            // State changing
            if (food < Parameters.FOOD_THRSHOLD)
                setState(State.HUNGER);
            else if (food > Parameters.FOOD_THRSHOLD && sex > Parameters.SEX_THRESHOLD)
                setState(State.MATE);

        }
        else if (state == State.HUNGER) {
            // Hunger Behavior
            if (huntTarget != null && huntTarget.getState() == State.DEAD)
                huntTarget = null;
            // if (huntTarget != null && )
            if (huntTarget == null) {
                ArrayList<Entity> entities = regionManager.getEntitiesInRange(this, sightRrange);
                for (Entity e : entities) {
                    if ((e instanceof Sheep) && Point.getDistance(e.getPosition(), position) <= sightRrange) {
                        huntTarget = (Sheep) e;
                        break;
                    }
                }
                if (huntTarget == null) {
                    if (Point.getDistance(position, destination) < Parameters.COLLISION_RANGE) {
                        destination = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
                    }
                    setDirection();
                    position.translate(speed * Math.cos(rad) * dt, speed * Math.sin(rad) * dt);
                    updateBasicProperties(+1 * dt, -Parameters.FOOD_DROP_RATE_WOLF * dt, Parameters.SEX_INCREASE_RATE_WOLF * dt);
                }
            } else {
                setDirection(huntTarget.getPosition());
                position.translate(Parameters.BOOST_FACTOR_WOLF * speed * Math.cos(rad) * dt, Parameters.BOOST_FACTOR_WOLF * speed * Math.sin(rad) * dt);
                updateBasicProperties(+1 * dt, -Parameters.FOOD_DROP_RATE_WOLF * Parameters.FOOD_DROP_BOOST_FACTOR * dt, Parameters.SEX_INCREASE_RATE_WOLF * dt);
                if (Point.getDistance(position, huntTarget.position) <= Parameters.COLLISION_RANGE) {
                    hunt();
                }
            }
            // State changing
            if (food > Parameters.FOOD_THRSHOLD && sex < Parameters.SEX_THRESHOLD)
                setState(State.NORMAL);
            else if (food > Parameters.FOOD_THRSHOLD && sex >= Parameters.SEX_THRESHOLD)
                setState(State.MATE);

        }

        else if (state == State.MATE) {
            if (mateTarget != null && ((mateTarget.getState() == State.DEAD) || (Point.getDistance(position, mateTarget.position) > sightRrange)))
                mateTarget = null;
            if (mateTarget == null) {
                ArrayList<Entity> entities = regionManager.getRegion(position);
                for(Entity e : entities) {
                    if ((e instanceof Wolf) && Point.getDistance(position, e.getPosition()) < sightRrange && e != this) {
                        mateTarget = (Wolf) e;
                        break;
                    }
                }
                if (mateTarget == null) {
                    if (Point.getDistance(position, destination) < Parameters.COLLISION_RANGE) {
                        destination = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
                    }
                    setDirection();
                    position.translate(speed * Math.cos(rad) * dt, speed * Math.sin(rad) * dt);
                    updateBasicProperties(+1 * dt, -Parameters.FOOD_DROP_RATE_WOLF * dt, Parameters.SEX_INCREASE_RATE_WOLF * dt);
                }
            } else {
                setDirection(mateTarget.position);
                position.translate(Parameters.BOOST_FACTOR_WOLF * speed * Math.cos(rad) * dt, Parameters.BOOST_FACTOR_WOLF * speed * Math.sin(rad) * dt);
                updateBasicProperties(+1 * dt, -Parameters.FOOD_DROP_RATE_WOLF * Parameters.FOOD_DROP_BOOST_FACTOR * dt, +Parameters.SEX_INCREASE_RATE_WOLF * dt);
                if (Point.getDistance(position, mateTarget.position) <= Parameters.COLLISION_RANGE)
                    mate();
            }
            // State changing
            if (food < Parameters.FOOD_THRSHOLD)
                setState(State.HUNGER);
            else if (food > Parameters.FOOD_THRSHOLD && sex < Parameters.SEX_THRESHOLD)
                setState(State.NORMAL);
        }

        if (food <= 0 || age >= Parameters.MAX_AGE_WOLF)
            setState(State.DEAD);
    }

    @Override
    public void render(Graphics g) {
        if (state == State.NORMAL)
            g.setColor(new Color(FILL_COLOR));
        else if (state == State.HUNGER)
            g.setColor(new Color(0xF20008));
        else if (state == State.MATE)
            g.setColor(new Color(0xD60096));
        g.fillRoundRect(position.getX()-SIZE/2, position.getY()-SIZE/2, SIZE, SIZE, SIZE/2, SIZE/2);

        if(debugRender) {
            g.drawString("food:" + Integer.toString((int) food), position.getX() - 3, position.getY() - 7);
            g.drawString("age:" + Integer.toString((int) age), position.getX() - 3, position.getY() - 16);
        }
    }

    private void hunt() {
        huntTarget.setState(State.DEAD);
        huntTarget = null;
        food += Parameters.FOOD_EAT_VALUE_WOLF;
        food = food > Parameters.MAX_VALUE ? 100 : food;
    }

    private void mate() {
        this.sex = 0;
        mateTarget.sex = 0;
        if ((new Random().nextFloat() < Parameters.PREGNENT_PROBABILITY_WOLF) && wolfBaby == null) {
            if (!mateTarget.isPregnent())
                mateTarget.pregnent(new Wolf(this, mateTarget));
        }
        mateTarget = null;
    }

    public boolean isPregnent() {
        return wolfBaby != null;
    }

    public void pregnent(Wolf w) {
        wolfBaby = w;
    }

    public Wolf deliveryBaby() {
        Wolf w = wolfBaby;
        wolfBaby = null;
        return w;
    }

    @Override
    protected void setNormalState() {
        huntTarget = null;
        mateTarget = null;
        state = State.NORMAL;
    }

    @Override
    protected void setMateState() {
        huntTarget = null;
        state = State.MATE;
    }

    @Override
    protected void setHungerState() {
        mateTarget = null;
        state = State.HUNGER;
    }

    @Override
    protected void setDangerState() {
        System.out.println("Error: Wolf state will not be dangerous.");
    }

    @Override
    protected void setDeadState() {
        state = State.DEAD;
    }

}
