package gametheoryexperiment.examplestratigies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class Friedman extends Strategy {
    private boolean shouldCooperate = true;

    public Friedman() {
        super("Friedman");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> opponentHistory) {
        if (!opponentHistory.isEmpty() && !opponentHistory.get(opponentHistory.size() - 1))
            shouldCooperate = false;
        return shouldCooperate;
    }
}
