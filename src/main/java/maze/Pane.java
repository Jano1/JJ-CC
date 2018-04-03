package maze;

/**
 * Created by Jan on 08.06.2016.
 */
public class Pane {
    private Position min;
    private Position max;
    private Terrain t;

    public Pane(Position min, Terrain t) {
        this.min = min;
        this.max = new Position(min.x + 1, min.y + 1);
        this.t = t;
    }

    @Override
    public String toString() {
        return "Pane{" +
                "min=" + min +
                ", max=" + max +
                ", t=" + t +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pane pane = (Pane) o;

        if (min != null ? !min.equals(pane.min) : pane.min != null) return false;
        if (max != null ? !max.equals(pane.max) : pane.max != null) return false;
        return t == pane.t;

    }

    @Override
    public int hashCode() {
        int result = min != null ? min.hashCode() : 0;
        result = 31 * result + (max != null ? max.hashCode() : 0);
        result = 31 * result + (t != null ? t.hashCode() : 0);
        return result;
    }

    public Terrain getTerrain() {
        return t;
    }

    public Position getMin() {
        return min;
    }

    public float[] min() {
        return new float[]{min.x, min.y, 0f};
    }

    public float[] max() {
        return new float[]{max.x, max.y, 1f};
    }
}
