package gametheoryexperiment.geministrategies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class MirrorGrim extends Strategy {
    private boolean triggered = false;

    public MirrorGrim() {
        super("Mirror Grim");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> opponentHistory) {
        if (opponentHistory.isEmpty()) return true;

        // If we are triggered, defect forever (Like Friedman)
        if (triggered) return false;

        // Check if opponent defected
        if (!opponentHistory.get(opponentHistory.size() - 1)) {
            // Special rule: Ignore a defection on Round 0 (Tester check)
            if (opponentHistory.size() == 1) {
                return true;
            }
            triggered = true;
            return false;
        }

        return true;
    }

    @Override
    public void reset() {
        triggered = false;
    }
}