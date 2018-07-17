package edu.ucsb.boning.game;

import edu.ucsb.boning.entities.Animal;
import edu.ucsb.boning.entities.Entity;
import edu.ucsb.boning.entities.Sheep;
import edu.ucsb.boning.entities.Wolf;
import edu.ucsb.boning.utilities.Statistician;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.UUID;

public class World {

    public static World world;

    private boolean debugRender = true;

    private ArrayList<Sheep> sheep = new ArrayList<>();
    private ArrayList<Wolf> wolves = new ArrayList<>();

    private Statistician statistic = new Statistician();

    private World() {
        for(int i = 0; i < Parameters.INIT_SHEEP_NUM; i++) {
            sheep.add(new Sheep());
        }
        for (int i = 0; i < Parameters.INIT_WOLF_NUM; i++){
            wolves.add(new Wolf());
        }
    }

    public void update(double dt) {
        for(Sheep s : sheep)
            s.update(dt);
        for(Wolf w : wolves)
            w.update(dt);

        //Remove dead and add new born:
        ArrayList<Sheep> newSheepList = new ArrayList<>();
        ArrayList<Wolf> newWolfList = new ArrayList<>();
        ArrayList<Entity> removeList = new ArrayList<>();
        for (Sheep s : sheep) {
            if (s.isPregnent())
                newSheepList.add(s.deliveryBaby());
            if (s.getState() == Animal.State.DEAD)
                removeList.add(s);
        }
        for (Wolf w : wolves) {
            if (w.isPregnent())
                newWolfList.add(w.deliveryBaby());
            if (w.getState() == Animal.State.DEAD)
                removeList.add(w);
        }
        for(Entity e: removeList)
            removeEntity(e);
        sheep.addAll(newSheepList);
        wolves.addAll(newWolfList);

        if (debugRender) {
            statistic.reset();
            statistic.update(sheep, wolves);
        }
    }

    public void render(Graphics g) {
        for(Sheep s : sheep)
            s.render(g);
        for(Wolf w : wolves)
            w.render(g);

        if (debugRender) {
            g.setColor(new Color(0xF8889F));
            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString(String.format("Sheep# %d, AvgSpeed: %.2f AvgRange: %.2f", statistic.getSheepNum(), statistic.getAvgSheepSpeed(), statistic.getAvgSheepSight()), 10, 20);
            g.drawString(String.format("Wolf# %d, AvgSpeed: %.2f AvgRange: %.2f", statistic.getWolfNum(), statistic.getAvgWolfSpeed(), statistic.getAvgWolfSight()), 10, 40);
        }
    }

    private void removeEntity(Entity e) {
        if (e instanceof Sheep)
            sheep.remove(e);
        else if (e instanceof Wolf)
            wolves.remove(e);
        RegionManager.getInstance().unregister(e);
    }

    public int getSheepNumbers() {
        return sheep.size();
    }

    public int getWolvesNumbers() {
        return wolves.size();
    }

    public static World getInstance() {
        if (world == null)
            world = new World();
        return world;
    }
}
