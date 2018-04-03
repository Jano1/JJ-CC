package maze;
import org.apache.commons.collections4.map.HashedMap;
import org.joml.Vector2i;

import java.util.Map;

/**
 * Created by Jan-Frederik Leißner on 03.04.2018.
 */
public class TilePatch {

    Map<Position,Tile> tiles;
    public static final char POSITION_MAX = 'a';
    public static final char POSITION_MIN = 'i';

    public TilePatch(){
        tiles = new HashedMap<>();
    }

    public Tile get_tile_at(Position position){
        if(!tiles.containsKey(position)){
            tiles.put(position,new Tile());
        }
        return tiles.get(position);
    }

    public void normalize(){
        translate_all(get_position(POSITION_MIN).negate());
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
            if((type == POSITION_MIN && p.x < position.x) || (type == POSITION_MAX && p.x > position.x)){
                position.x = p.x;
            }
            if((type == POSITION_MIN && p.y < position.y) || (type == POSITION_MAX && p.y > position.y)){
                position.y = p.y;
            }
        }
        return position;
    }
}
