package chess.gui;

import chess.board.Position;
import chess.game.GameController;
import chess.game.MoveResult;
import chess.pieces.Piece;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.function.Consumer;

/**
 * Swing panel that displays the chess board and forwards user move attempts to
 * the game controller. The panel never mutates piece positions directly; it
 * refreshes every square from the backend board state.
 */
public class ChessBoardPanel extends JPanel {

    private final ChessSquareButton[][] squares;
    private final GameController controller;
    private final Consumer<MoveResult> moveResultListener;
    private Color lightSquareColor = new Color(240, 217, 181);
    private Color darkSquareColor = new Color(181, 136, 99);
    private int boardPixelSize = 720;
    private int pieceFontSize = 40;
    private ChessSquareButton selectedSquare = null;

    /**
     * Creates a board panel with a new game controller.
     */
    public ChessBoardPanel() {
        this(new GameController(), null);
    }

    /**
     * Creates a board panel using the supplied controller.
     *
     * @param controller backend game controller
     * @param moveResultListener listener for status updates
     */
    public ChessBoardPanel(GameController controller, Consumer<MoveResult> moveResultListener) {
        this.controller = controller;
        this.moveResultListener = moveResultListener;
        squares = new ChessSquareButton[8][8];
        setLayout(new GridLayout(8, 8));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        initializeSquares();
        refreshBoardFromBackend();
    }

    private void initializeSquares() {
        removeAll();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessSquareButton square = new ChessSquareButton(row, col);
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                square.setPieceFontSize(pieceFontSize);
                squares[row][col] = square;
                square.addActionListener(e -> handleSquareClick(square));
                add(square);
            }
        }
    }

    private void handleSquareClick(ChessSquareButton clicked) {
        if (controller.isGameOver()) {
            JOptionPane.showMessageDialog(this,
                    "The game is over. Start a new game to continue.",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Position clickedPosition = new Position(clicked.getRow(), clicked.getCol());
        Piece clickedPiece = controller.getBoard().getPiece(clickedPosition);

        if (selectedSquare == null) {
            selectSourceSquare(clicked, clickedPiece);
            return;
        }

        if (clicked == selectedSquare) {
            clearSelection();
            return;
        }

        Position source = new Position(selectedSquare.getRow(), selectedSquare.getCol());
        String movingColor = controller.getCurrentTurn();
        Piece destinationPiece = controller.getBoard().getPiece(clickedPosition);
        boolean captureAttempt = destinationPiece != null && !destinationPiece.getColor().equals(movingColor);
        MoveResult result = controller.attemptMove(source, clickedPosition);
        clearSelection();
        refreshBoardFromBackend();

        MoveResult displayResult = result;
        if (result.isSuccess() && captureAttempt && !result.isCheck() && !result.isCheckmate()) {
            displayResult = new MoveResult(true, movingColor + " captured a piece.",
                    result.getCurrentTurn(), false, false, null);
        }
        notifyMoveResult(displayResult);

        if (!result.isSuccess()) {
            JOptionPane.showMessageDialog(this,
                    "That move is not legal. Please choose a valid move.",
                    "Invalid Move",
                    JOptionPane.WARNING_MESSAGE);
        } else if (result.isCheckmate()) {
            JOptionPane.showMessageDialog(this,
                    "Checkmate! " + result.getWinner() + " wins.",
                    "Checkmate",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (result.isCheck()) {
            JOptionPane.showMessageDialog(this,
                    "Check! " + result.getCurrentTurn() + " king is under attack.",
                    "Check",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void selectSourceSquare(ChessSquareButton clicked, Piece clickedPiece) {
        if (clickedPiece == null) {
            MoveResult result = new MoveResult(false, "Select a square that contains a piece.",
                    controller.getCurrentTurn(), false, false, null);
            notifyMoveResult(result);
            JOptionPane.showMessageDialog(this,
                    "Please select one of your pieces to move.",
                    "Invalid Move",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!clickedPiece.getColor().equals(controller.getCurrentTurn())) {
            MoveResult result = new MoveResult(false, "It is " + controller.getCurrentTurn() + "'s turn.",
                    controller.getCurrentTurn(), false, false, null);
            notifyMoveResult(result);
            JOptionPane.showMessageDialog(this,
                    "You cannot move your opponent's piece.",
                    "Invalid Move",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        selectedSquare = clicked;
        clicked.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
    }

    /**
     * Starts a new game and refreshes the board display.
     */
    public void resetBoard() {
        controller.resetGame();
        selectedSquare = null;
        refreshBoardFromBackend();
        notifyMoveResult(new MoveResult(true, controller.getCurrentTurn() + "'s turn.",
                controller.getCurrentTurn(), false, false, null));
    }

    /**
     * Refreshes every GUI square from the backend board.
     */
    public void refreshBoardFromBackend() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = controller.getBoard().getPiece(new Position(row, col));
                squares[row][col].setText(piece == null ? "" : toDisplaySymbol(piece.getSymbol()));
                squares[row][col].setPieceFontSize(pieceFontSize);
                squares[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }

        updateSquareColors();
        revalidate();
        repaint();
    }

    /**
     * Returns the current board as piece symbols for saving.
     *
     * @return 8x8 board state
     */
    public String[][] getBoardState() {
        return controller.getBoard().toSymbolArray();
    }

    /**
     * Loads a board state with White to move.
     *
     * @param state saved board symbols
     */
    public void setBoardState(String[][] state) {
        loadBoardState(state, "White");
    }

    /**
     * Loads a board state and current turn into the backend, then refreshes the GUI.
     *
     * @param state saved board symbols
     * @param currentTurn side to move
     */
    public void loadBoardState(String[][] state, String currentTurn) {
        selectedSquare = null;
        controller.loadGame(state, currentTurn);
        refreshBoardFromBackend();
        notifyMoveResult(new MoveResult(true, controller.getCurrentTurn() + "'s turn.",
                controller.getCurrentTurn(), false, false, null));
    }

    /**
     * Applies Phase 2 visual settings while keeping backend state unchanged.
     *
     * @param lightColor light square color
     * @param darkColor dark square color
     * @param boardPixelSize board size in pixels
     * @param pieceFontSize piece text font size
     */
    public void applySettings(Color lightColor, Color darkColor, int boardPixelSize, int pieceFontSize) {
        this.lightSquareColor = lightColor;
        this.darkSquareColor = darkColor;
        this.boardPixelSize = boardPixelSize;
        this.pieceFontSize = pieceFontSize;

        refreshBoardFromBackend();
    }

    /**
     * Reapplies alternating square colors.
     */
    public void updateSquareColors() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0) {
                    squares[row][col].setBackground(lightSquareColor);
                } else {
                    squares[row][col].setBackground(darkSquareColor);
                }
            }
        }
    }

    /**
     * Returns the current turn for save files and status display.
     *
     * @return current turn
     */
    public String getCurrentTurn() {
        return controller.getCurrentTurn();
    }

    public Color getLightSquareColor() {
        return lightSquareColor;
    }

    public Color getDarkSquareColor() {
        return darkSquareColor;
    }

    public int getBoardPixelSize() {
        return boardPixelSize;
    }

    public int getPieceFontSize() {
        return pieceFontSize;
    }

    private void clearSelection() {
        if (selectedSquare != null) {
            selectedSquare.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            selectedSquare = null;
        }
    }

    private void notifyMoveResult(MoveResult result) {
        if (moveResultListener != null) {
            moveResultListener.accept(result);
        }
    }

    private String toDisplaySymbol(String backendSymbol) {
        if ("wp".equals(backendSymbol)) {
            return "\u2659";
        }
        if ("wR".equals(backendSymbol)) {
            return "\u2656";
        }
        if ("wN".equals(backendSymbol)) {
            return "\u2658";
        }
        if ("wB".equals(backendSymbol)) {
            return "\u2657";
        }
        if ("wQ".equals(backendSymbol)) {
            return "\u2655";
        }
        if ("wK".equals(backendSymbol)) {
            return "\u2654";
        }
        if ("bp".equals(backendSymbol)) {
            return "\u265F";
        }
        if ("bR".equals(backendSymbol)) {
            return "\u265C";
        }
        if ("bN".equals(backendSymbol)) {
            return "\u265E";
        }
        if ("bB".equals(backendSymbol)) {
            return "\u265D";
        }
        if ("bQ".equals(backendSymbol)) {
            return "\u265B";
        }
        if ("bK".equals(backendSymbol)) {
            return "\u265A";
        }
        return backendSymbol;
    }
}
