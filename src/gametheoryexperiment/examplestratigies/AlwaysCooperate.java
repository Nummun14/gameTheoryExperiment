package gametheoryexperiment.examplestratigies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class AlwaysCooperate extends Strategy {
    public AlwaysCooperate() {
        super("Always Cooperate");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> opponentHistory) {
        return true;
    }
}