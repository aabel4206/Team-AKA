package chess.pieces;

import chess.board.Board;
import chess.board.Position;

public class Pawn extends Piece {
    /**
     * Creates a pawn with a color and starting position.
     *
     * @param color piece color
     * @param position current board position
     */
    public Pawn(String color, Position position) {
        super(color, position);
    }

    /**
     * Validates pawn movement, including initial two-square advances and
     * diagonal captures.
     *
     * @param board current board state
     * @param destination requested destination
     * @return true when the pawn move is legal
     */
    @Override
    public boolean isValidMove(Board board, Position destination) {
        if (!isWithinBounds(destination) || isSamePosition(destination) || isOwnPiece(board, destination)) {
            return false;
        }

        int currentRow = getPosition().getRow();
        int currentCol = getPosition().getCol();
        int destRow = destination.getRow();
        int destCol = destination.getCol();

        int rowDiff = destRow - currentRow;
        int colDiff = destCol - currentCol;

        int direction = getColor().equals("White") ? -1 : 1;

        boolean startingRow;
        if (getColor().equals("White")) {
            startingRow = currentRow == 6;
        } else {
            startingRow = currentRow == 1;
        }

        if (colDiff == 0) {
            if (rowDiff == direction && isDestinationEmpty(board, destination)) {
                return true;
            }

            if (rowDiff == 2 * direction && startingRow && isDestinationEmpty(board, destination)) {
                Position intermediate = new Position(currentRow + direction, currentCol);
                return isDestinationEmpty(board, intermediate);
            }
        }

        if (Math.abs(colDiff) == 1 && rowDiff == direction) {
            return isOpponentPiece(board, destination);
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return getColor().equals("White") ? "wp" : "bp";
    }
}
