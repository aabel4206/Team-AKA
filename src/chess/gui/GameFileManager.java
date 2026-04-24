package chess.gui;

import java.awt.Color;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Saves and loads GUI settings plus the backend board state.
 * The file format keeps the Phase 2 visual settings and adds the current turn
 * before the 8 board rows.
 */
public class GameFileManager {

    /**
     * Saves board colors, size, piece font size, current turn, and board state.
     *
     * @param file destination file
     * @param boardPanel board panel to save
     * @throws Exception when writing fails
     */
    public static void saveGame(File file, ChessBoardPanel boardPanel) throws Exception {
        PrintWriter writer = new PrintWriter(file);

        writer.println(boardPanel.getLightSquareColor().getRed() + ","
                + boardPanel.getLightSquareColor().getGreen() + ","
                + boardPanel.getLightSquareColor().getBlue());

        writer.println(boardPanel.getDarkSquareColor().getRed() + ","
                + boardPanel.getDarkSquareColor().getGreen() + ","
                + boardPanel.getDarkSquareColor().getBlue());

        writer.println(boardPanel.getBoardPixelSize());
        writer.println(boardPanel.getPieceFontSize());
        writer.println(boardPanel.getCurrentTurn());

        String[][] state = boardPanel.getBoardState();

        for (int row = 0; row < 8; row++) {
            StringBuilder line = new StringBuilder();
            for (int col = 0; col < 8; col++) {
                String value = state[row][col];
                if (value == null || value.isEmpty()) {
                    value = "-";
                }
                line.append(value);
                if (col < 7) {
                    line.append(" ");
                }
            }
            writer.println(line.toString());
        }

        writer.close();
    }

    /**
     * Loads board colors, size, piece font size, current turn, and board state.
     * Older Phase 2 save files without current-turn data are also accepted and
     * load with White to move.
     *
     * @param file source file
     * @param boardPanel board panel to update
     * @throws Exception when reading fails
     */
    public static void loadGame(File file, ChessBoardPanel boardPanel) throws Exception {
        Scanner scanner = new Scanner(file);

        Color lightColor = parseColor(scanner.nextLine());
        Color darkColor = parseColor(scanner.nextLine());
        int boardSize = Integer.parseInt(scanner.nextLine());
        int pieceFontSize = Integer.parseInt(scanner.nextLine());
        String firstBoardOrTurnLine = scanner.nextLine();

        String currentTurn = "White";
        String[][] state = new String[8][8];
        int startRow = 0;

        if (firstBoardOrTurnLine.equals("White") || firstBoardOrTurnLine.equals("Black")) {
            currentTurn = firstBoardOrTurnLine;
        } else {
            parseBoardRow(firstBoardOrTurnLine, state, 0);
            startRow = 1;
        }

        for (int row = startRow; row < 8; row++) {
            parseBoardRow(scanner.nextLine(), state, row);
        }

        scanner.close();

        boardPanel.applySettings(lightColor, darkColor, boardSize, pieceFontSize);
        boardPanel.loadBoardState(state, currentTurn);
    }

    private static void parseBoardRow(String line, String[][] state, int row) {
        String[] parts = line.split(" ");
        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid board row in save file.");
        }
        for (int col = 0; col < 8; col++) {
            state[row][col] = parts[col].equals("-") ? "" : parts[col];
        }
    }

    private static Color parseColor(String line) {
        String[] rgb = line.split(",");
        int red = Integer.parseInt(rgb[0]);
        int green = Integer.parseInt(rgb[1]);
        int blue = Integer.parseInt(rgb[2]);
        return new Color(red, green, blue);
    }
}
