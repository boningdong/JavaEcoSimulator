package edu.ucsb.boning.entities;

import edu.ucsb.boning.game.Parameters;
import edu.ucsb.boning.utilities.Point;
import edu.ucsb.boning.game.Game;

import java.awt.*;

public class Sheep extends Animal {

    public static final int FILL_COLOR = 0x5AD2CC;

    int sightRrange = 40;

    public Sheep() {
        super();
    }

    @Override
    protected void setNormalState() {

    }

    @Override
    protected void setMateState() {

    }

    @Override
    protected void setHungerState() {

    }

    @Override
    protected void setDangerState() {

    }

    @Override
    protected void setDeadState() {
        state = State.DEAD;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if (state == State.NORMAL) {

            if (Point.getDistance(position, destination) < Parameters.COLLISION_RANGE) {
                destination = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
                setDirection();
            }
            position.translate(speed * Math.cos(rad) * dt, speed * Math.sin(rad) * dt);

        } else if (state == State.MATE ) {


        } else if (state == State.HUNGER) {


        }

    }


    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0x5AD2CC));
        g.fillRoundRect(position.getX()-SIZE/2, position.getY()-SIZE/2, SIZE, SIZE, SIZE/2, SIZE/2);
    }
}
