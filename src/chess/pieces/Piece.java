package chess.pieces;

import chess.board.Board;
import chess.board.Position;

/**
 * Base class for every chess piece.
 * Stores shared color/position state and exposes movement validation hooks for
 * concrete piece types.
 */
public abstract class Piece {
    private final String color;
    private Position position;

    /**
     * Creates a piece.
     *
     * @param color piece color
     * @param position current board position
     */
    public Piece(String color, Position position) {
        this.color = color;
        this.position = position;
    }

    /**
     * Returns the piece color.
     *
     * @return "White" or "Black"
     */
    public String getColor() {
        return color;
    }

    /**
     * Returns the current board position.
     *
     * @return current position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Updates the stored board position.
     *
     * @param position new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    public void move(Position newPosition) {
        this.position = newPosition;
    }

    /**
     * Validates whether this piece can move to a destination according to its
     * piece-specific movement pattern.
     *
     * @param board current board state
     * @param destination requested destination
     * @return true when the piece movement pattern allows the move
     */
    public abstract boolean isValidMove(Board board, Position destination);

    /**
     * Returns the two-character GUI/save symbol for this piece.
     *
     * @return piece symbol such as wK or bp
     */
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

        int rowDiff = Math.abs(destRow - currentRow);
        int colDiff = Math.abs(destCol - currentCol);
        boolean straightMove = currentRow == destRow || currentCol == destCol;
        boolean diagonalMove = rowDiff == colDiff;
        if (!straightMove && !diagonalMove) {
            return false;
        }

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
