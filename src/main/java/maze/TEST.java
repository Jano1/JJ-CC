package maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jan on 08.06.2016.
 */
public class TEST {
    public static void main(String[] args) {
        int size = 30;
        long seed = "jano1".hashCode();
        Maze m = new Maze(size, seed);
        m.init(Terrain.WALL);
        RoomPlan p = new RoomPlan(size);
        p.createRandomRooms(100, seed, 3, 9);
        m.createRooms(p);
        m.createPaths();
        m.createDoors();
        m.removeDeadEnds(5);
        draw(m);
    }

    private static void draw(Maze m) {
        int drawSize = 3;
        BufferedImage off_Image = new BufferedImage(m.getTiles().length * drawSize, m.getTiles().length * drawSize, BufferedImage.TYPE_INT_ARGB);
        Graphics g = off_Image.createGraphics();
        Tile[][] tiles = m.getTiles();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                Tile t = tiles[x][y];
                if (t.getTerrain() == Terrain.FLAT) {
                    g.setColor(Color.WHITE);
                }
                if (t.getTerrain() == Terrain.DOOR) {
                    g.setColor(Color.LIGHT_GRAY);
                }
                if (t.getTerrain() == Terrain.WALL) {
                    g.setColor(Color.BLACK);
                }
                g.drawRect(x * drawSize, y * drawSize, drawSize - 1, drawSize - 1);
                g.fillRect(x * drawSize, y * drawSize, drawSize - 1, drawSize - 1);
            }
        }
        File output = new File("mazes/maze_size-" + m.getTiles().length + "_seed-" + m.getSeed() + ".png");
        try {
            ImageIO.write(off_Image, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
