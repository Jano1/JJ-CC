package maze;

/**
 * Created by Jan on 08.06.2016.
 */
public class Tile {
    private Terrain terrain;

    public Tile(Terrain terrain) {
        this.terrain = terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
