package level;

import org.joml.Vector2i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jan-Frederik Leißner on 04.04.2018.
 */
public class TEST {
    public static void main(String[] args){
        MazeConfiguration config = new MazeConfiguration(
                new Vector2i(10,30),
                123456,
                500,
                new Vector2i(3,3),
                new Vector2i(7,7),
                100,
                'w'
        );

        Maze m = MazeGenerator.generate(config);
        draw(m);
    }

    private static void draw(Maze m) {
        int drawSize = 3;
        BufferedImage off_Image = new BufferedImage(m.configuration().maze_size.x * drawSize, m.configuration().maze_size.y * drawSize, BufferedImage.TYPE_INT_ARGB);
        Graphics g = off_Image.createGraphics();
        for (int x = 0; x < m.configuration().maze_size.x; x++) {
            for (int y = 0; y < m.configuration().maze_size.y; y++) {
                Tile t = m.getOrDefault(new Position(x,y),new Tile('w',0));
                if (t.type == 'f') {
                    g.setColor(Color.WHITE);
                }
                if (t.type == 'd') {
                    g.setColor(Color.LIGHT_GRAY);
                }
                if (t.type == 'w') {
                    g.setColor(Color.BLACK);
                }
                g.drawRect(x * drawSize, y * drawSize, drawSize - 1, drawSize - 1);
                g.fillRect(x * drawSize, y * drawSize, drawSize - 1, drawSize - 1);
            }
        }
        File output = new File("mazes/maze_" +m.configuration().random_seed+ ".png");
        try {
            ImageIO.write(off_Image, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
