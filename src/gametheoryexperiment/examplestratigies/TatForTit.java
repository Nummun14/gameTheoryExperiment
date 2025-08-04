package gametheoryexperiment.examplestratigies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class TatForTit extends Strategy {
    public TatForTit() {
        super("Tat for tit");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> opponentHistory) {
        if (opponentHistory.isEmpty())
            return false;
        return !opponentHistory.get(opponentHistory.size() - 1);
    }
}