package edu.ucsb.boning.entities;

import edu.ucsb.boning.Utilities.Point;
import edu.ucsb.boning.game.Game;
import edu.ucsb.boning.game.RegionManager;

import java.awt.*;

public abstract class Entity {

    Point position = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);

    public abstract void update(double dt);
    public abstract void render(Graphics g);

    public Point getPosition() {
        return position;
    }
}
