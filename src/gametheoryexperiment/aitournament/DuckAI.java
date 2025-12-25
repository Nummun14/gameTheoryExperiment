package gametheoryexperiment.aitournament;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class DuckAI extends Strategy {
    private static final int FORGIVENESS_THRESHOLD = 1; // Number of defections before returning to cooperation
    private int consecutiveDefections = 0;

    public DuckAI() {
        super("DuckAI");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        // Start by cooperating
        if (history.isEmpty()) {
            return true;
        }

        // Check the opponent's last move
        boolean opponentLastMove = opponentHistory.get(opponentHistory.size() - 1);

        if (opponentLastMove) {
            // Last move was cooperation; cooperate
            consecutiveDefections = 0; // Reset defection count
            return true;
        } else {
            // Last move was defection
            consecutiveDefections++;
            // If the opponent has defected too many times, keep defecting
            if (consecutiveDefections > FORGIVENESS_THRESHOLD) {
                return false; // Defect in retaliation
            }
            // Under limited defection, forgive and cooperate
            return true; // Return to cooperation
        }
    }

    @Override
    public void reset() {
        consecutiveDefections = 0; // Reset after each game
    }
}
