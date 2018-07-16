package edu.ucsb.boning.game;

public class Parameters {

    public static final double MAX_VALUE = 100;
    //Speed
    public static final double INIT_SPEED_SHEEP = 40;
    public static final double INIT_SPEED_WOLF = 60;
    public static final double HUNGER_DECAY_EXP_FACTOR = 0.015;
    //Boost
    public static final double BOOST_FACTOR_WOLF = 3;
    public static final double BOOST_FACTOR_SHEEP = 2;
    //Age
    public static final double MAX_AGE_SHEEP = 8;
    public static final double MAX_AGE_WOLF = 12;
    //Food
    public static final double FOOD_THRSHOLD = 40;
    public static final double FOOD_DROP_BOOST_FACTOR = 1.5;
    public static final double FOOD_DROP_RATE_SHEEP = 20;
    public static final double FOOD_DROP_RATE_WOLF = 25;
    public static final double FOOD_EAT_RATE_SHEEP = 60;
    public static final double FOOD_EAT_VALUE_WOLF = 50;
    public static final double FOOD_SHORTAGE_TH_SHEEP = 5;
    public static final double FOOD_SHORTAGE_EXP_SHEEP = 2;
    //Sex
    public static final double SEX_THRESHOLD = 65;
    public static final double SEX_INCREASE_RATE_SHEEP = 45;
    public static final double SEX_INCREASE_RATE_WOLF = 25;
    public static final double PREGNENT_PROBABILITY_SHEEP = 0.9;
    public static final double PREGNENT_PROBABILITY_WOLF = 0.85;
    //Game
    public static final double COLLISION_RANGE = 8;

    public static double constrainParametersInRange(double value, double min, double max) {
        value = value > max ? max : value;
        value = value < min ? min : value;
        return value;
    }
}
