package edu.ucsb.boning.utilities;

import edu.ucsb.boning.entities.Sheep;
import edu.ucsb.boning.entities.Wolf;

import java.util.ArrayList;

public class Statistician {
    private double sheepSpeedSum = 0;
    private double wolfSpeedSum = 0;
    private double sheepSightSum = 0;
    private double wolfSightSum = 0;
    private int sheepNum = 0;
    private int wolfNum = 0;

    public void update(ArrayList<Sheep> sheep, ArrayList<Wolf> wolves) {
        sheepNum = sheep.size();
        for(Sheep s : sheep) {
            sheepSpeedSum += s.getSpeed();
            sheepSightSum += s.getSightRrange();
        }
        wolfNum = wolves.size();
        for (Wolf w : wolves) {
            wolfSpeedSum += w.getSpeed();
            wolfSightSum += w.getSightRrange();
        }
    }

    public int getSheepNum(){
        return sheepNum;
    }

    public int getWolfNum() {
        return wolfNum;
    }

    public double getAvgSheepSpeed() {
        return sheepSpeedSum / sheepNum;
    }

    public double getAvgSheepSight() {
        return sheepSightSum / sheepNum;
    }

    public double getAvgWolfSpeed() {
        return wolfSpeedSum / wolfNum;
    }

    public double getAvgWolfSight() {
        return wolfSightSum / wolfNum;
    }

    public void reset() {
        sheepSpeedSum = 0;
        wolfSpeedSum = 0;
        sheepSightSum = 0;
        wolfSightSum = 0;
        sheepNum = 0;
        wolfNum = 0;
    }
}
