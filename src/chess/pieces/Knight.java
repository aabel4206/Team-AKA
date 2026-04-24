package chess.pieces;

import chess.board.Board;
import chess.board.Position;

/**
 * Chess knight piece. Moves in an L shape and may jump over pieces.
 */
public class Knight extends Piece {
    public Knight(String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Board board, Position destination) {
        if (!isWithinBounds(destination) || isSamePosition(destination) || isOwnPiece(board, destination)) {
            return false;
        }

        int rowDiff = Math.abs(destination.getRow() - getPosition().getRow());
        int colDiff = Math.abs(destination.getCol() - getPosition().getCol());

        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }

    @Override
    public String getSymbol() {
        return getColor().equals("White") ? "wN" : "bN";
    }
}
