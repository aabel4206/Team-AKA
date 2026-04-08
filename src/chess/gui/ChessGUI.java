package chess.gui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Color;
import java.io.File;

public class ChessGUI extends JFrame {

    private ChessBoardPanel boardPanel;

    public ChessGUI() {
        setTitle("Chess Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardPanel = new ChessBoardPanel();
        setJMenuBar(createMenuBar());
        add(boardPanel);

        updateBoardWindowSize(boardPanel.getBoardPixelSize());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        JMenuItem loadGameItem = new JMenuItem("Load Game");
        JMenuItem settingsItem = new JMenuItem("Settings");

        newGameItem.addActionListener(e -> boardPanel.resetBoard());
        saveGameItem.addActionListener(e -> saveGame());
        loadGameItem.addActionListener(e -> loadGame());
        settingsItem.addActionListener(e -> {
            SettingsDialog dialog = new SettingsDialog(this, boardPanel);
            dialog.setVisible(true);
        });

        gameMenu.add(newGameItem);
        gameMenu.add(saveGameItem);
        gameMenu.add(loadGameItem);
        gameMenu.add(settingsItem);

        menuBar.add(gameMenu);
        return menuBar;
    }

    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                GameFileManager.saveGame(file, boardPanel);
                JOptionPane.showMessageDialog(this, "Game saved successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving game.");
            }
        }
    }

    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                GameFileManager.loadGame(file, boardPanel);
                updateBoardWindowSize(boardPanel.getBoardPixelSize());
                JOptionPane.showMessageDialog(this, "Game loaded successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading game.");
            }
        }
    }

    public void applyBoardSettings(Color lightColor, Color darkColor, int boardPixelSize, int pieceFontSize) {
        boardPanel.applySettings(lightColor, darkColor, boardPixelSize, pieceFontSize);
        updateBoardWindowSize(boardPixelSize);
    }

    public void updateBoardWindowSize(int boardPixelSize) {
        boardPanel.setPreferredSize(new Dimension(boardPixelSize, boardPixelSize));
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }
}