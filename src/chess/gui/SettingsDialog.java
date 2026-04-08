package chess.gui;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;

public class SettingsDialog extends JDialog {

    private ChessGUI parentFrame;
    private ChessBoardPanel boardPanel;

    private JComboBox<String> lightColorBox;
    private JComboBox<String> darkColorBox;
    private JComboBox<String> boardSizeBox;
    private JComboBox<String> pieceStyleBox;

    public SettingsDialog(ChessGUI parentFrame, ChessBoardPanel boardPanel) {
        super(parentFrame, "Settings", true);
        this.parentFrame = parentFrame;
        this.boardPanel = boardPanel;

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        lightColorBox = new JComboBox<>(new String[]{"Classic Light", "White", "Light Gray", "Beige", "Blue"});
        darkColorBox = new JComboBox<>(new String[]{"Classic Dark", "Black", "Dark Gray", "Brown", "Green"});
        boardSizeBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
        pieceStyleBox = new JComboBox<>(new String[]{"Normal", "Large"});

        setSelectionsFromCurrentSettings();

        formPanel.add(new JLabel("Light Square Color:"));
        formPanel.add(lightColorBox);
        formPanel.add(new JLabel("Dark Square Color:"));
        formPanel.add(darkColorBox);
        formPanel.add(new JLabel("Board Size:"));
        formPanel.add(boardSizeBox);
        formPanel.add(new JLabel("Piece Text Style:"));
        formPanel.add(pieceStyleBox);

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(e -> applySettings());

        add(formPanel, BorderLayout.CENTER);
        add(applyButton, BorderLayout.SOUTH);

        setSize(350, 220);
        setLocationRelativeTo(parentFrame);
    }

    private void setSelectionsFromCurrentSettings() {
        Color light = boardPanel.getLightSquareColor();
        Color dark = boardPanel.getDarkSquareColor();

        if (light.equals(new Color(240, 217, 181))) {
            lightColorBox.setSelectedItem("Classic Light");
        } else if (light.equals(Color.WHITE)) {
            lightColorBox.setSelectedItem("White");
        } else if (light.equals(Color.LIGHT_GRAY)) {
            lightColorBox.setSelectedItem("Light Gray");
        } else if (light.equals(new Color(222, 184, 135))) {
            lightColorBox.setSelectedItem("Beige");
        } else {
            lightColorBox.setSelectedItem("Blue");
        }

        if (dark.equals(new Color(181, 136, 99))) {
            darkColorBox.setSelectedItem("Classic Dark");
        } else if (dark.equals(Color.BLACK)) {
            darkColorBox.setSelectedItem("Black");
        } else if (dark.equals(Color.DARK_GRAY)) {
            darkColorBox.setSelectedItem("Dark Gray");
        } else if (dark.equals(new Color(139, 69, 19))) {
            darkColorBox.setSelectedItem("Brown");
        } else {
            darkColorBox.setSelectedItem("Green");
        }

        int boardSize = boardPanel.getBoardPixelSize();
        if (boardSize == 480) {
            boardSizeBox.setSelectedItem("Small");
        } else if (boardSize == 600) {
            boardSizeBox.setSelectedItem("Medium");
        } else {
            boardSizeBox.setSelectedItem("Large");
        }

        int fontSize = boardPanel.getPieceFontSize();
        if (fontSize <= 18) {
            pieceStyleBox.setSelectedItem("Normal");
        } else {
            pieceStyleBox.setSelectedItem("Large");
        }
    }

    private void applySettings() {
        Color lightColor = getLightColor((String) lightColorBox.getSelectedItem());
        Color darkColor = getDarkColor((String) darkColorBox.getSelectedItem());
        int boardSize = getBoardSize((String) boardSizeBox.getSelectedItem());
        int fontSize = getPieceFontSize((String) pieceStyleBox.getSelectedItem());

        parentFrame.applyBoardSettings(lightColor, darkColor, boardSize, fontSize);
    }

    private Color getLightColor(String selection) {
        if (selection.equals("White")) {
            return Color.WHITE;
        }
        if (selection.equals("Light Gray")) {
            return Color.LIGHT_GRAY;
        }
        if (selection.equals("Beige")) {
            return new Color(222, 184, 135);
        }
        if (selection.equals("Blue")) {
            return new Color(173, 216, 230);
        }
        return new Color(240, 217, 181);
    }

    private Color getDarkColor(String selection) {
        if (selection.equals("Black")) {
            return Color.BLACK;
        }
        if (selection.equals("Dark Gray")) {
            return Color.DARK_GRAY;
        }
        if (selection.equals("Brown")) {
            return new Color(139, 69, 19);
        }
        if (selection.equals("Green")) {
            return new Color(34, 139, 34);
        }
        return new Color(181, 136, 99);
    }

    private int getBoardSize(String selection) {
        if (selection.equals("Small")) {
            return 480;
        }
        if (selection.equals("Large")) {
            return 720;
        }
        return 600;
    }

    private int getPieceFontSize(String selection) {
        if (selection.equals("Large")) {
            return 24;
        }
        return 18;
    }
}