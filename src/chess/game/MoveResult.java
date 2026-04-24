package chess.game;

/**
 * Immutable result returned after a move attempt.
 * It reports whether the move succeeded and any game state changes such as
 * check, checkmate, winner, and current turn.
 */
public class MoveResult {

    private final boolean success;
    private final String message;
    private final String currentTurn;
    private final boolean check;
    private final boolean checkmate;
    private final String winner;

    /**
     * Creates a move result.
     *
     * @param success whether the move was applied
     * @param message user-facing status message
     * @param currentTurn current side to move, or winning side after checkmate
     * @param check whether the opponent is in check
     * @param checkmate whether the move caused checkmate
     * @param winner winning color when checkmate occurs
     */
    public MoveResult(boolean success, String message, String currentTurn,
            boolean check, boolean checkmate, String winner) {
        this.success = success;
        this.message = message;
        this.currentTurn = currentTurn;
        this.check = check;
        this.checkmate = checkmate;
        this.winner = winner;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public boolean isCheck() {
        return check;
    }

    public boolean isCheckmate() {
        return checkmate;
    }

    public String getWinner() {
        return winner;
    }
}
