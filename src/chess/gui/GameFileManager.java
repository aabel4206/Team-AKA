package chess.gui;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.Color;

public class GameFileManager {

    public static void saveGame(File file, ChessBoardPanel boardPanel) throws Exception {
        PrintWriter writer = new PrintWriter(file);

        writer.println(boardPanel.getLightSquareColor().getRed() + "," +
                       boardPanel.getLightSquareColor().getGreen() + "," +
                       boardPanel.getLightSquareColor().getBlue());

        writer.println(boardPanel.getDarkSquareColor().getRed() + "," +
                       boardPanel.getDarkSquareColor().getGreen() + "," +
                       boardPanel.getDarkSquareColor().getBlue());

        writer.println(boardPanel.getBoardPixelSize());
        writer.println(boardPanel.getPieceFontSize());

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

    public static void loadGame(File file, ChessBoardPanel boardPanel) throws Exception {
        Scanner scanner = new Scanner(file);

        Color lightColor = parseColor(scanner.nextLine());
        Color darkColor = parseColor(scanner.nextLine());
        int boardSize = Integer.parseInt(scanner.nextLine());
        int pieceFontSize = Integer.parseInt(scanner.nextLine());

        String[][] state = new String[8][8];

        for (int row = 0; row < 8; row++) {
            String[] parts = scanner.nextLine().split(" ");
            for (int col = 0; col < 8; col++) {
                if (parts[col].equals("-")) {
                    state[row][col] = "";
                } else {
                    state[row][col] = parts[col];
                }
            }
        }

        scanner.close();

        boardPanel.applySettings(lightColor, darkColor, boardSize, pieceFontSize);
        boardPanel.setBoardState(state);
    }

    private static Color parseColor(String line) {
        String[] rgb = line.split(",");
        int red = Integer.parseInt(rgb[0]);
        int green = Integer.parseInt(rgb[1]);
        int blue = Integer.parseInt(rgb[2]);
        return new Color(red, green, blue);
    }
}
