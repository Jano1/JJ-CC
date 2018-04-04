package level;

/**
 * Created by Jan-Frederik Leißner on 04.04.2018.
 */
public class Tile {
    public char type;
    public int region;

    public Tile(char type, int region) {
        this.type = type;
        this.region = region;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "type=" + type +
                ", region=" + region +
                '}';
    }
}
