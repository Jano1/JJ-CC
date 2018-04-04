package level;

import org.joml.Vector2i;

/**
 * Created by Jan-Frederik Leißner on 04.04.2018.
 */
public class Maze extends TilePatch{
    private MazeConfiguration configuration;

    public Maze(MazeConfiguration configuration){
        super();
        this.configuration = configuration;
    }

    public boolean valid_position(Position position) {
        return position.x >= 0 && position.y >= 0 && position.x < configuration.maze_size.x && position.y < configuration.maze_size.y;
    }

    public void set(Position p, Tile t){
        if(containsKey(p)){
            Tile to_change = get(p);
            to_change.type = t.type;
            to_change.region = t.region;
        }else{
            put(p,t);
        }
    }

    public MazeConfiguration configuration() {
        return configuration;
    }
}
