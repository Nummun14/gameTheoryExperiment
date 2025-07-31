package gametheoryexperiment.examplestratigies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class Random extends Strategy {
    public Random() {
        super("Random");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> opponentHistory) {
        return Math.random() < 0.5;
    }
}