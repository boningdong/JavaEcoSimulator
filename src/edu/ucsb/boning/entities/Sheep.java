package edu.ucsb.boning.entities;

import edu.ucsb.boning.Utilities.Point;
import edu.ucsb.boning.game.Game;

import java.awt.*;
import java.util.ArrayList;

public class Sheep extends Animal {

    public static final int FILL_COLOR = 0x5AD2CC;

    private Animal.State state = State.NORMAL;

    public Sheep() {
        super();
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if (state == State.NORMAL) {

            if (Point.getDistance(position, destination) < Game.COLLISION_RANGE) {
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
