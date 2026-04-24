package chess.board;

import java.util.Objects;

/**
 * Immutable row/column coordinate for the chess board.
 * Row and column are zero-based, with A8 represented as row 0, column 0.
 */
public class Position {

    private final int row;
    private final int column;

    /**
     * Creates a board position.
     *
     * @param row zero-based row
     * @param column zero-based column
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the zero-based row.
     *
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the zero-based column.
     *
     * @return column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the zero-based column.
     *
     * @return column
     */
    public int getCol() {
        return column;
    }

    /**
     * Converts algebraic notation such as E2 to a board position.
     *
     * @param notation chess square notation
     * @return converted position
     */
    public static Position fromNotation(String notation) {
        if (notation == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }

        String trimmed = notation.trim().toUpperCase();
        if (!trimmed.matches("^[A-H][1-8]$")) {
            throw new IllegalArgumentException("Invalid chess notation: " + notation);
        }

        int column = trimmed.charAt(0) - 'A';
        int rank = trimmed.charAt(1) - '0';
        int row = 8 - rank;

        return new Position(row, column);
    }

    /**
     * Converts this position to algebraic notation such as E2.
     *
     * @return chess square notation
     */
    public String toNotation() {
        char file = (char) ('A' + column);
        int rank = 8 - row;
        return String.valueOf(file) + rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        Position other = (Position) obj;
        return row == other.row && column == other.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return toNotation();
    }
}
