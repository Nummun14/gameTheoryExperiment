# 🤝 Prisoner's Dilemma Strategy Experiment

Welcome to the **Prisoner's Dilemma Strategy Experiment**! This is a programming competition and simulation experiment where each participant writes a Java class representing a strategy for the Iterated Prisoner’s Dilemma. Your mission: **build the smartest strategy and see how it stacks up against others**.

---

## 🧠 How the Game Works

Each strategy plays a series of **1-on-1 matches** against:
- Every other submitted strategy
- Itself

Each match consists of **a random number of rounds (between 175 and 225)**. In each round, both strategies simultaneously decide whether to **Cooperate** or **Defect**, based only on the **opponent's move history so far**.

### ✅ Payoff Matrix (per round)

| Player A     | Player B     | A's Points | B's Points |
|--------------|--------------|------------|------------|
| Cooperates   | Cooperates   | 3          | 3          |
| Cooperates   | Defects      | 0          | 5          |
| Defects      | Cooperates   | 5          | 0          |
| Defects      | Defects      | 1          | 1          |

After all games are complete, the **average points per game** is calculated for each strategy.

---

## 📦 Project Structure

- `Strategy.java`: The abstract base class that all strategies must extend.
- `examplestratigies`: Contains example strategies to get inspiration from.
- Other folders may contain the logic to run all strategy matchups and record results.

---

## 🛠 How to Create Your Strategy

### 1. Clone the Repository
### 2. Create a new branch
### 3. Implement your strategy by creating a new Java class in the `season1` package that extends `Strategy.java` and implement the `shouldCooperate()` method.
### 4. Push your changes to your branch and create a pull request.


