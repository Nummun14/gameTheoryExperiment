package gametheoryexperiment.aitournament;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class Deepseek extends Strategy {
    private int consecutiveDefections = 0;
    private double cooperationRate = 0.0;
    private int roundCount = 0;
    private final double forgivenessThreshold;
    private final int maxConsecutiveDefections;

    public Deepseek() {
        super("Deepseek");
        this.forgivenessThreshold = 0.7; // Forgive if cooperation rate > 70%
        this.maxConsecutiveDefections = 3; // Maximum defections before retaliating
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        roundCount++;

        // First move: cooperate to establish trust
        if (history.isEmpty()) {
            return true;
        }

        // Calculate opponent's cooperation rate
        double opponentCooperation = calculateCooperationRate(opponentHistory);

        // Update our own cooperation rate
        cooperationRate = (cooperationRate * (roundCount - 1) + (history.get(history.size() - 1) ? 1 : 0)) / roundCount;

        // If opponent has high cooperation rate, be forgiving
        if (opponentCooperation > forgivenessThreshold && consecutiveDefections < maxConsecutiveDefections) {
            consecutiveDefections = 0;
            return true;
        }

        // Standard Tit-for-Tat: mirror opponent's last move
        boolean opponentLastMove = opponentHistory.get(opponentHistory.size() - 1);

        if (!opponentLastMove) {
            consecutiveDefections++;
        } else {
            consecutiveDefections = Math.max(0, consecutiveDefections - 1);
        }

        // Occasionally cooperate randomly to break cycles of mutual defection (5% chance)
        if (consecutiveDefections > 0 && Math.random() < 0.05) {
            consecutiveDefections--;
            return true;
        }

        return opponentLastMove;
    }

    private double calculateCooperationRate(ArrayList<Boolean> moves) {
        if (moves.isEmpty()) return 1.0;

        int cooperations = 0;
        for (boolean move : moves) {
            if (move) cooperations++;
        }
        return (double) cooperations / moves.size();
    }

    @Override
    public void reset() {
        consecutiveDefections = 0;
        cooperationRate = 0.0;
        roundCount = 0;
    }
}
