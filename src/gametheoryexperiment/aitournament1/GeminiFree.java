package gametheoryexperiment.aitournament1;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

/**
 * Adaptive Generous Tit-for-Tat
 * * Logic:
 * 1. Cooperates on the first move.
 * 2. Usually plays Tit-for-Tat (copies opponent's last move).
 * 3. Adds a 10% chance to forgive a defection to break cycles.
 * 4. Becomes strictly Tit-for-Tat if the opponent is consistently hostile.
 */
public class GeminiFree extends Strategy {

    public GeminiFree() {
        super("Gemini-free");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        // Round 1: Always cooperate to build trust
        if (opponentHistory.isEmpty()) {
            return true;
        }

        boolean lastOpponentMove = opponentHistory.get(opponentHistory.size() - 1);

        // If opponent cooperated last, we always cooperate
        if (lastOpponentMove) {
            return true;
        }

        // If opponent defected, we usually defect (Tit-for-Tat)
        // But we add "Generosity" to break defection loops

        // First, check if the opponent is being exploitative
        double opponentDefectionRate = calculateDefectionRate(opponentHistory);

        // If they defect > 40% of the time, don't be generous. They are likely a "Always Defect" bot.
        if (opponentDefectionRate > 0.40) {
            return false;
        }

        // Otherwise, 10% chance to forgive and cooperate anyway
        return Math.random() < 0.10;
    }

    private double calculateDefectionRate(ArrayList<Boolean> history) {
        if (history.isEmpty()) return 0;
        long defections = history.stream().filter(move -> !move).count();
        return (double) defections / history.size();
    }

    @Override
    public void reset() {
        // This strategy is stateless between games,
        // as it analyzes history provided by the Game class.
    }
}