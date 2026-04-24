package chess.game;

import chess.board.Board;
import chess.board.Position;

/**
 * Coordinates game flow between the Swing GUI and the backend board.
 * The controller owns turn state and delegates all chess rule validation to
 * the board.
 */
public class GameController {

    private Board board;
    private String currentTurn;
    private boolean gameOver;
    private String winner;

    /**
     * Creates a new game controller with a fresh board.
     */
    public GameController() {
        resetGame();
    }

    /**
     * Attempts a move for the current side.
     *
     * @param from source square
     * @param to destination square
     * @return complete move result for the GUI
     */
    public MoveResult attemptMove(Position from, Position to) {
        if (gameOver) {
            return new MoveResult(false, "The game is over. Start a new game to continue.",
                    currentTurn, false, true, winner);
        }

        if (!board.isWithinBounds(from) || !board.isWithinBounds(to)) {
            return new MoveResult(false, "Move is outside the board.", currentTurn, false, false, null);
        }

        if (board.getPiece(from) == null) {
            return new MoveResult(false, "Select a square that contains a piece.", currentTurn, false, false, null);
        }

        if (!currentTurn.equals(board.getPiece(from).getColor())) {
            return new MoveResult(false, "It is " + currentTurn + "'s turn.", currentTurn, false, false, null);
        }

        if (!board.movePiece(from, to, currentTurn)) {
            return new MoveResult(false, "Illegal move. Try a legal chess move.", currentTurn, false, false, null);
        }

        String movingColor = currentTurn;
        String opponentColor = oppositeColor(currentTurn);
        boolean check = board.isInCheck(opponentColor);
        boolean checkmate = board.isCheckmate(opponentColor);

        if (checkmate) {
            gameOver = true;
            winner = movingColor;
            return new MoveResult(true, movingColor + " wins by checkmate.",
                    movingColor, true, true, winner);
        }

        currentTurn = opponentColor;
        String message = check
                ? opponentColor + " is in check. " + currentTurn + "'s turn."
                : currentTurn + "'s turn.";
        return new MoveResult(true, message, currentTurn, check, false, null);
    }

    /**
     * Resets the game to the starting position with White to move.
     */
    public void resetGame() {
        board = new Board();
        currentTurn = "White";
        gameOver = false;
        winner = null;
    }

    /**
     * Loads a saved board state and turn.
     *
     * @param state saved 8x8 piece symbols
     * @param currentTurn side to move
     */
    public void loadGame(String[][] state, String currentTurn) {
        board = new Board();
        board.loadFromSymbols(state);
        this.currentTurn = isValidColor(currentTurn) ? currentTurn : "White";
        gameOver = false;
        winner = null;
    }

    /**
     * Returns the authoritative board.
     *
     * @return board state
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the color whose turn it is.
     *
     * @return current turn
     */
    public String getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Returns whether the game has ended.
     *
     * @return true after checkmate
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns the winner after checkmate.
     *
     * @return winning color, or null while the game is active
     */
    public String getWinner() {
        return winner;
    }

    private boolean isValidColor(String color) {
        return "White".equals(color) || "Black".equals(color);
    }

    private String oppositeColor(String color) {
        return "White".equals(color) ? "Black" : "White";
    }
}
