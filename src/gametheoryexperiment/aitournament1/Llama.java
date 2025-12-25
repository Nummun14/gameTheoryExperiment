package gametheoryexperiment.aitournament1;

import gametheoryexperiment.Strategy;

import java.util.ArrayList;
import java.util.Random;

public class Llama extends Strategy {
    private boolean lastOpponentMove = true; // Assume opponent cooperates initially
    private int defectStreak = 0;
    private Random random = new Random();

    public Llama() {
        super("Llama");
    }

    @Override
    public boolean shouldCooperate(ArrayList<Boolean> history, ArrayList<Boolean> opponentHistory) {
        if (opponentHistory.isEmpty()) {
            // Cooperate on the first move
            return true;
        }

        lastOpponentMove = opponentHistory.get(opponentHistory.size() - 1);

        if (!lastOpponentMove) {
            defectStreak++;
        } else {
            defectStreak = 0;
        }

        // Forgive with a certain probability if the opponent has defected for a while
        if (defectStreak > 3 && random.nextDouble() < 0.2) {
            return true;
        }

        // Otherwise, follow Tit-for-Tat
        return lastOpponentMove;
    }

    @Override
    public void reset() {
        lastOpponentMove = true;
        defectStreak = 0;
    }
}
