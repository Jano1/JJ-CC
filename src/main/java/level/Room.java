package level;

import org.joml.Rectangled;
import org.joml.Vector2i;

import java.awt.*;

/**
 * Created by Jan-Frederik Leißner on 04.04.2018.
 */
public class Room {

    public Vector2i position;
    public Vector2i size;

    public Room(Vector2i position, Vector2i size) {
        this.position = position;
        this.size = size;
    }

    public Rectangle as_rectangle(){
        return new Rectangle(position.x,position.y,size.x,size.y);
    }

    public boolean collide_with(Room other){
        return as_rectangle().intersects(other.as_rectangle());
    }

    public Vector2i other_corner(){
        return position.add(size,new Vector2i());
    }

    @Override
    public String toString() {
        return position.x+"|"+position.y+":"+size.x+"|"+size.y;
    }
}
