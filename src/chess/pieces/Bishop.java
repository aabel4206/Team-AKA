package chess.pieces;

import chess.board.Board;
import chess.board.Position;

/**
 * Chess bishop piece. Moves diagonally when its path is clear.
 */
public class Bishop extends Piece {
    public Bishop(String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Board board, Position destination) {
        if (!isWithinBounds(destination) || isSamePosition(destination) || isOwnPiece(board, destination)) {
            return false;
        }

        int rowDiff = Math.abs(destination.getRow() - getPosition().getRow());
        int colDiff = Math.abs(destination.getCol() - getPosition().getCol());

        return rowDiff == colDiff && isPathClear(board, destination);
    }

    @Override
    public String getSymbol() {
        return getColor().equals("White") ? "wB" : "bB";
    }
}
