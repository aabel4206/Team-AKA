package chess.pieces;

import chess.board.Board;
import chess.board.Position;

public abstract class Piece {
    private final String color;
    private Position position;

    public Piece(String color, Position position) {
        this.color = color;
        this.position = position;
    }

    public String getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void move(Position newPosition) {
        this.position = newPosition;
    }

    public abstract boolean isValidMove(Board board, Position destination);

    public abstract String getSymbol();

    protected boolean isSamePosition(Position destination) {
        return position.getRow() == destination.getRow()
                && position.getCol() == destination.getCol();
    }

    protected boolean isWithinBounds(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    protected Piece getPieceAt(Board board, Position position) {
        return board.getPiece(position);
    }

    protected boolean isDestinationEmpty(Board board, Position destination) {
        return getPieceAt(board, destination) == null;
    }

    protected boolean isOpponentPiece(Board board, Position destination) {
        Piece piece = getPieceAt(board, destination);
        return piece != null && !piece.getColor().equals(this.color);
    }

    protected boolean isOwnPiece(Board board, Position destination) {
        Piece piece = getPieceAt(board, destination);
        return piece != null && piece.getColor().equals(this.color);
    }

    protected boolean isPathClear(Board board, Position destination) {
        int currentRow = position.getRow();
        int currentCol = position.getCol();
        int destRow = destination.getRow();
        int destCol = destination.getCol();

        int rowStep = Integer.compare(destRow, currentRow);
        int colStep = Integer.compare(destCol, currentCol);

        int row = currentRow + rowStep;
        int col = currentCol + colStep;

        while (row != destRow || col != destCol) {
            if (board.getPiece(new Position(row, col)) != null) {
                return false;
            }
            row += rowStep;
            col += colStep;
        }

        return true;
    }
}