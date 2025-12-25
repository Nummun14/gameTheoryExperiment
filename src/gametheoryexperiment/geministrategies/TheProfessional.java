package gametheoryexperiment.geministrategies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

/**
 * "The Professional"
 * A strategy designed to cooperate with the fair, punish the cruel,
 * and exploit the predictable.
 */
public class TheProfessional extends Strategy {
    public TheProfessional() {
        super("The Professional");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        int round = opponentHistory.size();

        // 1. Start Strong: Cooperate on the first move to build trust.
        if (round == 0) {
            return true;
        }

        // 2. Detect "Always Cooperate": If they've cooperated for 10 rounds straight,
        // we can occasionally defect to gain extra points (every 4th round).
        if (round > 10 && !opponentHistory.contains(false)) {
            return round % 4 != 0;
        }

        // 3. Detect "Tat for Tit" / "Random": If the opponent is wildly inconsistent,
        // it's better to just Defect and protect ourselves.
        if (round > 15) {
            int defections = 0;
            for (boolean move : opponentHistory) {
                if (!move) defections++;
            }
            // If they defect more than 60% of the time, stop trying to be nice.
            if ((double) defections / round > 0.6) {
                return false;
            }
        }

        // 4. Default to Tit For Tat logic:
        // This keeps us in sync with Friedman and Tit For Tat.
        return opponentHistory.get(round - 1);
    }
}