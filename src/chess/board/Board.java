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
            grid[1][column] = new Pawn();
            grid[6][column] = new Pawn();
        }

        grid[0][0] = new Rook();
        grid[0][1] = new Knight();
        grid[0][2] = new Bishop();
        grid[0][3] = new Queen();
        grid[0][4] = new King();
        grid[0][5] = new Bishop();
        grid[0][6] = new Knight();
        grid[0][7] = new Rook();

        grid[7][0] = new Rook();
        grid[7][1] = new Knight();
        grid[7][2] = new Bishop();
        grid[7][3] = new Queen();
        grid[7][4] = new King();
        grid[7][5] = new Bishop();
        grid[7][6] = new Knight();
        grid[7][7] = new Rook();
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
