package gametheoryexperiment.examplestratigies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class TitForTat extends Strategy {
    public TitForTat() {
        super("Tit For Tat");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> opponentHistory) {
        if (opponentHistory.isEmpty())
            return true;
        return opponentHistory.get(opponentHistory.size() - 1);
    }
}