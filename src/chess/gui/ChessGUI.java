package chess.gui;

import chess.game.GameController;
import chess.game.MoveResult;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

/**
 * Main Swing window for the chess application.
 * It builds the menu, status label, and board panel while preserving the Phase
 * 2 settings and save/load features.
 */
public class ChessGUI extends JFrame {

    private static final Color APP_BACKGROUND = new Color(245, 242, 235);
    private static final Color STATUS_BACKGROUND = new Color(252, 248, 239);
    private static final Color STATUS_TEXT = new Color(45, 35, 25);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 24);
    private static final Font MENU_FONT = new Font("SansSerif", Font.PLAIN, 14);

    private final ChessBoardPanel boardPanel;
    private final JLabel statusLabel;

    /**
     * Creates and displays the chess window.
     */
    public ChessGUI() {
        configureOptionPaneStyle();

        setTitle("Team-AKA Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(APP_BACKGROUND);

        statusLabel = new JLabel(formatTurnText("White"));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(STATUS_BACKGROUND);
        statusLabel.setFont(LABEL_FONT);
        statusLabel.setForeground(STATUS_TEXT);
        statusLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(210, 198, 180)),
                BorderFactory.createEmptyBorder(14, 10, 14, 10)));

        GameController controller = new GameController();
        boardPanel = new ChessBoardPanel(controller, this::handleMoveResult);

        JPanel boardWrapper = new JPanel(new BorderLayout());
        boardWrapper.setBackground(APP_BACKGROUND);
        boardWrapper.setBorder(BorderFactory.createEmptyBorder(16, 16, 20, 16));
        boardWrapper.add(boardPanel, BorderLayout.CENTER);

        setJMenuBar(createMenuBar());
        add(statusLabel, BorderLayout.NORTH);
        add(boardWrapper, BorderLayout.CENTER);

        updateBoardWindowSize(boardPanel.getBoardPixelSize());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(250, 247, 240));
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(210, 198, 180)));

        JMenu gameMenu = createMenu("Game");
        JMenu settingsMenu = createMenu("Settings");
        JMenu helpMenu = createMenu("Help");

        JMenuItem newGameItem = createMenuItem("New Game");
        JMenuItem saveGameItem = createMenuItem("Save Game");
        JMenuItem loadGameItem = createMenuItem("Load Game");
        JMenuItem exitItem = createMenuItem("Exit");
        JMenuItem boardSettingsItem = createMenuItem("Board Settings");
        JMenuItem aboutItem = createMenuItem("About");

        newGameItem.addActionListener(e -> boardPanel.resetBoard());
        saveGameItem.addActionListener(e -> saveGame());
        loadGameItem.addActionListener(e -> loadGame());
        exitItem.addActionListener(e -> dispose());
        boardSettingsItem.addActionListener(e -> {
            SettingsDialog dialog = new SettingsDialog(this, boardPanel);
            dialog.setVisible(true);
        });
        aboutItem.addActionListener(e -> showAboutDialog());

        gameMenu.add(newGameItem);
        gameMenu.add(saveGameItem);
        gameMenu.add(loadGameItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);

        settingsMenu.add(boardSettingsItem);
        helpMenu.add(aboutItem);

        menuBar.add(gameMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private JMenu createMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.setFont(MENU_FONT);
        return menu;
    }

    private JMenuItem createMenuItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(MENU_FONT);
        return item;
    }

    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Game");
        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                GameFileManager.saveGame(file, boardPanel);
                JOptionPane.showMessageDialog(this,
                        "The current game state was saved successfully.",
                        "Game Saved",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Unable to save the game. Please try again.",
                        "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Game");
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                GameFileManager.loadGame(file, boardPanel);
                updateBoardWindowSize(boardPanel.getBoardPixelSize());
                statusLabel.setText(formatTurnText(boardPanel.getCurrentTurn()));
                JOptionPane.showMessageDialog(this,
                        "The saved game state was loaded successfully.",
                        "Game Loaded",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Unable to load the game. Please try again.",
                        "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Applies board appearance settings from the settings dialog.
     *
     * @param lightColor light square color
     * @param darkColor dark square color
     * @param boardPixelSize board size in pixels
     * @param pieceFontSize piece text size
     */
    public void applyBoardSettings(Color lightColor, Color darkColor, int boardPixelSize, int pieceFontSize) {
        boardPanel.applySettings(lightColor, darkColor, boardPixelSize, pieceFontSize);
        updateBoardWindowSize(boardPixelSize);
    }

    /**
     * Resizes the frame around the board.
     *
     * @param boardPixelSize board size in pixels
     */
    public void updateBoardWindowSize(int boardPixelSize) {
        boardPanel.setPreferredSize(new Dimension(boardPixelSize, boardPixelSize));
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    private void handleMoveResult(MoveResult result) {
        if (result.isCheckmate()) {
            statusLabel.setText("Checkmate! " + result.getWinner() + " Wins");
        } else if (result.isCheck()) {
            statusLabel.setText("Check: " + result.getCurrentTurn() + " King");
        } else if (result.getMessage() != null && result.getMessage().contains("captured")) {
            statusLabel.setText(result.getMessage());
        } else {
            statusLabel.setText(formatTurnText(result.getCurrentTurn()));
        }
    }

    private String formatTurnText(String color) {
        return color + "\u2019s Turn";
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "Team-AKA Chess\nOOP CS 3354.255\nPhase 3: Integrated GUI and backend chess logic.",
                "About Team-AKA Chess",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void configureOptionPaneStyle() {
        UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.BOLD, 13));
        UIManager.put("FileChooser.font", new Font("SansSerif", Font.PLAIN, 13));
    }
}
