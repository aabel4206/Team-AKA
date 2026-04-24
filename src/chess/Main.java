package chess;

import javax.swing.SwingUtilities;
import chess.gui.ChessGUI;

/**
 * Application entry point for the Swing chess game.
 */
public class Main {
    /**
     * Launches the GUI on the Swing event dispatch thread.
     *
     * @param args command-line arguments, unused
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGUI());
    }
}
