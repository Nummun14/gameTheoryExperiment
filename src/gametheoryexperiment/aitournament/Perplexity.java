package gametheoryexperiment.aitournament;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class Perplexity extends Strategy {
    private int consecutiveDefectsByOpponent = 0;
    private double oppCoopEMA = 1.0;
    private final double alpha = 0.3;
    private int punishmentRoundsLeft = 0;

    public Perplexity() {
        super("Perplexity");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        if (history.isEmpty()) return true; // first move

        if (punishmentRoundsLeft > 0) {
            punishmentRoundsLeft--;
            return false; // defect as punishment
        }

        boolean oppLast = opponentHistory.get(opponentHistory.size() - 1);
        oppCoopEMA = alpha * (oppLast ? 1.0 : 0.0) + (1 - alpha) * oppCoopEMA;
        if (!oppLast) consecutiveDefectsByOpponent++;
        else consecutiveDefectsByOpponent = 0;

        if (consecutiveDefectsByOpponent >= 3) {
            // cautious retaliation
            if (Math.random() < 0.5) {
                punishmentRoundsLeft = 1;
                return false;
            } else {
                return true;
            }
        }

        if (oppCoopEMA >= 0.7) return true;
        if (!oppLast) {
            // light retaliation
            punishmentRoundsLeft = 0;
            return false;
        }
        return true;
    }

}
