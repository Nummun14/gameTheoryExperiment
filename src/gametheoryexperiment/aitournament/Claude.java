package gametheoryexperiment.aitournament;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

/**
 * Adaptive Tit-for-Tat with Forgiveness
 *
 * This strategy uses a sophisticated approach:
 * 1. Starts with cooperation to establish mutual benefit
 * 2. Mirrors opponent's last move (classic Tit-for-Tat)
 * 3. Forgives occasional defections to escape deadlocks
 * 4. Detects exploitation patterns and responds firmly
 * 5. Attempts to re-establish cooperation after punishment
 */
public class Claude extends Strategy {
    private int consecutiveDefections = 0;
    private int forgivenessCounter = 0;
    private static final int FORGIVENESS_THRESHOLD = 10;
    private static final double EXPLOITATION_RATIO = 0.7;

    public Claude() {
        super("Claude");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        // First move: always cooperate
        if (opponentHistory.isEmpty()) {
            return true;
        }

        // Get opponent's last move
        boolean opponentLastMove = opponentHistory.get(opponentHistory.size() - 1);

        // Calculate opponent's cooperation rate
        double opponentCoopRate = calculateCooperationRate(opponentHistory);

        // If opponent is heavily exploitative (cooperates less than 30%), be cautious
        if (opponentHistory.size() > 20 && opponentCoopRate < 0.3) {
            // Only cooperate if they just cooperated AND we haven't been exploited recently
            return opponentLastMove && consecutiveDefections == 0;
        }

        // Track consecutive defections
        if (!opponentLastMove) {
            consecutiveDefections++;
        } else {
            consecutiveDefections = 0;
            forgivenessCounter = 0; // Reset forgiveness when opponent cooperates
        }

        // If opponent defected last round
        if (!opponentLastMove) {
            // Forgiveness mechanism: occasionally cooperate after a defection
            // This helps escape mutual defection cycles
            if (consecutiveDefections >= 3 && consecutiveDefections <= 5) {
                forgivenessCounter++;
                if (forgivenessCounter >= FORGIVENESS_THRESHOLD) {
                    forgivenessCounter = 0;
                    return true; // Olive branch
                }
            }

            // If they're defecting constantly (5+ times), defect back firmly
            if (consecutiveDefections >= 5) {
                return false;
            }

            // Otherwise, standard Tit-for-Tat: mirror their defection
            return false;
        }

        // If opponent cooperated, cooperate back
        return true;
    }

    @Override
    public void reset() {
        consecutiveDefections = 0;
        forgivenessCounter = 0;
    }

    private double calculateCooperationRate(ArrayList<Boolean> history) {
        if (history.isEmpty()) return 1.0;

        int cooperations = 0;
        for (boolean move : history) {
            if (move) cooperations++;
        }
        return (double) cooperations / history.size();
    }
}