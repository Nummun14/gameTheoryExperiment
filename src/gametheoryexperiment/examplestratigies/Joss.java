package gametheoryexperiment.examplestratigies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class Joss extends Strategy {
    public Joss() {
        super("Joss");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> opponentHistory) {
        if (opponentHistory.isEmpty())
            return true;

        double randomValue = Math.random();
        return randomValue > 0.1 ? opponentHistory.get(opponentHistory.size() - 1) : false;
    }
}