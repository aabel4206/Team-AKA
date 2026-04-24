package chess.gui;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * Button representing a single square on the chess board.
 * Stores its immutable row and column so click handlers can create backend
 * positions.
 */
public class ChessSquareButton extends JButton {

    private final int row;
    private final int col;

    /**
     * Creates a square button for a board coordinate.
     *
     * @param row zero-based row
     * @param col zero-based column
     */
    public ChessSquareButton(int row, int col) {
        this.row = row;
        this.col = col;
        setPieceFontSize(40);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
     * Returns the square row.
     *
     * @return zero-based row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the square column.
     *
     * @return zero-based column
     */
    public int getCol() {
        return col;
    }

    /**
     * Applies the configured piece font size.
     *
     * @param size font size
     */
    public void setPieceFontSize(int size) {
        int effectiveSize = Math.max(size, 36);
        setFont(new Font("Serif", Font.BOLD, effectiveSize));
    }
}
