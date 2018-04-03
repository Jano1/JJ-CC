package maze;

import java.util.*;

/**
 * Created by Jan on 08.06.2016.
 */
public class Maze {
    private Tile[][] maze;
    private int[][] regions;
    private Random random;
    private int regionCounter = 0;
    private long seed = 0;

    public Maze(int size, long seed) {
        size = (size % 2 == 0 ? size + 1 : size);
        maze = new Tile[size][size];
        regions = new int[size][size];
        random = new Random(seed);
        this.seed = seed;
    }

    public void init(Terrain standard) {
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze.length; y++) {
                maze[x][y] = new Tile(standard);
                regions[x][y] = regionCounter;
            }
        }
        regionCounter++;
    }

    public void createRooms(RoomPlan plan) {
        for (Room r : plan.getRooms()) {
            for (int x = r.getPos().x; x < r.getPos().x + r.getWidth(); x++) {
                for (int y = r.getPos().y; y < r.getPos().y + r.getHeight(); y++) {
                    setTile(new Position(x, y), Terrain.FLAT, regionCounter);
                }
            }
            regionCounter++;
        }
    }

    public void createPaths() {
        Stack<Position> visitable = getVisitablePositions();
        while (!visitable.isEmpty()) {
            Stack<Position> path = new Stack<>();
            path.push(visitable.pop());
            setTile(path.peek(), Terrain.FLAT, regionCounter);
            while (!path.isEmpty()) {
                Position next = getNextPathPosition(path.peek());
                if (next == null) {
                    path.pop();
                } else {
                    setTile(next, Terrain.FLAT, regionCounter);
                    setTile(new Position(path.peek().x + (next.x - path.peek().x) / 2, path.peek().y + (next.y - path.peek().y) / 2), Terrain.FLAT, regionCounter);
                    path.push(next);
                    visitable.remove(next);
                }
            }
            regionCounter++;
        }
    }

    private void setTile(Position p, Terrain t, int region) {
        maze[p.x][p.y].setTerrain(t);
        regions[p.x][p.y] = region;
    }

    private Position getNextPathPosition(Position current) {
        List<Position> possible = new ArrayList<>();
        possible.add(new Position(current.x + 2, current.y));
        possible.add(new Position(current.x - 2, current.y));
        possible.add(new Position(current.x, current.y + 2));
        possible.add(new Position(current.x, current.y - 2));
        List<Position> sorted = new ArrayList<>();
        for (Position p : possible) {
            if (isPossiblePathPosition(p)) {
                sorted.add(p);
            }
        }
        if (sorted.isEmpty()) {
            return null;
        }
        return sorted.get(random.nextInt(sorted.size()));
    }

    private boolean isPossiblePathPosition(Position p) {
        return (p.x >= 0) && (p.y >= 0) && (p.x < maze.length) && (p.y < maze.length) && (maze[p.x][p.y].getTerrain() == Terrain.WALL);
    }

    private Stack<Position> getVisitablePositions() {
        Stack<Position> visitable = new Stack<>();
        for (int x = 1; x < maze.length; x += 2) {
            for (int y = 1; y < maze.length; y += 2) {
                if (maze[x][y].getTerrain() == Terrain.WALL) {
                    visitable.push(new Position(x, y));
                }
            }
        }
        return visitable;
    }

    @Override
    public String toString() {
        String ret = "";
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze.length; y++) {
                //ret += regions[x][y] + " ";
                Terrain t = maze[x][y].getTerrain();
                if(t == Terrain.WALL){
                    ret += "# ";
                }else if(t == Terrain.FLAT){
                    ret += "  ";
                }else if(t == Terrain.DOOR){
                    ret += "0 ";
                }
            }
            ret += "\n";
        }
        return ret;
    }

    public void createDoors() {
        HashMap<String, List<Position>> connections = new HashMap<>();
        int margin = 1;
        for (int x = margin; x < maze.length - margin; x++) {
            for (int y = margin; y < maze.length - margin; y++) {
                if (regions[x][y] == 0) {
                    getLRConnection(connections, x, y);
                    getUDConnection(connections, x, y);
                }
            }
        }
        for (List<Position> possible : connections.values()) {
            Position door = possible.get(random.nextInt(possible.size()));
            maze[door.x][door.y].setTerrain(Terrain.DOOR);
        }
    }

    private void getLRConnection(HashMap<String, List<Position>> connections, int x, int y) {
        int l = regions[x - 1][y];
        int r = regions[x + 1][y];
        if (y % 2 == 1) {
            insertConnection(connections, x, y, l, r);
        }
    }

    private void getUDConnection(HashMap<String, List<Position>> connections, int x, int y) {
        int u = regions[x][y + 1];
        int d = regions[x][y - 1];
        if (x % 2 == 1) {
            insertConnection(connections, x, y, u, d);
        }
    }

    private void insertConnection(HashMap<String, List<Position>> connections, int x, int y, int a, int b) {
        if (a != 0 && b != 0 && a != b) {
            String key = a + "_" + b;
            String invKey = b + "_" + a;
            if (connections.containsKey(key)) {
                connections.get(key).add(new Position(x, y));
            } else if (connections.containsKey(invKey)) {
                connections.get(invKey).add(new Position(x, y));
            } else {
                connections.put(key, new ArrayList<>());
                connections.get(key).add(new Position(x, y));
            }
        }
    }

    public void removeDeadEnds(int attempts) {
        int margin = 1;
        for (int a = 0; a < attempts; a++) {
            List<Position> dead_ends = new ArrayList<>();
            int counter = 0;
            for (int x = margin; x < maze.length - margin; x += 2) {
                for (int y = margin; y < maze.length - margin; y += 2) {
                    if (isDeadEnd(x, y)) {
                        dead_ends.add(new Position(x, y));
                        counter++;
                    }
                }
            }
            if (counter < 1) {
                a = attempts;
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
                    setTile(p_sub, Terrain.WALL, 0);
                }
            }
        }
    }

    private boolean isDeadEnd(int x, int y) {
        Position[] positions = new Position[]{
                new Position(x + 1, y),
                new Position(x - 1, y),
                new Position(x, y + 1),
                new Position(x, y - 1)
        };
        int count = 0;
        for (Position p : positions) {
            if (p.x < 0 || p.y < 0 || p.x > maze.length - 1 || p.y > maze.length - 1) {
                continue;
            }
            if (maze[p.x][p.y].getTerrain() != Terrain.WALL) {
                count++;
            }
        }
        return count == 1;
    }

    public Tile[][] getTiles() {
        return maze;
    }

    public long getSeed() {
        return seed;
    }
}
