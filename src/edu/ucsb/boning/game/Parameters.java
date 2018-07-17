package edu.ucsb.boning.game;

import java.util.Random;

public class Parameters {

    //Game
    public static final int INIT_SHEEP_NUM = 200;
    public static final int INIT_WOLF_NUM = 30;
    public static final double COLLISION_RANGE = 8;

    public static final double MAX_VALUE = 100;
    //Sight
    public static final double INIT_SIGHT_SHEEP = 40;
    public static final double INIT_SIGHT_WOLF = 50;
    //Speed
    public static final double INIT_SPEED_SHEEP = 35;
    public static final double INIT_SPEED_WOLF = 60;
    public static final double HUNGER_DECAY_EXP_FACTOR = 0.007;
    //Boost
    public static final double BOOST_FACTOR_WOLF = 3;
    public static final double BOOST_FACTOR_SHEEP = 2;
    //Age
    public static final double MAX_AGE_SHEEP = 8;
    public static final double MAX_AGE_WOLF = 14;
    //Food
    public static final double FOOD_THRSHOLD = 50;
    public static final double FOOD_DROP_BOOST_FACTOR = 1.2;
    public static final double FOOD_DROP_RATE_SHEEP = 20;
    public static final double FOOD_DROP_RATE_WOLF = 18;
    public static final double FOOD_DROP_SEX_WOLF = 10;
    public static final double FOOD_EAT_RATE_SHEEP = 60;
    public static final double FOOD_EAT_VALUE_WOLF = 50;
    public static final double FOOD_SHORTAGE_TH_SHEEP = 5;
    public static final double FOOD_SHORTAGE_EXP_SHEEP = 2;
    //Sex
    public static final double SEX_THRESHOLD = 65;
    public static final double SEX_INCREASE_RATE_SHEEP = 40;
    public static final double SEX_INCREASE_RATE_WOLF = 30;
    public static final double PREGNENT_EXP_DECAY_FACTOR_WOLF = 0;
    public static final double PREGNENT_PROBABILITY_SHEEP = 0.9;
    public static final double PREGNENT_PROBABILITY_WOLF = 0.75;
    public static final double NEW_BORN_DELIVER_RANGE = 60;
    //Mutation
    public static final double MUTATION_TOLERANCE_WOLF = 0.2;
    public static final double MUTATION_TOLERANCE_SHEEP = 0.2;
    public static final double MAX_MUTATION_FACTOR = 1.3;

    public static double constrainParametersInRange(double value, double min, double max) {
        value = value > max ? max : value;
        value = value < min ? min : value;
        return value;
    }

    public static double getRandomizedParameter(double value, double tolerance) {
        double t = (new Random().nextDouble() - 0.5) * 2 * tolerance;
        return value * (1 + t);
    }
}
