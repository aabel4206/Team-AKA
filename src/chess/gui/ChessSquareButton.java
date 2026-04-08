package chess.gui;

import javax.swing.JButton;
import java.awt.Font;

public class ChessSquareButton extends JButton {

    private int row;
    private int col;

    public ChessSquareButton(int row, int col) {
        this.row = row;
        this.col = col;
        setFont(new Font("Arial", Font.BOLD, 18));
        setFocusPainted(false);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}