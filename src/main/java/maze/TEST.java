package maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Jan on 08.06.2016.
 */
public class TEST {
    public static void main(String[] args) {
        int size = 200;
        //long seed = 123456;
        long seed = 0;
        Maze m = new Maze(size, seed);

        m.init(Terrain.WALL);
        //System.out.println(m);

        RoomPlan p = new RoomPlan(size);
        p.createRandomRooms(1000, seed, 3, 9);
        m.createRooms(p);
        //System.out.println(m);

        m.createPaths();
        //System.out.println(m);

        m.createDoors();
        //System.out.println(m);

        m.removeDeadEnds(100);
        //System.out.println(m);

        draw(m);
    }

    private static void drawVisible(List<Pane> panes, Maze m, double i, double v) {
        int factor = 10;
        int drawSize = 3 * factor;
        BufferedImage off_Image = new BufferedImage(m.getTiles().length * drawSize, m.getTiles().length * drawSize, BufferedImage.TYPE_INT_ARGB);
        Graphics g = off_Image.createGraphics();
        for (Pane p : panes) {
            if (p.getTerrain() == Terrain.FLAT) {
                g.setColor(Color.WHITE);
            }
            if (p.getTerrain() == Terrain.DOOR) {
                g.setColor(Color.LIGHT_GRAY);
            }
            if (p.getTerrain() == Terrain.WALL) {
                g.setColor(Color.BLACK);
            }
            g.drawRect(p.getMin().x * drawSize, p.getMin().y * drawSize, drawSize - 1, drawSize - 1);
            g.fillRect(p.getMin().x * drawSize, p.getMin().y * drawSize, drawSize - 1, drawSize - 1);
        }
        g.setColor(Color.GREEN);
        g.drawLine((int) (i * drawSize), (int) (v * drawSize), (int) (i * drawSize), (int) (v * drawSize));
        File output = new File("visible_maze_size-" + m.getTiles().length + "_seed-" + m.getSeed() + ".png");
        try {
            ImageIO.write(off_Image, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        File output = new File("maze_size-" + m.getTiles().length + "_seed-" + m.getSeed() + ".png");
        try {
            ImageIO.write(off_Image, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
