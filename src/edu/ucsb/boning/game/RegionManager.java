package edu.ucsb.boning.game;

import edu.ucsb.boning.entities.Sheep;
import edu.ucsb.boning.utilities.Point;
import edu.ucsb.boning.entities.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class RegionManager {
    private static RegionManager regionManager = new RegionManager();
    private static final int xRegionNumbers = 20;
    private static final int yRegionNumbers = 15;
    private static boolean debugRender = false;

    private int xLength = Game.WIDTH / xRegionNumbers;
    private int yLength = Game.HEIGHT / yRegionNumbers;

    private Hashtable<Entity, ArrayList<Entity>> entityRecord = new Hashtable<>();
    private ArrayList<Entity>[] regions;

    private RegionManager() {
        regions = new ArrayList[xRegionNumbers * yRegionNumbers];
        for(int i = 0; i < regions.length; i++) {
            regions[i] = new ArrayList<>();
        }
    }

    public boolean register(Entity e) {
        if (entityRecord.get(e) == null) {
            ArrayList<Entity> region = getRegion(e.getPosition());
            region.add(e);
            entityRecord.put(e, region);
            return true;
        }
        System.out.println("Error: register failed. Already registered");
        return false;
    }

    public boolean unregister(Entity e) {
        ArrayList<Entity> region = entityRecord.get(e);
        if (region == null) {
            System.out.println("Error: unregister failed. Entity doesn't exist");
            return false;
        }
        region.remove(e);
        entityRecord.remove(e);
        return true;
    }

    public void updateEntity(Entity e) {
        if (!entityRecord.containsKey(e)) {
            System.out.println("Error: update failed. Entity is not in record");
            return;
        }
        ArrayList<Entity> region = getRegion(e.getPosition());
        // If entity is registered in RegionManager
        if (region != entityRecord.get(e)) {
            // TODO: delete the entity from the old region, put it in new region, and overwrite the record.
            entityRecord.get(e).remove(e);
            region.add(e);
            entityRecord.put(e, region);
        }
    }

    public ArrayList<Entity> getEntitiesInRange(Entity e, double r) {
        int i = getRegionIndex(e.getPosition());
        int x = i % xRegionNumbers;
        int y = i / xRegionNumbers;

        double rightBound = e.getPosition().getX() + r;
        rightBound = rightBound >= Game.WIDTH ? Game.WIDTH - 1 : rightBound;
        double leftBound = e.getPosition().getX() - r;
        leftBound = leftBound < 0 ? 0 : leftBound;
        double lowerBound = e.getPosition().getY() + r;
        lowerBound = lowerBound >= Game.HEIGHT ? Game.HEIGHT - 1 : lowerBound;
        double upperBound = e.getPosition().getY() - r;
        upperBound = upperBound < 0 ? 0 : upperBound;

        int x0 = (int) (leftBound / xLength);
        int x1 = (int) (rightBound / xLength);
        int y0 = (int) (upperBound / yLength);
        int y1 = (int) (lowerBound / yLength);

        ArrayList<Entity> entities = new ArrayList<>();
        for(x = x0; x <= x1; x++) {
            for (y = y0; y <= y1; y++) {
                ArrayList<Entity> region = regions[x + y * xRegionNumbers];
                entities.addAll(region);
            }
        }

        return entities;
    }

    public void render(Graphics2D g) {
        if (debugRender) {
            g.setColor(new Color(0x34000A));
            for (int x = 1; x < xRegionNumbers; x++) {
                g.drawLine(xLength * x, 0, xLength * x, yLength * yRegionNumbers);
            }
            for (int y = 1; y < yRegionNumbers; y++) {
                g.drawLine(0, yLength * y, xLength * xRegionNumbers, yLength * y);
            }

            for (int x = 0; x < xRegionNumbers; x++) {
                for (int y = 0; y < yRegionNumbers; y++) {
                    int xx = x * xLength + xLength / 2;
                    int yy = y * yLength + yLength / 2;
                    ArrayList<Entity> region = getRegion(new Point(xx, yy));
                    g.drawString(Integer.toString(region.size()), xx, yy);
                }
            }
        }
    }

    public ArrayList<Entity> getRegion(Point p) {
        return regions[getRegionIndex(p)];
    }

    private int getRegionIndex(Point p) {
        p = Point.constrainInFrame(p);
        return p.getX()/xLength + p.getY()/yLength * xRegionNumbers;
    }

    public static RegionManager getInstance() {
        return regionManager;
    }

    public static void main(String[] args) {
        Hashtable<Entity, Integer> t = new Hashtable<>();
        Entity e = new Sheep();
        Sheep s = (Sheep) e;
        t.put(e, 20);
        System.out.println();
    }
}
