package edu.ucsb.boning.entities;

import edu.ucsb.boning.utilities.Point;
import edu.ucsb.boning.game.Game;

import java.awt.*;
import java.util.UUID;

public abstract class Entity {

    UUID id = UUID.randomUUID();
    Point position = Point.getRandomPoint(Game.WIDTH, Game.HEIGHT);

    public abstract void update(double dt);
    public abstract void render(Graphics g);

    public Point getPosition() {
        return position;
    }

    public UUID getId() {
        return id;
    }
}
