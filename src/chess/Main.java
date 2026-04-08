package chess;

import javax.swing.SwingUtilities;
import chess.gui.ChessGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGUI());
    }
}