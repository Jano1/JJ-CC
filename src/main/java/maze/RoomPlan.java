package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jan on 08.06.2016.
 */
public class RoomPlan {
    private List<Room> rooms;
    private int size;
    private Random rand;

    public RoomPlan(int size) {
        this.size = size;
        rooms = new ArrayList<>();
    }

    public boolean addRoom(Room b) {
        if (isValid(b)) {
            rooms.add(b);
            return true;
        }
        return false;
    }

    private boolean isValid(Room b) {
        return hasValidBoundaries(b) && !collides(b) && !isToBig(b) && !isToSmall(b);
    }

    private boolean isToSmall(Room b) {
        return b.getPos().x < 0 || b.getPos().y < 0;
    }

    private boolean isToBig(Room b) {
        return ((b.getPos().x + b.getWidth()) > size - 1) || ((b.getPos().y + b.getHeight()) > size - 1);
    }

    private boolean collides(Room b) {
        for (Room r : rooms) {
            if (r.collide(b)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasValidBoundaries(Room b) {
        return ((b.getHeight() % 2) == 1) && ((b.getWidth() % 2) == 1) && ((b.getPos().x % 2) == 1) && ((b.getPos().y % 2) == 1);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void createRandomRooms(int attempts, long seed, int min_room_size, int max_room_size) {
        if (rand == null) {
            rand = new Random(seed);
        }
        int trys = attempts;
        int success = 0;
        while (trys > 0) {
            int x = rand.nextInt(size);
            x = (x % 2 == 1 ? x : x + 1);
            int y = rand.nextInt(size);
            y = (y % 2 == 1 ? y : y + 1);
            int w = min_room_size + rand.nextInt(max_room_size - min_room_size);
            w = (w % 2 == 1 ? w : w + 1);
            int h = min_room_size + rand.nextInt(max_room_size - min_room_size);
            h = (h % 2 == 1 ? h : h + 1);
            Position p = new Position(x, y);
            if (addRoom(new Room(p, w, h))) {
                success++;
            }
            trys--;
        }
    }
}
