package chess.gui;

import javax.swing.JFrame;

public class ChessGUI extends JFrame {

    public ChessGUI() {
        setTitle("Chess Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        add(new ChessBoardPanel());
        setVisible(true);
    }
}