package edu.ucsb.boning.utilities;

public class LogData {
    private final double time;
    private final int sheepNumber;
    private final int wolvesNumber;

    public LogData(double time, int sheepNumber, int wolvesNumber) {
        this.time = time;
        this.sheepNumber = sheepNumber;
        this.wolvesNumber = wolvesNumber;
    }

    public double getTime() {
        return time;
    }

    public int getSheepNumber() {
        return sheepNumber;
    }

    public int getWolvesNumber() {
        return wolvesNumber;
    }
}
