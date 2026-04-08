package chess.board;

import java.util.Objects;

public class Position {

    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

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
