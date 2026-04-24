package chess.gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Modal dialog for changing board colors, board size, and piece text size.
 */
public class SettingsDialog extends JDialog {

    private static final Color DIALOG_BACKGROUND = new Color(245, 242, 235);
    private static final Color PANEL_BACKGROUND = new Color(252, 248, 239);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font CONTROL_FONT = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 13);

    private ChessGUI parentFrame;
    private ChessBoardPanel boardPanel;

    private JComboBox<String> lightColorBox;
    private JComboBox<String> darkColorBox;
    private JComboBox<String> boardSizeBox;
    private JComboBox<String> pieceStyleBox;

    /**
     * Creates the settings dialog for the current GUI.
     *
     * @param parentFrame owning chess window
     * @param boardPanel board panel to configure
     */
    public SettingsDialog(ChessGUI parentFrame, ChessBoardPanel boardPanel) {
        super(parentFrame, "Board Settings", true);
        this.parentFrame = parentFrame;
        this.boardPanel = boardPanel;

        setLayout(new BorderLayout());
        getContentPane().setBackground(DIALOG_BACKGROUND);

        JLabel titleLabel = new JLabel("Board Settings");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(new Color(45, 35, 25));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(14, 16, 8, 16));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PANEL_BACKGROUND);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 198, 180)),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)));

        lightColorBox = createComboBox(new String[]{"Classic Light", "White", "Light Gray", "Beige", "Blue"});
        darkColorBox = createComboBox(new String[]{"Classic Dark", "Black", "Dark Gray", "Brown", "Green"});
        boardSizeBox = createComboBox(new String[]{"Small", "Medium", "Large"});
        pieceStyleBox = createComboBox(new String[]{"Normal", "Large"});

        setSelectionsFromCurrentSettings();

        addSettingRow(formPanel, 0, "Light Square Color", lightColorBox);
        addSettingRow(formPanel, 1, "Dark Square Color", darkColorBox);
        addSettingRow(formPanel, 2, "Board Size", boardSizeBox);
        addSettingRow(formPanel, 3, "Piece Text Style", pieceStyleBox);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(DIALOG_BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        contentPanel.add(formPanel, BorderLayout.CENTER);

        JButton applyButton = createButton("Apply");
        applyButton.addActionListener(e -> applySettings());

        JButton cancelButton = createButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonPanel.setBackground(DIALOG_BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(12, 16, 14, 16));
        buttonPanel.add(cancelButton);
        buttonPanel.add(applyButton);

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(390, 290);
        setLocationRelativeTo(parentFrame);
    }

    private JComboBox<String> createComboBox(String[] values) {
        JComboBox<String> comboBox = new JComboBox<>(values);
        comboBox.setFont(CONTROL_FONT);
        return comboBox;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        return button;
    }

    private void addSettingRow(JPanel panel, int row, String labelText, JComboBox<String> comboBox) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(8, 0, 8, 18);

        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(new Color(45, 35, 25));
        panel.add(label, labelConstraints);

        GridBagConstraints inputConstraints = new GridBagConstraints();
        inputConstraints.gridx = 1;
        inputConstraints.gridy = row;
        inputConstraints.weightx = 1.0;
        inputConstraints.fill = GridBagConstraints.HORIZONTAL;
        inputConstraints.insets = new Insets(8, 0, 8, 0);
        panel.add(comboBox, inputConstraints);
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
        if (fontSize <= 40) {
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
            return 46;
        }
        return 40;
    }
}
