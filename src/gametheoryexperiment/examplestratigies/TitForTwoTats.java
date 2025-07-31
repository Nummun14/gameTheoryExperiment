package gametheoryexperiment.examplestratigies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class TitForTwoTats extends Strategy {
    public TitForTwoTats() {
        super("Tit For Two Tats");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> opponentHistory) {
        if (opponentHistory.size() < 2)
            return true;
        int size = opponentHistory.size();
        return opponentHistory.get(size - 1) || opponentHistory.get(size - 2);
    }
}