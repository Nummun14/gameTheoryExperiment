package gametheoryexperiment.aitournament;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;
import java.util.Random;

public class ChatGPT extends Strategy {
    private static final double FORGIVENESS_RATE = 0.1;
    private static final double PROBE_RATE = 0.05;
    private static final int MIN_ROUNDS_BEFORE_JUDGING = 10;

    private final Random random = new Random();

    public ChatGPT() {
        super("ChatGPT");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {

        // First move: cooperate
        if (history.isEmpty()) {
            return true;
        }

        int roundsPlayed = opponentHistory.size();

        // Calculate opponent cooperation rate
        int coopCount = 0;
        for (boolean move : opponentHistory) {
            if (move) coopCount++;
        }

        double coopRate = (double) coopCount / roundsPlayed;

        boolean opponentLastMove = opponentHistory.get(roundsPlayed - 1);

        // If opponent is mostly defecting, switch to punishment mode
        if (roundsPlayed >= MIN_ROUNDS_BEFORE_JUDGING && coopRate < 0.3) {
            // Occasionally probe cooperation to escape mutual defection
            return random.nextDouble() < PROBE_RATE;
        }

        // Tit-for-Tat with forgiveness
        if (!opponentLastMove) {
            return random.nextDouble() < FORGIVENESS_RATE;
        }

        // Otherwise cooperate
        return true;
    }

    @Override
    public void reset() {
        // No persistent state between matches
    }
}
