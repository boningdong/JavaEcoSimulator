package edu.ucsb.boning.entities;

import edu.ucsb.boning.Utilities.Point;
import edu.ucsb.boning.game.Game;

import java.awt.*;

public class Wolf extends Animal {

    public static final int FILL_COLOR = 0xD44B4C;

    public Wolf() {
        super();
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if (Point.getDistance(position, destination) < Game.COLLISION_RANGE) {
            destination = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);
            setDirection();
        }
        position.translate(speed * Math.cos(rad) * dt, speed * Math.sin(rad) * dt);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(FILL_COLOR));
        g.fillRoundRect(position.getX()-SIZE/2, position.getY()-SIZE/2, SIZE, SIZE, SIZE/2, SIZE/2);
    }
}
