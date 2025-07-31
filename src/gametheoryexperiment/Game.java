package gametheoryexperiment;

import java.util.ArrayList;

public class Game {
    private final Strategy[] strategies;

    public Game(Strategy[] strategies) {
        this.strategies = strategies;
    }

    public void play() {
        for (int i = 0; i < strategies.length; i++) {
            for (int j = i; j < strategies.length; j++)
                playMatch(strategies[i], strategies[j]);
        }
    }

    private void playMatch(Strategy strategy1, Strategy strategy2) {
        ArrayList<Boolean> history1 = new ArrayList<>();
        ArrayList<Boolean> history2 = new ArrayList<>();
        int score1 = 0, score2 = 0;
        int rounds = (int) (Math.random() * 50) + 175;

        for (int i = 0; i < rounds; i++) {
            boolean coop1 = strategy1.shouldCooperate(history2);
            boolean coop2 = strategy2.shouldCooperate(history1);
            history1.add(coop1);
            history2.add(coop2);
            int[] scores = getScores(coop1, coop2);
            score1 += scores[0];
            score2 += scores[1];
        }
        System.out.println(strategy1.getName() + " vs " + strategy2.getName() + ": " + score1 + " - " + score2);

        if (strategy1 == strategy2) {
            strategy1.updateScore(score1, rounds);
            return;
        }
        strategy1.updateScore(score1, rounds);
        strategy2.updateScore(score2, rounds);
    }

    private int[] getScores(boolean coop1, boolean coop2) {
        if (coop1 && coop2)
            return new int[]{3, 3};
        if (coop1)
            return new int[]{0, 5};
        if (coop2)
            return new int[]{5, 0};
        return new int[]{1, 1};
    }
}