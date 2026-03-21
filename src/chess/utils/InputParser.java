package chess.utils;

public class InputParser {

    public static boolean isValidFormat(String input) {
        if (input == null) {
            return false;
        }
        return input.trim().matches("^[A-Ha-h][1-8]\\s+[A-Ha-h][1-8]$");
    }

    public static String[] parseMove(String input) {
        String[] parts = input.trim().toUpperCase().split("\\s+");
        return new String[]{parts[0], parts[1]};
    }
}