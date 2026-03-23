package chess.pieces;

import chess.board.Board;
import chess.board.Position;

public class Pawn extends Piece {
    public Pawn(String color, Position position) {
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

        int rowDiff = destRow - currentRow;
        int colDiff = destCol - currentCol;

        int direction = getColor().equals("White") ? 1 : -1;

        boolean startingRow;
        if (getColor().equals("White")) {
            startingRow = currentRow == 1 || currentRow == 2;
        } else {
            startingRow = currentRow == 6 || currentRow == 7;
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