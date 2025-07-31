package gametheoryexperiment;

public class Main {
    public static void main(String[] args) {
        Strategy[] strategies = {};
        Game game = new Game(strategies);
        game.play();
        sortStrategies(strategies);
        printResults(strategies);
    }

    private static void sortStrategies(Strategy[] strategies) {
        for (int i = 0; i < strategies.length; i++) {
            for (int j = i + 1; j < strategies.length; j++) {
                if (strategies[i].getPointsPerGame() < strategies[j].getPointsPerGame()) {
                    Strategy temp = strategies[i];
                    strategies[i] = strategies[j];
                    strategies[j] = temp;
                }
            }
        }
    }

    private static void printResults(Strategy[] strategies) {
        System.out.printf("%-20s | %-15s%n", "Strategy Name", "Points Per Game");
        System.out.println("---------------------+-----------------");

        for (Strategy strategy : strategies)
            System.out.printf("%-20s | %-15.2f%n", strategy.getName(), strategy.getPointsPerGame());
    }
}