package maze;

import java.awt.*;

/**
 * Created by Jan on 08.06.2016.
 */
public class Room {
    private Position pos;
    private int width, height;

    public Room(Position pos, int width, int height) {
        this.pos = pos;
        this.width = width;
        this.height = height;
    }

    private Rectangle getRect() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

    public boolean collide(Room r) {
        return getRect().intersects(r.getRect());
    }

    public Position getPos() {
        return pos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Room{" +
                "pos=" + pos +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
