package maze;

import javafx.geometry.Pos;
import org.apache.commons.collections4.map.HashedMap;
import org.joml.Vector2i;

import java.util.Map;

/**
 * Created by Jan-Frederik Leißner on 03.04.2018.
 */
public class Room{

    Map<Position,Tile> tiles;
    public static final char MAX = 'a';
    public static final char MIN = 'i';

    public Room(){
        tiles = new HashedMap<>();
    }

    public Tile get_tile_at(Position position){
        if(!tiles.containsKey(position)){
            tiles.put(position,new Tile());
        }
        return tiles.get(position);
    }

    public void normalize(){
        translate_all(get_position(MIN).negate());
    }

    private void translate_all(Vector2i amount) {
        if(amount.x != 0 || amount.y != 0){
            for(Position p : tiles.keySet()){
                p.add(amount);
            }
        }
    }

    private Position get_position(char type) {
        Position position = null;
        for(Position p : tiles.keySet()){
            if(position == null){
                position = new Position(p.x,p.y);
                continue;
            }
            if((type == MIN && p.x < position.x) || (type == MAX && p.x > position.x)){
                position.x = p.x;
            }
            if((type == MIN && p.y < position.y) || (type == MAX && p.y > position.y)){
                position.y = p.y;
            }
        }
        return position;
    }

    public Map<Position,Tile> get_tiles(){
        return tiles;
    }

    public TilePatch as_tilepatch(){
        normalize();
        TilePatch patch = new TilePatch(get_position(MAX));
        for(Map.Entry<Position,Tile> entry : get_tiles().entrySet()){
            patch.patch()[entry.getKey().x][entry.getKey().y] = entry.getValue();
        }
        return patch;
    }
}
