package edu.ucsb.boning.entities;

import edu.ucsb.boning.game.Parameters;
import edu.ucsb.boning.game.World;
import edu.ucsb.boning.utilities.Point;
import edu.ucsb.boning.game.Game;

import java.awt.*;
import java.util.ArrayList;


public class Wolf extends Animal {

    public static final int FILL_COLOR = 0xD44B4C;


    private int sightRrange = 40;
    private Sheep huntTarget = null;

    public Wolf() {
        super();
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        food -= 10 * dt;
        /*************************************************************************************************/
        if(state == State.NORMAL){
            if (Point.getDistance(position, destination) < Parameters.COLLISION_RANGE) {
                destination = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
            }
            setDirection();
            position.translate(speed * Math.cos(rad) * dt, speed * Math.sin(rad) * dt);
            // State changing
            if (food < Parameters.FOOD_THRSHOLD)
                setState(State.HUNGER);

        /*************************************************************************************************/
        } else if (state == State.HUNGER) {
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
                }
            }
            if (huntTarget != null) {
                setDirection(huntTarget.getPosition());
                position.translate(Parameters.BOOST_FACTOR * speed * Math.cos(rad) * dt, Parameters.BOOST_FACTOR * speed * Math.sin(rad) * dt);
                if (Point.getDistance(position, huntTarget.position) <= Parameters.COLLISION_RANGE) {
                    hunt();
                }
                // State changing
                if (food > Parameters.FOOD_THRSHOLD && sex < Parameters.SEX_THRESHOLD)
                    setState(State.NORMAL);
                /*
                if (food > Parameters.FOOD_THRSHOLD && sex >= Parameters.SEX_THRESHOLD)
                    setState(State.MATE);*/
            }

        /*************************************************************************************************/
        } else if (state == State.MATE) {

        /*************************************************************************************************/
        } else {

        }
        if (food < 0)
            state = State.DEAD;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(FILL_COLOR));
        g.fillRoundRect(position.getX()-SIZE/2, position.getY()-SIZE/2, SIZE, SIZE, SIZE/2, SIZE/2);
        g.drawString(Integer.toString((int)food), position.getX(), position.getY() - 5);
    }

    private void hunt() {
        huntTarget.setState(State.DEAD);
        huntTarget = null;
        food += 10;
        food = food > Parameters.MAX_VALUE ? 100 : food;
    }

    @Override
    protected void setNormalState() {
        huntTarget = null;
        state = State.NORMAL;
    }

    @Override
    protected void setMateState() {
        huntTarget = null;
        state = State.MATE;
    }

    @Override
    protected void setHungerState() {
        huntTarget = null;
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
