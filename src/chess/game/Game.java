package chess.game;

import java.util.Scanner;
import chess.board.Position;
import chess.utils.InputParser;

/**
 * Optional console game loop that uses the same backend controller as the GUI.
 */
public class Game {

    private final GameController controller;

    /**
     * Creates a console game.
     */
    public Game() {
        controller = new GameController();
    }

    /**
     * Starts a terminal move loop using notation such as E2 E4.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Chess Game Started");
        System.out.println("Type moves in the format: E2 E4");
        System.out.println("Type 'exit' to quit.\n");
        controller.getBoard().displayBoard();

        while (!controller.isGameOver()) {
            System.out.println(controller.getCurrentTurn() + "'s turn");
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
            MoveResult result = controller.attemptMove(
                    Position.fromNotation(moveParts[0]),
                    Position.fromNotation(moveParts[1]));

            System.out.println(result.getMessage());
            controller.getBoard().displayBoard();
            System.out.println();
        }

        scanner.close();
    }
}
