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

    private boolean debugRender = false;
    private double speed = Parameters.getRandomizedParameter(Parameters.INIT_SPEED_WOLF, 0.1);
    private double sightRrange = Parameters.INIT_SIGHT_WOLF;
    private Sheep huntTarget = null;
    private Wolf mateTarget = null;
    private Wolf wolfBaby = null;

    public Wolf() {
        super();
    }

    public Wolf(Wolf p1, Wolf p2) {
        super();
        food = (p1.food + p2.food) / 2;
        position = Point.getRandomPointNear(p1.position, Parameters.NEW_BORN_DELIVER_RANGE);
        speed = Parameters.getRandomizedParameter((p1.speed + p2.speed)/2, Parameters.MUTATION_TOLERANCE_WOLF);
        sightRrange = Parameters.getRandomizedParameter((p1.sightRrange + p2.sightRrange)/2, Parameters.MUTATION_TOLERANCE_WOLF);

        double initSpeed = Parameters.INIT_SPEED_WOLF;
        double initSight = Parameters.INIT_SIGHT_WOLF;
        double limit = Parameters.MAX_MUTATION_FACTOR;
        speed = Parameters.constrainParametersInRange(speed, initSpeed / limit, initSpeed * limit);
        sightRrange = Parameters.constrainParametersInRange(sightRrange, initSight / limit, initSight * limit);
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        // Normal State
        if(state == State.NORMAL){
            normalWalkAround(dt);
            // State changing
            if (food < Parameters.FOOD_THRSHOLD)
                setState(State.HUNGER);
            else if (food > Parameters.FOOD_THRSHOLD && sex > Parameters.SEX_THRESHOLD)
                setState(State.MATE);
        }
        // Hunger State
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
                if (huntTarget == null)
                    normalWalkAround(dt);
            } else {
                setDirection(huntTarget.getPosition());
                double a = Math.exp((food - Parameters.MAX_VALUE) * Parameters.HUNGER_DECAY_EXP_FACTOR);
                position.translate(a * Parameters.BOOST_FACTOR_WOLF * speed * Math.cos(rad) * dt, a * Parameters.BOOST_FACTOR_WOLF * speed * Math.sin(rad) * dt);
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
        // Mate State
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
                if (mateTarget == null)
                    normalWalkAround(dt);
            } else {
                setDirection(mateTarget.position);
                double a = Math.exp((food - Parameters.MAX_VALUE) * Parameters.HUNGER_DECAY_EXP_FACTOR);
                position.translate(a * Parameters.BOOST_FACTOR_WOLF * speed * Math.cos(rad) * dt, a * Parameters.BOOST_FACTOR_WOLF * speed * Math.sin(rad) * dt);
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
            g.setColor(new Color(0xE25245));
        else if (state == State.HUNGER)
            g.setColor(new Color(0xF2683F));
        else if (state == State.MATE)
            g.setColor(new Color(0xD65F8A));
        g.fillRoundRect(position.getX()-SIZE/2, position.getY()-SIZE/2, SIZE, SIZE, SIZE/2, SIZE/2);

        if(debugRender) {
            g.drawString("food:" + Integer.toString((int) food), position.getX() - 3, position.getY() - 7);
            g.drawString("age:" + Integer.toString((int) age), position.getX() - 3, position.getY() - 16);
        }
    }

    private void normalWalkAround(double dt) {
        if (Point.getDistance(position, destination) < Parameters.COLLISION_RANGE) {
            destination = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
        }
        setDirection();
        double a = Math.exp((food - Parameters.MAX_VALUE) * Parameters.HUNGER_DECAY_EXP_FACTOR);
        position.translate(a * speed * Math.cos(rad) * dt, a * speed * Math.sin(rad) * dt);
        updateBasicProperties(+1 * dt, -Parameters.FOOD_DROP_RATE_WOLF * dt, Parameters.SEX_INCREASE_RATE_WOLF * dt);
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
        double a = Math.exp((food + mateTarget.food - 2 * Parameters.MAX_VALUE)/2 * Parameters.PREGNENT_EXP_DECAY_FACTOR_WOLF);
        if ((new Random().nextFloat() < Parameters.PREGNENT_PROBABILITY_WOLF * a) && wolfBaby == null) {
            if (!mateTarget.isPregnent())
                mateTarget.pregnent(new Wolf(this, mateTarget));
        }
        food -= Parameters.FOOD_DROP_SEX_WOLF;
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

    public double getSightRrange() {
        return sightRrange;
    }

    public double getSpeed() {
        return speed;
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
