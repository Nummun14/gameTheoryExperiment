package gametheoryexperiment.examplestratigies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class Tester extends Strategy {
    private boolean shouldAlternate = false;

    public Tester() {
        super("Tester");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> opponentHistory) {
        if (opponentHistory.isEmpty())
            return false;
        if (opponentHistory.get(0) && !shouldAlternate)
            shouldAlternate = true;
        if (shouldAlternate)
            return opponentHistory.size() % 2 == 0;
        return true;
    }
}