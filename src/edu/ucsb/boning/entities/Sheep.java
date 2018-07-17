package edu.ucsb.boning.entities;

import edu.ucsb.boning.display.Gui;
import edu.ucsb.boning.game.Parameters;
import edu.ucsb.boning.utilities.Point;
import edu.ucsb.boning.game.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Sheep extends Animal {

    public static final int FILL_COLOR = 0x5AD2CC;
    public static boolean debugRender = false;

    int sightRrange = 40;
    private double speed = Parameters.INIT_SPEED_SHEEP;
    private Sheep mateTarget = null;
    private Wolf dangerSource = null;
    private Sheep sheepBaby = null;

    public Sheep() {
        super();
    }

    public Sheep(Sheep p1, Sheep p2) {
        super();
        food = (p1.food + p2.food) / 2;
        position = Point.getRandomPointNear(p1.position, Parameters.NEW_BORN_DELIVER_RANGE);
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        // Normal State
        if (state == State.NORMAL) {
            normalWalkAround(dt);
            // Change state:
            if (dangerSource == null)
                dangerSource = alertDangerSource();
            if (dangerSource != null)
                setState(State.DANGER);
            else if (sex > Parameters.SEX_THRESHOLD) {
                setState(State.MATE);
            }
        }
        // Mate State
        else if (state == State.MATE ) {
            if (mateTarget != null && ((mateTarget.getState() == State.DEAD) || (Point.getDistance(position, mateTarget.position) > sightRrange)))
                mateTarget = null;
            if (mateTarget == null) {
                ArrayList<Entity> entities = regionManager.getEntitiesInRange(this, sightRrange);
                for (Entity e : entities) {
                    if ((e instanceof Sheep) && Point.getDistance(position, e.getPosition()) < sightRrange && e != this) {
                        mateTarget = (Sheep) e;
                        break;
                    }
                }
                if (mateTarget == null)
                    normalWalkAround(dt);
            }
            else {
                //if (mateTarget != null)
                setDirection(mateTarget.position);
                double a = Math.exp((food - Parameters.MAX_VALUE) * Parameters.HUNGER_DECAY_EXP_FACTOR);
                position.translate(a * speed * Parameters.BOOST_FACTOR_SHEEP * Math.cos(rad) * dt, a * speed * Parameters.BOOST_FACTOR_SHEEP * Math.sin(rad) * dt);
                updateBasicProperties(+1 * dt, -Parameters.FOOD_DROP_RATE_SHEEP * Parameters.FOOD_DROP_BOOST_FACTOR * dt, +Parameters.SEX_INCREASE_RATE_SHEEP * dt);
                if (Point.getDistance(position, mateTarget.position) < Parameters.COLLISION_RANGE)
                    mate();
            }

            if (dangerSource == null)
                dangerSource = alertDangerSource();

            if (dangerSource != null)
                setState(State.DANGER);
            //else if (dangerSource == null && food < Parameters.FOOD_THRSHOLD) {}
            else if (sex < Parameters.SEX_THRESHOLD)
                setState(State.NORMAL);

        }
        // Danger State
        else if (state == State.DANGER) {
            if (dangerSource != null && dangerSource.getState() == State.DEAD)
                dangerSource = null;
            if (dangerSource == null)
                normalWalkAround(dt);
            else {
                setDirection(dangerSource.position);
                rad += Math.PI;
                double a = Math.exp((food - Parameters.MAX_VALUE) * Parameters.HUNGER_DECAY_EXP_FACTOR);
                position.translate(a * speed * Parameters.BOOST_FACTOR_SHEEP * Math.cos(rad) * dt, a * speed * Parameters.BOOST_FACTOR_SHEEP * Math.sin(rad) * dt);
                updateBasicProperties(+1 * dt, -Parameters.FOOD_DROP_RATE_SHEEP * Parameters.FOOD_DROP_BOOST_FACTOR * dt, +Parameters.SEX_INCREASE_RATE_SHEEP * dt);
            }
            // Change State
            if (dangerSource != null && Point.getDistance(dangerSource.position, position) < sightRrange) { }
            else {
                dangerSource = alertDangerSource();
                if (dangerSource == null && sex < Parameters.SEX_THRESHOLD)
                    setState(State.NORMAL);
                else if (dangerSource == null && sex > Parameters.SEX_THRESHOLD)
                    setState(State.MATE);
            }
        }

        if (food <= 0 || age >= Parameters.MAX_AGE_SHEEP)
            setState(State.DEAD);
        if (Point.isOutOfBoundary(position, 0, 0, Game.WIDTH, Game.HEIGHT)) {
            resetPosition();
            setState(State.NORMAL);
        }
        eat(dt);
    }

    @Override
    public void render(Graphics g) {
        if (state == State.NORMAL)
            g.setColor(new Color(0x5DB0D6));
        else if (state == State.DANGER)
            g.setColor(new Color(0x60C8D6));
        else if (state == State.MATE)
            g.setColor(new Color(0x7B94DD));
        g.fillRoundRect(position.getX()-SIZE/2, position.getY()-SIZE/2, SIZE, SIZE, SIZE/2, SIZE/2);

        if (debugRender) {
            g.setColor(new Color(0x007188));
            g.drawString("food: " + (int)food, position.getX() - 5, getPosition().getY() - 7);
            g.drawString("age: " + (int)age, position.getX() - 5, getPosition().getY() - 16);
        }

    }

    public boolean isPregnent() {
        return (sheepBaby != null);
    }

    public void pregnent(Sheep s) {
        sheepBaby = s;
    }

    public Sheep deliveryBaby() {
        Sheep s = sheepBaby;
        sheepBaby = null;
        return s;
    }

    private Wolf alertDangerSource() {
        ArrayList<Entity> entities = regionManager.getEntitiesInRange(this, sightRrange);
        for(Entity e : entities) {
            if ((e instanceof Wolf) && Point.getDistance(e.position, position) < sightRrange) {
                return (Wolf) e;
            }
        }
        return null;
    }

    private void normalWalkAround(double dt) {
        //From normal
        if (Point.getDistance(position, destination) < Parameters.COLLISION_RANGE) {
            destination = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
        }
        setDirection();
        double a = Math.exp((food - Parameters.MAX_VALUE) * Parameters.HUNGER_DECAY_EXP_FACTOR);
        position.translate(a * speed * Math.cos(rad) * dt, a * speed * Math.sin(rad) * dt);
        updateBasicProperties(+1 * dt, -Parameters.FOOD_DROP_RATE_SHEEP * dt, +Parameters.SEX_INCREASE_RATE_SHEEP * dt);
    }

    private void mate() {
        this.sex = 0;
        mateTarget.sex = 0;
        if ((new Random().nextFloat() < Parameters.PREGNENT_PROBABILITY_SHEEP) && sheepBaby == null) {
            if (!mateTarget.isPregnent())
                mateTarget.pregnent(new Sheep(this, mateTarget));
        }
        mateTarget = null;
    }

    private void eat(double dt) {
        int sheepNum = 0;
        ArrayList<Entity> entities = regionManager.getRegion(position);
        for(Entity e: entities)
            sheepNum += (e instanceof Sheep) ? 1 : 0;
        sheepNum = (int) Parameters.constrainParametersInRange((double)sheepNum - Parameters.FOOD_SHORTAGE_TH_SHEEP, 0, Integer.MAX_VALUE);
        food += Parameters.FOOD_EAT_RATE_SHEEP * Math.exp(-sheepNum * Parameters.FOOD_SHORTAGE_EXP_SHEEP) * dt;
        food = Parameters.constrainParametersInRange(food, Integer.MIN_VALUE, Parameters.MAX_VALUE);
    }

    private void resetPosition() {
        double x = position.getX();
        x = x >= Game.WIDTH ? x - Game.WIDTH : x;
        x = x < 0 ? x + Game.WIDTH : x;
        double y = position.getY();
        y = y >= Game.HEIGHT ? y - Game.HEIGHT : y;
        y = y < 0 ? y + Game.HEIGHT : y;
        position = new Point(x, y);
    }

    @Override
    protected void setNormalState() {
        mateTarget = null;
        dangerSource = null;
        state = State.NORMAL;
    }

    @Override
    protected void setMateState() {
        state = State.MATE;
    }

    @Override
    protected void setHungerState() {

    }

    @Override
    protected void setDangerState() {
        mateTarget = null;
        state = State.DANGER;
    }

    @Override
    protected void setDeadState() {
        state = State.DEAD;
    }


}
