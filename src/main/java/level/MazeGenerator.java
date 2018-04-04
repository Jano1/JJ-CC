package level;

import org.apache.commons.collections4.map.HashedMap;
import org.joml.Vector2i;

import java.util.*;

/**
 * Created by Jan-Frederik Leißner on 04.04.2018.
 */
public class MazeGenerator {
    public static Maze generate(MazeConfiguration configuration) {
        Maze maze = new Maze(configuration);
        add_rooms(maze);
        create_paths(maze);
        create_doors(maze);
        remove_dead_ends(maze);
        return maze;
    }

    private static void create_doors(Maze maze) {
        Map<String, List<Position>> connections = new HashedMap();
        int margin = 1;
        for (int x = margin; x < maze.configuration().maze_size.x - (margin+1); x++) {
            for (int y = margin; y < maze.configuration().maze_size.y - (margin+1); y++) {
                Position position = new Position(x,y);
                Tile tile = maze.getOrDefault(position,new Tile('w',0));
                if (tile.region == 0) {
                    get_LRConnection(connections, position, maze);
                    get_UDConnection(connections, position, maze);
                }
            }
        }
        for (List<Position> possible : connections.values()) {
            Position door = possible.get(maze.configuration().random().nextInt(possible.size()));
            maze.set(door,new Tile('d',0));
        }
    }

    private static void get_LRConnection(Map<String, List<Position>> connections, Position position, Maze maze) {
        Position pos_l = new Position(position.x-1,position.y);
        int l = maze.getOrDefault(pos_l,new Tile('w',0)).region;
        Position pos_r = new Position(position.x+1,position.y);
        int r = maze.getOrDefault(pos_r,new Tile('w',0)).region;
        if (position.y % 2 == 1) {
            insert_connection(connections, position, l, r);
        }
    }

    private static void get_UDConnection(Map<String, List<Position>> connections, Position position, Maze maze) {
        Position pos_u = new Position(position.x,position.y-1);
        int u = maze.getOrDefault(pos_u,new Tile('w',0)).region;
        Position pos_d = new Position(position.x,position.y+1);
        int d = maze.getOrDefault(pos_d,new Tile('w',0)).region;
        if (position.x % 2 == 1) {
            insert_connection(connections, position, u, d);
        }
    }

    private static void insert_connection(Map<String, List<Position>> connections, Position position, int a, int b) {
        if (a != 0 && b != 0 && a != b) {
            String key = a + "_" + b;
            String invKey = b + "_" + a;
            if (connections.containsKey(key)) {
                connections.get(key).add(position);
            } else if (connections.containsKey(invKey)) {
                connections.get(invKey).add(position);
            } else {
                connections.put(key, new ArrayList<>());
                connections.get(key).add(position);
            }
        }
    }

    private static void create_paths(Maze maze) {
        Stack<Position> visitable = get_visitable_positions(maze);
        int region = maze.configuration().rooms().size() + 1;
        while (!visitable.isEmpty()) {
            Stack<Position> path = new Stack<>();
            path.push(visitable.pop());
            maze.set(path.peek(), new Tile('f',region));
            while (!path.isEmpty()) {
                Position next = get_next_path_position(path.peek(),maze);
                if (next == null) {
                    path.pop();
                } else {
                    maze.set(next, new Tile('f',region));
                    Position position = new Position(path.peek().x + (next.x - path.peek().x) / 2, path.peek().y + (next.y - path.peek().y) / 2);
                    maze.set(position, new Tile('f',region));
                    path.push(next);
                    visitable.remove(next);
                }
            }
            System.out.println("added path with region "+region+"...");
            region++;
        }
    }

    private static Position get_next_path_position(Position current,Maze maze) {
        List<Position> possible = new ArrayList<>();
        possible.add(new Position(current.x + 2, current.y));
        possible.add(new Position(current.x - 2, current.y));
        possible.add(new Position(current.x, current.y + 2));
        possible.add(new Position(current.x, current.y - 2));
        List<Position> sorted = new ArrayList<>();
        for (Position p : possible) {
            if (is_possible_path_position(p,maze)) {
                sorted.add(p);
            }
        }
        if (sorted.isEmpty()) {
            return null;
        }
        return sorted.get(maze.configuration().random().nextInt(sorted.size()));
    }

    private static boolean is_possible_path_position(Position p,Maze m) {
        return m.valid_position(p) && m.getOrDefault(p,new Tile('w',0)).type == 'w' ;
    }

    private static Stack<Position> get_visitable_positions(Maze maze) {
        Stack<Position> visitable = new Stack<>();
        for (int x = 1; x < maze.configuration().maze_size.x; x += 2) {
            for (int y = 1; y < maze.configuration().maze_size.y; y += 2) {
                Position position = new Position(x,y);
                if(is_possible_path_position(position,maze)){
                    visitable.push(position);
                }
            }
        }
        return visitable;
    }

    private static void add_rooms(Maze maze) {
        int region = 1;
        for (Room room : maze.configuration().rooms()) {
            Vector2i corner = room.other_corner();
            for(int x = room.position.x; x < corner.x; x++){
                for(int y = room.position.y; y < corner.y; y++){
                    maze.put(new Position(x,y),new Tile('f',region));
                }
            }
            System.out.println("added "+room+" with region "+region+"...");
            region++;
        }
    }

    public static void remove_dead_ends(Maze maze) {
        int margin = 1;
        for (int a = 0; a < maze.configuration().dead_end_remove_amount; a++) {
            List<Position> dead_ends = new ArrayList<>();
            int counter = 0;
            for (int x = margin; x < maze.configuration().maze_size.x - margin; x += 2) {
                for (int y = margin; y < maze.configuration().maze_size.y  - margin; y += 2) {
                    if (is_dead_end(x, y, maze)) {
                        dead_ends.add(new Position(x, y));
                        counter++;
                    }
                }
            }
            if (counter < 1) {
                a = maze.configuration().dead_end_remove_amount;
            }
            for (Position p : dead_ends) {
                Position[] positions = new Position[]{
                        new Position(p.x + 1, p.y),
                        new Position(p.x - 1, p.y),
                        new Position(p.x, p.y + 1),
                        new Position(p.x, p.y - 1),
                        p
                };
                for (Position p_sub : positions) {
                    maze.set(p_sub, new Tile('w',0));
                }
            }
        }
    }

    private static boolean is_dead_end(int x, int y, Maze maze) {
        Position[] positions = new Position[]{
                new Position(x + 1, y),
                new Position(x - 1, y),
                new Position(x, y + 1),
                new Position(x, y - 1)
        };
        int count = 0;
        for (Position p : positions) {
            if (p.x < 0 || p.y < 0 || p.x > maze.configuration().maze_size.x - 1 || p.y > maze.configuration().maze_size.y - 1) {
                continue;
            }
            if (maze.getOrDefault(p,new Tile('w',0)).type != 'w') {
                count++;
            }
        }
        return count == 1;
    }
}
