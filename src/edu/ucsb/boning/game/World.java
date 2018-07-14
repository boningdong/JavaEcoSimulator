package edu.ucsb.boning.game;

import edu.ucsb.boning.entities.Animal;
import edu.ucsb.boning.entities.Entity;
import edu.ucsb.boning.entities.Sheep;
import edu.ucsb.boning.entities.Wolf;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.UUID;

public class World {

    public static World world;

    private ArrayList<Sheep> sheep = new ArrayList<>();
    private ArrayList<Wolf> wolves = new ArrayList<>();

    private World() {
        for(int i = 0; i < 200; i++) {
            sheep.add(new Sheep());
        }
        for (int i = 0; i < 20; i++){
            wolves.add(new Wolf());
        }
    }

    public void update(double dt) {
        for(Sheep s : sheep)
            s.update(dt);
        for(Wolf w : wolves)
            w.update(dt);

        //Remove:
        ArrayList<Entity> removeList = new ArrayList<>();
        for (Sheep s : sheep) {
            if (s.getState() == Animal.State.DEAD)
                removeList.add(s);
        }
        for (Wolf w : wolves) {
            if (w.getState() == Animal.State.DEAD)
                removeList.add(w);
        }
        for(Entity e: removeList)
            removeEntity(e);
    }

    public void render(Graphics g) {
        for(Sheep s : sheep)
            s.render(g);
        for(Wolf w : wolves)
            w.render(g);
    }

    private void removeEntity(Entity e) {
        if (e instanceof Sheep)
            sheep.remove(e);
        else if (e instanceof Wolf)
            wolves.remove(e);
        RegionManager.getInstance().unregister(e);
    }

    public static World getInstance() {
        if (world == null)
            world = new World();
        return world;
    }
}
