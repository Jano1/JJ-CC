package graphic;

public class IndexGroup {
    public static final int NO_VALUE = -1;

    public int position;

    public int texture_vertice;

    public int vector_normal;

    public IndexGroup() {
        position = NO_VALUE;
        texture_vertice = NO_VALUE;
        vector_normal = NO_VALUE;
    }

    @Override
    public String toString() {
        return "IndexGroup{" +
                "position=" + position +
                ", texture_vertice=" + texture_vertice +
                ", vector_normal=" + vector_normal +
                '}';
    }
}
