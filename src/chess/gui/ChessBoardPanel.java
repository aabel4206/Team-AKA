package chess.gui;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.BorderFactory;

public class ChessBoardPanel extends JPanel {

    public ChessBoardPanel() {
        setLayout(new GridLayout(8, 8));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessSquareButton square = new ChessSquareButton(row, col);

                if ((row + col) % 2 == 0) {
                    square.setBackground(new Color(240, 217, 181));
                } else {
                    square.setBackground(new Color(181, 136, 99));
                }

                square.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                setInitialPieces(square, row, col);
                add(square);
            }
        }
    }

    private void setInitialPieces(ChessSquareButton square, int row, int col) {
        if (row == 0) {
            String[] pieces = {"bR", "bN", "bB", "bQ", "bK", "bB", "bN", "bR"};
            square.setText(pieces[col]);
        } else if (row == 1) {
            square.setText("bp");
        } else if (row == 6) {
            square.setText("wp");
        } else if (row == 7) {
            String[] pieces = {"wR", "wN", "wB", "wQ", "wK", "wB", "wN", "wR"};
            square.setText(pieces[col]);
        }
    }
}