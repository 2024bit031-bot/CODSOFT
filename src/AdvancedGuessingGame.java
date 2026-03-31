import java.util.*;
import java.io.*;

public class AdvancedGuessingGame {
    private int min = 1;
    private int max = 100;
    private int maxAttempts;
    private int score = 0;
    private int roundsPlayed = 0;
    private int bestScore = Integer.MAX_VALUE;

    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);

    private final String FILE_NAME = "highscore.txt";

    public static void main(String[] args) {
        AdvancedGuessingGame game = new AdvancedGuessingGame();
        game.loadHighScore();
        game.start();
    }

    public void start() {
        System.out.println(" Welcome to Advanced Number Guessing Game!");

        chooseDifficulty();

        while (true) {
            playRound();
            roundsPlayed++;

            System.out.println("\n Score: " + score);
            System.out.println(" Best Score: " + bestScore);

            System.out.print("\nPlay again? (y/n): ");
            if (!scanner.next().equalsIgnoreCase("y")) {
                saveHighScore();
                System.out.println(" Thanks for playing!");
                break;
            }
        }
    }

    private void chooseDifficulty() {
        System.out.println("\nSelect Difficulty:");
        System.out.println("1. Easy (10 attempts)");
        System.out.println("2. Medium (7 attempts)");
        System.out.println("3. Hard (5 attempts)");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> maxAttempts = 10;
            case 2 -> maxAttempts = 7;
            case 3 -> maxAttempts = 5;
            default -> {
                System.out.println("Invalid choice, defaulting to Medium.");
                maxAttempts = 7;
            }
        }
    }

    private void playRound() {
        int number = random.nextInt(max - min + 1) + min;
        int attempts = 0;

        System.out.println("\n Guess the number between " + min + " and " + max);

        while (attempts < maxAttempts) {
            System.out.print("Enter guess: ");

            if (!scanner.hasNextInt()) {
                System.out.println(" Enter a valid number!");
                scanner.next();
                continue;
            }

            int guess = scanner.nextInt();
            attempts++;

            if (guess == number) {
                System.out.println(" Correct!");
                int roundScore = (maxAttempts - attempts + 1);
                score += roundScore;

                if (roundScore < bestScore) {
                    bestScore = roundScore;
                }

                return;
            } else if (guess < number) {
                System.out.println(" Too low!");
            } else {
                System.out.println(" Too high!");
            }

            System.out.println("Attempts left: " + (maxAttempts - attempts));
        }

        System.out.println(" You lost! The number was: " + number);
    }

    //  Save high score to file
    private void saveHighScore() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(String.valueOf(bestScore));
        } catch (IOException e) {
            System.out.println("Error saving high score.");
        }
    }

    //  Load high score from file
    private void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            bestScore = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            bestScore = Integer.MAX_VALUE;
        }
    }
}
