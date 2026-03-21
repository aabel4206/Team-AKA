package chess.game;

import java.util.Scanner;
import chess.utils.InputParser;

public class Game {

    private String currentTurn;

    public Game() {
        currentTurn = "White";
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Chess Game Started");
        System.out.println("Type moves in the format: E2 E4");
        System.out.println("Type 'exit' to quit.\n");

        while (true) {
            System.out.println(currentTurn + "'s turn");
            System.out.print("Enter move: ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Game ended.");
                break;
            }

            if (!InputParser.isValidFormat(input)) {
                System.out.println("Invalid format. Use E2 E4.\n");
                continue;
            }

            String[] moveParts = InputParser.parseMove(input);
            String from = moveParts[0];
            String to = moveParts[1];

            System.out.println("Move accepted: " + from + " -> " + to);

            switchTurn();
            System.out.println();
        }

        scanner.close();
    }

    private void switchTurn() {
        currentTurn = currentTurn.equals("White") ? "Black" : "White";
    }
}