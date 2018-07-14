package edu.ucsb.boning.utilities;

public class FrameTimer {
    private double dt;
    private double last = 0;
    private double current = 0;

    public FrameTimer(double dt) {
        this.dt = dt;
    }

    public boolean tick() {
        current = System.currentTimeMillis();
        if (current - last > dt) {
            last = current;
            return true;
        }
        return false;
    }

    public double getDtSeconds() {
        return dt / 1000;
    }
}
