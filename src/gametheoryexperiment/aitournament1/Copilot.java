package gametheoryexperiment.aitournament1;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

public class Copilot extends Strategy {

    // Handshake pattern: C, C, D, C  (true, true, false, true)
    private static final boolean[] HANDSHAKE_PATTERN = new boolean[]{true, true, false, true};
    private static final int HANDSHAKE_LENGTH = HANDSHAKE_PATTERN.length;

    // Internal state flags
    private boolean handshakeComplete = false;
    private boolean opponentMatchedHandshake = false;
    private boolean opponentClassified = false;
    private boolean opponentLikelyCooperative = false;

    // Grim trigger: once set because of clear exploitation, we become very uncooperative
    private boolean grimTriggered = false;

    // Counters for classification
    private int opponentDefectionsDuringHandshake = 0;
    private int opponentCooperationsDuringHandshake = 0;

    public Copilot() {
        super("Copilot");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        int round = history.size(); // 0-based index

        // 1. Handshake phase
        if (round < HANDSHAKE_LENGTH) {
            return handshakeMove(round, opponentHistory);
        }

        // After handshake, ensure we’ve classified the opponent
        if (!handshakeComplete) {
            finalizeHandshake(opponentHistory);
        }

        // 2. If grim triggered, mostly defect, occasionally test
        if (grimTriggered) {
            return grimModeDecision(history, opponentHistory);
        }

        // 3. If opponent seems cooperative, play generous tit-for-tat
        if (opponentLikelyCooperative) {
            return generousTitForTat(history, opponentHistory);
        }

        // 4. Otherwise, cautious tit-for-tat leaning towards defection
        return cautiousTitForTat(history, opponentHistory);
    }

    /**
     * Handshake behavior: follow a fixed pattern and observe opponent response.
     */
    private boolean handshakeMove(int round, ArrayList<Boolean> opponentHistory) {
        // Count opponent behavior during handshake
        if (opponentHistory.size() > 0) {
            boolean lastOppMove = opponentHistory.get(opponentHistory.size() - 1);
            if (lastOppMove) {
                opponentCooperationsDuringHandshake++;
            } else {
                opponentDefectionsDuringHandshake++;
            }
        }

        // Play handshake pattern
        return HANDSHAKE_PATTERN[round];
    }

    /**
     * After handshake, classify opponent based on handshake response.
     */
    private void finalizeHandshake(ArrayList<Boolean> opponentHistory) {
        handshakeComplete = true;

        // Check if opponent matched our handshake pattern exactly for those rounds
        if (opponentHistory.size() >= HANDSHAKE_LENGTH) {
            boolean matched = true;
            for (int i = 0; i < HANDSHAKE_LENGTH; i++) {
                if (opponentHistory.get(i) != HANDSHAKE_PATTERN[i]) {
                    matched = false;
                    break;
                }
            }
            opponentMatchedHandshake = matched;
        }

        // Classification: cooperative if they mostly cooperated during handshake and/or matched pattern
        opponentLikelyCooperative =
                opponentMatchedHandshake ||
                        opponentCooperationsDuringHandshake > opponentDefectionsDuringHandshake;

        opponentClassified = true;
    }

    /**
     * Grim mode: once we've decided the opponent is exploitative, defect heavily.
     * However, we occasionally cooperate to test if they changed.
     */
    private boolean grimModeDecision(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        int round = history.size();

        // Every 25 rounds, send a "test" cooperation to see if opponent changed
        if (round % 25 == 0) {
            return true;
        }

        // Otherwise, defect
        return false;
    }

    /**
     * Generous Tit-for-Tat:
     * - Start with cooperation
     * - Mostly copy opponent's last move
     * - If they defect rarely, we forgive and go back to cooperation
     */
    private boolean generousTitForTat(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        int round = history.size();

        // First move after handshake: cooperate
        if (round == HANDSHAKE_LENGTH) {
            return true;
        }

        // If opponent has no history (shouldn't happen after handshake), cooperate
        if (opponentHistory.isEmpty()) {
            return true;
        }

        // Look at opponent's last move
        boolean oppLast = opponentHistory.get(opponentHistory.size() - 1);

        // If they cooperated last move, we cooperate
        if (oppLast) {
            return true;
        }

        // If they defected last move, check recent behavior to decide whether to forgive
        int window = Math.min(10, opponentHistory.size());
        int coopCount = 0;
        for (int i = opponentHistory.size() - window; i < opponentHistory.size(); i++) {
            if (opponentHistory.get(i)) {
                coopCount++;
            }
        }

        // If they have been mostly cooperative in recent window, forgive and cooperate
        if (coopCount >= window * 0.6) {
            return true;
        }

        // Otherwise, retaliate by defecting
        return false;
    }

    /**
     * Cautious Tit-for-Tat:
     * - Lean more towards defection if opponent defects often
     * - May trigger grim mode if they exploit us repeatedly
     */
    private boolean cautiousTitForTat(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        int round = history.size();

        // First move after handshake: defect cautiously to test them
        if (round == HANDSHAKE_LENGTH) {
            return false;
        }

        if (opponentHistory.isEmpty()) {
            return false;
        }

        // Compute a longer-term view of opponent's behavior
        int window = Math.min(20, opponentHistory.size());
        int oppCoop = 0;
        for (int i = opponentHistory.size() - window; i < opponentHistory.size(); i++) {
            if (opponentHistory.get(i)) {
                oppCoop++;
            }
        }
        double coopRate = (double) oppCoop / window;

        // If opponent is very uncooperative, trigger grim
        if (coopRate < 0.2) {
            grimTriggered = true;
            return false;
        }

        // Otherwise: Copy last move, but bias slightly towards defection
        boolean oppLast = opponentHistory.get(opponentHistory.size() - 1);

        if (!oppLast) {
            // Retaliate defection
            return false;
        }

        // If they cooperated but coopRate is not high, occasionally defect to exploit
        if (coopRate < 0.7) {
            // Every 5th round, defect to test/exploit
            if (round % 5 == 0) {
                return false;
            }
        }

        // Default: cooperate
        return true;
    }

    @Override
    public void reset() {
        handshakeComplete = false;
        opponentMatchedHandshake = false;
        opponentClassified = false;
        opponentLikelyCooperative = false;
        grimTriggered = false;
        opponentDefectionsDuringHandshake = 0;
        opponentCooperationsDuringHandshake = 0;
    }
}