package gametheoryexperiment.geministrategies;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;

/**
 * "The Vigilante"
 * A refined Grim Trigger that won't be bullied by 'Testers'
 * but won't trigger the 'Friedman' death spiral accidentally.
 */
public class Vigilante extends Strategy {
    private boolean isGrim = false;
    private boolean inProbation = false;

    public Vigilante() {
        super("The Vigilante");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        int round = opponentHistory.size();

        if (round == 0) return true;
        if (isGrim) return false;

        boolean lastOpponentMove = opponentHistory.get(round - 1);

        // 1. Handle the "Tester" / Round 1 Defection
        if (round == 1 && !lastOpponentMove) {
            inProbation = true;
            return false; // Retaliate once
        }

        // 2. Check if they "paid their debt" after a Round 1 test
        if (inProbation) {
            inProbation = false;
            if (!lastOpponentMove) {
                isGrim = true; // They defected twice in a row at start? Grim.
                return false;
            }
            return true; // They cooperated after our retaliation, trust is restored.
        }

        // 3. Standard Grim Trigger for the rest of the game
        if (!lastOpponentMove) {
            isGrim = true;
            return false;
        }

        return true;
    }

    @Override
    public void reset() {
        isGrim = false;
        inProbation = false;
    }
}