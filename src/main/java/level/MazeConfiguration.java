package level;

import com.google.gson.Gson;
import org.joml.Random;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan-Frederik Leißner on 04.04.2018.
 */
public class MazeConfiguration {
    public Vector2i maze_size;

    public long random_seed;
    private Random random;

    public int room_attempts;
    public Vector2i room_min_size;
    public Vector2i room_max_size;
    private List<Room> rooms;

    public int dead_end_remove_amount;

    public char standard_tile;

    public MazeConfiguration(Vector2i maze_size, long random_seed, int room_attempts, Vector2i room_min_size, Vector2i room_max_size, int dead_end_remove_amount, char standard_tile) {
        this.maze_size = maze_size;
        if(this.maze_size.x % 2 == 0){
            this.maze_size.x += 1;
        }
        if(this.maze_size.y % 2 == 0){
            this.maze_size.y += 1;
        }
        this.random_seed = random_seed;
        this.room_attempts = room_attempts;
        this.room_min_size = room_min_size;
        this.room_max_size = room_max_size;
        this.dead_end_remove_amount = dead_end_remove_amount;
        this.standard_tile = standard_tile;
    }

    public Random random() {
        if (random == null) {
            random = new Random(random_seed);
        }
        return random;
    }

    public List<Room> rooms() {
        if (rooms == null) {
            rooms = generate_random_rooms();
        }
        return rooms;
    }

    private List<Room> generate_random_rooms() {
        List<Room> rooms = new ArrayList<>();
        for (int attempt = 0; attempt < room_attempts; attempt++) {
            Room room = new Room(random_point(maze_size), random_point(room_min_size, room_max_size));
            if (valid_room(room, rooms)) {
                rooms.add(room);
            }
        }
        return rooms;
    }

    private boolean valid_room(Room room, List<Room> rooms) {
        return usable_room(room) && !room_out_of_maze(room) && !room_collides(room, rooms);
    }

    private boolean usable_room(Room room) {
        return ((room.size.y % 2) == 1) && ((room.size.x % 2) == 1) && ((room.position.x % 2) == 1) && ((room.position.y % 2) == 1);
    }

    private boolean room_out_of_maze(Room room) {
        return ((room.position.x + room.size.x - 1) > maze_size.x - 1) || ((room.position.y + room.size.y - 1) > maze_size.y - 1);
    }

    private boolean room_collides(Room room, List<Room> rooms) {
        for (Room existent_room : rooms) {
            if (room.collide_with(existent_room)) {
                return true;
            }
        }
        return false;
    }

    private Vector2i random_point(Vector2i max) {
        return random_point(new Vector2i(0, 0), max);
    }

    private Vector2i random_point(Vector2i min, Vector2i max) {
        return new Vector2i(
                min.x + random().nextInt(max.x - min.x + 1),
                min.y + random().nextInt(max.y - min.y + 1)
        );
    }

    public String as_json(){
        Gson json = new Gson();
        return json.toJson(this);
    }
}
