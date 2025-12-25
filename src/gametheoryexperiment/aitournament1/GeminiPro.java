package gametheoryexperiment.aitournament1;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class GeminiPro extends Strategy {

    // Helper to track if we just tried to break a loop
    private boolean attemptedPeace = false;

    public GeminiPro() {
        super("Gemini Pro");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        int rounds = history.size();

        // 1. OPENING: Always Cooperate.
        // We want to establish trust immediately to reach the (3,3) payout.
        if (rounds == 0) {
            return true;
        }

        boolean myLastMove = history.get(rounds - 1);
        boolean opponentLastMove = opponentHistory.get(rounds - 1);

        // 2. ANALYSIS: Check for "Death Spirals"
        // A death spiral is when both bots punish each other indefinitely (Defect/Defect).
        // If we have both defected for the last 3 turns, we are likely stuck.
        if (rounds >= 3) {
            boolean deadlockDetected = true;
            for (int i = 0; i < 3; i++) {
                // If anyone cooperated in the last 3 turns, it's not a pure deadlock
                if (history.get(rounds - 1 - i) || opponentHistory.get(rounds - 1 - i)) {
                    deadlockDetected = false;
                    break;
                }
            }

            if (deadlockDetected) {
                // If we already tried peace last time and they defected again,
                // give up and protect ourselves (Defect).
                if (attemptedPeace) {
                    attemptedPeace = false;
                    return false;
                }

                // Otherwise, offer an olive branch to reset the game to Cooperation.
                attemptedPeace = true;
                return true;
            }
        }

        // Reset peace flag if the loop is broken or normal play resumes
        if (opponentLastMove) {
            attemptedPeace = false;
        }

        // 3. CORE LOGIC: Tit-for-Tat
        // Copy the opponent's last move.
        // If they were nice, we are nice. If they were mean, we retaliate.
        return opponentLastMove;
    }

    @Override
    public void reset() {
        // Clear our internal state for the next match
        attemptedPeace = false;
    }
}