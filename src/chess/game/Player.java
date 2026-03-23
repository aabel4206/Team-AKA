package chess.game;

public class Player {

    private final String name;
    private final String color;

    public Player(String color) {
        this(color, color);
    }

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }
}
