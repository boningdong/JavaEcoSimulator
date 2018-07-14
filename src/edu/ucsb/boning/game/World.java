package edu.ucsb.boning.game;

import edu.ucsb.boning.entities.Sheep;
import edu.ucsb.boning.entities.Wolf;

import java.awt.*;
import java.util.ArrayList;

public class World {

    private ArrayList<Sheep> sheep = new ArrayList<>();
    private ArrayList<Wolf> wolves = new ArrayList<>();

    public World() {
        for(int i = 0; i < 100; i++) {
            sheep.add(new Sheep());
            if (i % 10 == 0)
                wolves.add(new Wolf());
        }
    }

    public void update(double dt) {
        for(Sheep s : sheep)
            s.update(dt);
        for(Wolf w : wolves)
            w.update(dt);
    }

    public void render(Graphics g) {
        for(Sheep s : sheep)
            s.render(g);
        for(Wolf w : wolves)
            w.render(g);
    }
}
