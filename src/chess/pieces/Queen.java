package chess.pieces;

import chess.board.Board;
import chess.board.Position;

public class Queen extends Piece {
    public Queen(String color, Position position) {
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

        int rowDiff = Math.abs(destRow - currentRow);
        int colDiff = Math.abs(destCol - currentCol);

        boolean straightMove = currentRow == destRow || currentCol == destCol;
        boolean diagonalMove = rowDiff == colDiff;

        return (straightMove || diagonalMove) && isPathClear(board, destination);
    }

    @Override
    public String getSymbol() {
        return getColor().equals("White") ? "wQ" : "bQ";
    }
}