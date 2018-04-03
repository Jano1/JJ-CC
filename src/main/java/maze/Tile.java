package maze;

/**
 * Created by Jan-Frederik Leißner on 03.04.2018.
 */
public class Tile {
    public static final char FLAT = 'f';
    public static final char WALL = 'w';
    public static final char DOOR = 'd';

    public char type;
    public int value;

    public Tile(char type, int value) {
        this.type = type;
        this.value = value;
    }

    public Tile(){
        this(WALL,0);
    }
}
