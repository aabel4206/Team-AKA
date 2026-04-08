package chess.gui;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.BorderFactory;

public class ChessBoardPanel extends JPanel {

    private ChessSquareButton[][] squares;
    private Color lightSquareColor = new Color(240, 217, 181);
    private Color darkSquareColor = new Color(181, 136, 99);
    private int boardPixelSize = 600;
    private int pieceFontSize = 18;

    public ChessBoardPanel() {
        squares = new ChessSquareButton[8][8];
        setLayout(new GridLayout(8, 8));
        initializeBoard();
    }

    private void initializeBoard() {
        removeAll();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessSquareButton square = new ChessSquareButton(row, col);
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                square.setPieceFontSize(pieceFontSize);
                squares[row][col] = square;
                add(square);
            }
        }

        resetBoard();
    }

    public void resetBoard() {
        clearBoard();

        for (int col = 0; col < 8; col++) {
            squares[1][col].setText("bp");
            squares[6][col].setText("wp");
        }

        String[] blackPieces = {"bR", "bN", "bB", "bQ", "bK", "bB", "bN", "bR"};
        String[] whitePieces = {"wR", "wN", "wB", "wQ", "wK", "wB", "wN", "wR"};

        for (int col = 0; col < 8; col++) {
            squares[0][col].setText(blackPieces[col]);
            squares[7][col].setText(whitePieces[col]);
        }

        updateSquareColors();
        revalidate();
        repaint();
    }

    private void clearBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].setText("");
            }
        }
    }

    public String[][] getBoardState() {
        String[][] state = new String[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                state[row][col] = squares[row][col].getText();
            }
        }

        return state;
    }

    public void setBoardState(String[][] state) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].setText(state[row][col] == null ? "" : state[row][col]);
            }
        }

        updateSquareColors();
        revalidate();
        repaint();
    }

    public void applySettings(Color lightColor, Color darkColor, int boardPixelSize, int pieceFontSize) {
        this.lightSquareColor = lightColor;
        this.darkSquareColor = darkColor;
        this.boardPixelSize = boardPixelSize;
        this.pieceFontSize = pieceFontSize;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].setPieceFontSize(pieceFontSize);
            }
        }

        updateSquareColors();
        revalidate();
        repaint();
    }

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
}
