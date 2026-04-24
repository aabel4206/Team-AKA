package chess.board;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

/**
 * Represents the authoritative chess board state for the game.
 * The board owns the piece grid, validates legal moves, performs captures,
 * and detects check and checkmate.
 */
public class Board {

    private Piece[][] grid;

    /**
     * Creates a board in the standard starting chess position.
     */
    public Board() {
        grid = new Piece[8][8];
        initializeBoard();
    }

    /**
     * Creates a deep copy of another board.
     *
     * @param other board to copy
     */
    public Board(Board other) {
        grid = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = other.grid[row][col];
                if (piece != null) {
                    grid[row][col] = copyPiece(piece, new Position(row, col));
                }
            }
        }
    }

    /**
     * Resets the board to the standard starting chess position.
     */
    public final void initializeBoard() {
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

    /**
     * Prints the board to the terminal for console debugging.
     */
    public void displayBoard() {
        System.out.println("   A  B  C  D  E  F  G  H");
        for (int row = 0; row < 8; row++) {
            int rank = 8 - row;
            System.out.print(rank + " ");
            for (int column = 0; column < 8; column++) {
                Piece piece = grid[row][column];
                System.out.print((piece == null ? "##" : piece.getSymbol()) + " ");
            }
            System.out.println(rank);
        }
        System.out.println("   A  B  C  D  E  F  G  H");
    }

    /**
     * Returns the piece at a position, or null for an empty/out-of-bounds square.
     *
     * @param position square to inspect
     * @return piece at the square, or null
     */
    public Piece getPiece(Position position) {
        if (!isWithinBounds(position)) {
            return null;
        }
        return grid[position.getRow()][position.getCol()];
    }

    /**
     * Places a piece at a board position and updates the piece position.
     *
     * @param position destination square
     * @param piece piece to place, or null to clear the square
     */
    public void setPiece(Position position, Piece piece) {
        if (!isWithinBounds(position)) {
            return;
        }
        if (piece != null) {
            piece.setPosition(position);
        }
        grid[position.getRow()][position.getCol()] = piece;
    }

    /**
     * Attempts to move a piece for the current player.
     *
     * @param from source square
     * @param to destination square
     * @param currentTurn color whose turn it is
     * @return true when the move was legal and applied
     */
    public boolean movePiece(Position from, Position to, String currentTurn) {
        if (!isLegalMove(from, to, currentTurn)) {
            return false;
        }
        applyMove(from, to);
        return true;
    }

    /**
     * Moves a piece without validating turn or king safety. This overload is
     * kept for older console code and utility use.
     *
     * @param from source square
     * @param to destination square
     * @return true when a piece existed and was moved
     */
    public boolean movePiece(Position from, Position to) {
        if (!isWithinBounds(from) || !isWithinBounds(to) || getPiece(from) == null) {
            return false;
        }
        applyMove(from, to);
        return true;
    }

    /**
     * Validates a move without changing the board.
     *
     * @param from source square
     * @param to destination square
     * @param currentTurn color whose turn it is
     * @return true when the move is legal
     */
    public boolean isLegalMove(Position from, Position to, String currentTurn) {
        if (!isWithinBounds(from) || !isWithinBounds(to) || from.equals(to)) {
            return false;
        }

        Piece movingPiece = getPiece(from);
        if (movingPiece == null || !movingPiece.getColor().equals(currentTurn)) {
            return false;
        }

        Piece destinationPiece = getPiece(to);
        if (destinationPiece != null) {
            if (destinationPiece.getColor().equals(currentTurn)) {
                return false;
            }
            if (destinationPiece instanceof King) {
                return false;
            }
        }

        return movingPiece.isValidMove(this, to) && canMoveWithoutLeavingKingInCheck(from, to);
    }

    /**
     * Returns whether every square between two aligned positions is empty.
     *
     * @param from source square
     * @param to destination square
     * @return true when no piece blocks the route
     */
    public boolean isPathClear(Position from, Position to) {
        if (!isWithinBounds(from) || !isWithinBounds(to)) {
            return false;
        }

        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getCol() - from.getCol());
        boolean straightMove = from.getRow() == to.getRow() || from.getCol() == to.getCol();
        boolean diagonalMove = rowDiff == colDiff;
        if (!straightMove && !diagonalMove) {
            return false;
        }

        int rowStep = Integer.compare(to.getRow(), from.getRow());
        int colStep = Integer.compare(to.getCol(), from.getCol());
        int row = from.getRow() + rowStep;
        int col = from.getCol() + colStep;

        while (row != to.getRow() || col != to.getCol()) {
            if (getPiece(new Position(row, col)) != null) {
                return false;
            }
            row += rowStep;
            col += colStep;
        }

        return true;
    }

    /**
     * Simulates a move and verifies that the moving side's king remains safe.
     *
     * @param from source square
     * @param to destination square
     * @return true when the simulated move does not leave the king in check
     */
    public boolean canMoveWithoutLeavingKingInCheck(Position from, Position to) {
        Piece movingPiece = getPiece(from);
        if (movingPiece == null || !isWithinBounds(to)) {
            return false;
        }
        Piece capturedPiece = getPiece(to);
        Position originalPosition = movingPiece.getPosition();

        grid[to.getRow()][to.getCol()] = movingPiece;
        movingPiece.setPosition(to);
        grid[from.getRow()][from.getCol()] = null;

        boolean kingSafe = !isInCheck(movingPiece.getColor());

        grid[from.getRow()][from.getCol()] = movingPiece;
        movingPiece.setPosition(originalPosition);
        grid[to.getRow()][to.getCol()] = capturedPiece;
        if (capturedPiece != null) {
            capturedPiece.setPosition(to);
        }

        return kingSafe;
    }

    /**
     * Determines whether the specified king is currently under attack.
     *
     * @param color king color to inspect
     * @return true when the king is in check
     */
    public boolean isInCheck(String color) {
        Position kingPosition = findKing(color);
        if (kingPosition == null) {
            return false;
        }

        String opponentColor = oppositeColor(color);
        return isSquareAttacked(kingPosition, opponentColor);
    }

    /**
     * Determines whether the specified color is checkmated.
     *
     * @param color color to inspect
     * @return true when the color is in check and has no legal move
     */
    public boolean isCheckmate(String color) {
        if (!isInCheck(color)) {
            return false;
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    Position from = new Position(row, col);
                    for (int destRow = 0; destRow < 8; destRow++) {
                        for (int destCol = 0; destCol < 8; destCol++) {
                            if (isLegalMove(from, new Position(destRow, destCol), color)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Finds the king for the requested color.
     *
     * @param color king color
     * @return king position, or null if not found
     */
    public Position findKing(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    /**
     * Clears the board and rebuilds it from saved piece symbols.
     *
     * @param state 8x8 saved board symbols
     */
    public void loadFromSymbols(String[][] state) {
        grid = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                String symbol = state[row][col];
                if (symbol != null && !symbol.isBlank() && !symbol.equals("-")) {
                    setPiece(new Position(row, col), createPieceFromSymbol(symbol, new Position(row, col)));
                }
            }
        }
    }

    /**
     * Returns a symbol grid suitable for save files and GUI rendering.
     *
     * @return 8x8 piece symbol array
     */
    public String[][] toSymbolArray() {
        String[][] state = new String[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                state[row][col] = piece == null ? "" : piece.getSymbol();
            }
        }
        return state;
    }

    /**
     * Returns whether a position is on the 8x8 board.
     *
     * @param position position to inspect
     * @return true when in bounds
     */
    public boolean isWithinBounds(Position position) {
        return position != null
                && position.getRow() >= 0
                && position.getRow() < 8
                && position.getCol() >= 0
                && position.getCol() < 8;
    }

    private boolean isSquareAttacked(Position square, String attackerColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null
                        && piece.getColor().equals(attackerColor)
                        && piece.isValidMove(this, square)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void applyMove(Position from, Position to) {
        Piece movingPiece = getPiece(from);
        grid[to.getRow()][to.getCol()] = movingPiece;
        movingPiece.setPosition(to);
        grid[from.getRow()][from.getCol()] = null;
    }

    private String oppositeColor(String color) {
        return "White".equals(color) ? "Black" : "White";
    }

    private Piece createPieceFromSymbol(String symbol, Position position) {
        String normalized = symbol.trim();
        if (normalized.length() != 2) {
            throw new IllegalArgumentException("Invalid piece symbol: " + symbol);
        }

        String color = normalized.charAt(0) == 'w' ? "White" : "Black";
        char type = Character.toUpperCase(normalized.charAt(1));

        switch (type) {
            case 'P':
                return new Pawn(color, position);
            case 'R':
                return new Rook(color, position);
            case 'N':
                return new Knight(color, position);
            case 'B':
                return new Bishop(color, position);
            case 'Q':
                return new Queen(color, position);
            case 'K':
                return new King(color, position);
            default:
                throw new IllegalArgumentException("Invalid piece symbol: " + symbol);
        }
    }

    private Piece copyPiece(Piece piece, Position position) {
        return createPieceFromSymbol(piece.getSymbol(), position);
    }
}
