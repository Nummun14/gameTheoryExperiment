package gametheoryexperiment;

import java.util.ArrayList;

/**
 * Abstract class representing a strategy in the prisoner dilemma.
 * Each strategy must implement the {@link Strategy#shouldCooperate(ArrayList)} with their game strategy.
 */
public abstract class Strategy {
    private double totalPointsPerRound = 0;
    private int gamesPlayed = 0;
    private final String name;

    public Strategy(String name) {
        this.name = name;
    }

    /**
     * Determines whether the strategy should cooperate or defect.
     * If the opponent history is used, then it is up to the strategy to decide how to use it. Bear in mind that it'll be empty on the first round.
     *
     * @param opponentHistory the history of the opponent's moves, where true means cooperate and false means defect.
     * @return true if the strategy should cooperate, false if it should defect.
     */
    public abstract boolean shouldCooperate(ArrayList<Boolean> opponentHistory);

    public void updateScore(int points, int roundsPlayed) {
        this.totalPointsPerRound += (double) points / roundsPlayed;
        gamesPlayed++;
    }

    public double getPointsPerGame() {
        return gamesPlayed == 0 ? 0 : (totalPointsPerRound / gamesPlayed) * 200;
    }

    public double getGamesPlayed() {
        return gamesPlayed;
    }

    public String getName() {
        return name;
    }
}