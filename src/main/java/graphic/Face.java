package graphic;

import java.util.Arrays;

public class Face {

    /**
     * List of idxGroup groups for a face triangle (3 vertices per face).
     */
    private IndexGroup[] index_groups;

    public Face(String v1, String v2, String v3) {
        index_groups = new IndexGroup[3];
        // Parse the lines
        index_groups[0] = parse(v1);
        index_groups[1] = parse(v2);
        index_groups[2] = parse(v3);
    }

    private IndexGroup parse(String line) {
        IndexGroup indexGroup = new IndexGroup();
        String[] lineTokens = line.split("/");
        int length = lineTokens.length;
        indexGroup.position = Integer.parseInt(lineTokens[0]) - 1;
        if (length > 1) {
            String textCoord = lineTokens[1];
            indexGroup.texture_vertice = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IndexGroup.NO_VALUE;
            if (length > 2) {
                indexGroup.vector_normal = Integer.parseInt(lineTokens[2]) - 1;
            }
        }
        return indexGroup;
    }

    public IndexGroup[] get_face_vertex_indices() {
        return index_groups;
    }

    @Override
    public String toString() {
        return "Face{" +
                "index_groups=" + Arrays.toString(index_groups) +
                '}';
    }
}