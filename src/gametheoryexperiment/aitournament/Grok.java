package gametheoryexperiment.aitournament;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class Grok extends Strategy {
    public Grok() {
        super("Grok");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        if (opponentHistory.isEmpty()) {
            return true;
        }
        return opponentHistory.get(opponentHistory.size() - 1);
    }
}