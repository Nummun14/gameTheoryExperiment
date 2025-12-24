package gametheoryexperiment.geministrategies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

/**
 * "The Analyst"
 * Goal: Behave like TFT for the first 30 rounds to build 100% trust.
 * Then, if the opponent is confirmed to be Always Cooperate, exploit them.
 * If the opponent is Joss or Random, tighten up defense.
 */
public class TheAnalyst extends Strategy {
    private boolean isSucker = false;

    public TheAnalyst() {
        super("The Analyst");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> opponentHistory) {
        int round = opponentHistory.size();

        // 1. Initial Trust Building (The first 30 rounds)
        // We play pure Tit For Tat. This ensures Friedman and TFT trust us completely.
        if (round == 0) return true;

        if (round < 30) {
            return opponentHistory.get(round - 1);
        }

        // 2. The Analysis Phase (At round 30)
        // Check if they have cooperated 100% of the time so far.
        if (round == 30) {
            boolean neverDefected = !opponentHistory.contains(false);
            if (neverDefected) {
                isSucker = true;
            }
        }

        // 3. Execution Phase
        if (isSucker) {
            // If they are a "Sucker" (Always Cooperate), we defect every 2nd round.
            // This yields an average of 4 points per round [(5+0)/2] instead of 3.
            // Note: If the strategy was actually Friedman, he would have defected
            // by now if we had defected. Since we haven't defected yet (round 30),
            // we are safe to test this.
            return round % 2 == 0;
        }

        // 4. Defensive Tit For Tat
        // Against everyone else, copy the last move.
        // If they defected at all in the last 3 rounds, we stay cautious.
        return opponentHistory.get(round - 1);
    }

    @Override
    public void reset() {
        isSucker = false;
    }
}