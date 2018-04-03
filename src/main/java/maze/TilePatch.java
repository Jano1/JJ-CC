package maze;

import org.joml.Vector2i;

/**
 * Created by Jan-Frederik Leißner on 03.04.2018.
 */
public class TilePatch {
    private Tile[][] patch;

    public TilePatch(int x,int y){
        patch = new Tile[x][y];
    }

    public TilePatch(Vector2i size){
        this(size.x,size.y);
    }

    public Tile[][] patch(){
        return patch;
    }
}
