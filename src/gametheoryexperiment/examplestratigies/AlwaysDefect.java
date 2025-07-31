package gametheoryexperiment.examplestratigies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class AlwaysDefect extends Strategy {
    public AlwaysDefect() {
        super("Always Defect");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> opponentHistory) {
        return false;
    }
}