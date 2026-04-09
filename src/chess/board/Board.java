package chess.board;

import java.lang.reflect.Method;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class Board {

    private Piece[][] grid;

    public Board() {
        grid = new Piece[8][8];
        initializeBoard();
    }

    public void initializeBoard() {
        grid = new Piece[8][8];

        for (int column = 0; column < 8; column++) {
            grid[1][column] = new Pawn("Black", new Position(1, column));
            grid[6][column] = new Pawn("White", new Position(6, column));
        }

        grid[0][0] = new Rook("Black", new Position(0, 0));
        grid[0][1] = new Knight("Black", new Position(0, 1));
        grid[0][2] = new Bishop("Black", new Position(0, 2));
        grid[0][3] = new Queen("Black", new Position(0, 3));
        grid[0][4] = new King("Black", new Position(0, 4));
        grid[0][5] = new Bishop("Black", new Position(0, 5));
        grid[0][6] = new Knight("Black", new Position(0, 6));
        grid[0][7] = new Rook("Black", new Position(0, 7));

        grid[7][0] = new Rook("White", new Position(7, 0));
        grid[7][1] = new Knight("White", new Position(7, 1));
        grid[7][2] = new Bishop("White", new Position(7, 2));
        grid[7][3] = new Queen("White", new Position(7, 3));
        grid[7][4] = new King("White", new Position(7, 4));
        grid[7][5] = new Bishop("White", new Position(7, 5));
        grid[7][6] = new Knight("White", new Position(7, 6));
        grid[7][7] = new Rook("White", new Position(7, 7));
    }

    public void displayBoard() {
        System.out.println("   A  B  C  D  E  F  G  H");
        for (int row = 0; row < 8; row++) {
            int rank = 8 - row;
            System.out.print(rank + " ");
            for (int column = 0; column < 8; column++) {
                System.out.print(formatSquare(grid[row][column]) + " ");
            }
            System.out.println(rank);
        }
        System.out.println("   A  B  C  D  E  F  G  H");
    }

    public Piece getPiece(Position position) {
        if (!isWithinBounds(position)) {
            return null;
        }
        return grid[position.getRow()][position.getColumn()];
    }

    public void setPiece(Position position, Piece piece) {
        if (!isWithinBounds(position)) {
            return;
        }
        if (piece != null) {
            piece.setPosition(position);
        }
        grid[position.getRow()][position.getColumn()] = piece;
    }

    public boolean movePiece(Position from, Position to) {
        if (!isWithinBounds(from) || !isWithinBounds(to)) {
            return false;
        }

        Piece piece = getPiece(from);
        if (piece == null) {
            return false;
        }

        setPiece(to, piece);
        setPiece(from, null);
        return true;
    }

    public boolean isWithinBounds(Position position) {
        return position != null
                && position.getRow() >= 0
                && position.getRow() < 8
                && position.getColumn() >= 0
                && position.getColumn() < 8;
    }

    private String formatSquare(Piece piece) {
        if (piece == null) {
            return "##";
        }

        String symbol = resolveSymbol(piece);
        if (symbol == null || symbol.isBlank()) {
            return "??";
        }

        symbol = symbol.trim();
        if (symbol.length() == 1) {
            return symbol + " ";
        }
        return symbol.substring(0, Math.min(2, symbol.length()));
    }

    private String resolveSymbol(Piece piece) {
        try {
            Method method = piece.getClass().getMethod("getSymbol");
            Object value = method.invoke(piece);
            if (value instanceof String) {
                return (String) value;
            }
        } catch (Exception ignored) {
        }

        if (piece instanceof Knight) {
            return "N";
        }
        if (piece instanceof Bishop) {
            return "B";
        }
        if (piece instanceof Rook) {
            return "R";
        }
        if (piece instanceof Queen) {
            return "Q";
        }
        if (piece instanceof King) {
            return "K";
        }
        if (piece instanceof Pawn) {
            return "P";
        }
        return "?";
    }
}
