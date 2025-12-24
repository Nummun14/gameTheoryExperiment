package gametheoryexperiment.geministrategies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

/**
 * "The Peacekeeper"
 * Designed to win by sustaining 100% cooperation with Friedman and TFT,
 * while aggressively shutting down "chaos" strategies.
 */
public class Peacekeeper extends Strategy {
    public Peacekeeper() {
        super("The Peacekeeper");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> opponentHistory) {
        int round = opponentHistory.size();

        // 1. ALWAYS cooperate first.
        // This is vital to keep Friedman friendly.
        if (round == 0) return true;

        // 2. Identify "Tat for Tit" (The Inverse)
        // If they defect when we cooperate, and cooperate when we defect,
        // they are "Tat for Tit". We must Defect to stop the cycle.
        if (round >= 2) {
            boolean theyDefectedLast = !opponentHistory.get(round - 1);
            if (theyDefectedLast) return false; // Immediate retaliation
        }

        // 3. Identify "Always Cooperate"
        // If they have cooperated for 40 rounds straight, they are likely AllC.
        // We defect only on the very last few rounds (if we knew the end)
        // OR we use a very rare defection. But to beat Friedman,
        // it's safer to just keep cooperating.

        // 4. Default to Tit For Tat
        // This is the safest way to stay at 500+ points with the top tier.
        return opponentHistory.get(round - 1);
    }
}