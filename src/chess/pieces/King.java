package chess.pieces;

import chess.board.Board;
import chess.board.Position;

/**
 * Chess king piece. Moves one square in any direction.
 */
public class King extends Piece {
    public King(String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Board board, Position destination) {
        if (!isWithinBounds(destination) || isSamePosition(destination) || isOwnPiece(board, destination)) {
            return false;
        }

        int rowDiff = Math.abs(destination.getRow() - getPosition().getRow());
        int colDiff = Math.abs(destination.getCol() - getPosition().getCol());

        return rowDiff <= 1 && colDiff <= 1;
    }

    @Override
    public String getSymbol() {
        return getColor().equals("White") ? "wK" : "bK";
    }
}
