package chess.pieces;

import chess.board.Board;
import chess.board.Position;

/**
 * Chess rook piece. Moves horizontally or vertically when its path is clear.
 */
public class Rook extends Piece {
    public Rook(String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Board board, Position destination) {
        if (!isWithinBounds(destination) || isSamePosition(destination) || isOwnPiece(board, destination)) {
            return false;
        }

        int currentRow = getPosition().getRow();
        int currentCol = getPosition().getCol();
        int destRow = destination.getRow();
        int destCol = destination.getCol();

        boolean straightMove = currentRow == destRow || currentCol == destCol;

        return straightMove && isPathClear(board, destination);
    }

    @Override
    public String getSymbol() {
        return getColor().equals("White") ? "wR" : "bR";
    }
}
