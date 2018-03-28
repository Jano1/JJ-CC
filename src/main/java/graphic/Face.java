package graphic;

import java.util.Arrays;

public class Face {

    /**
     * List of idxGroup groups for a face triangle (3 vertices per face).
     */
    private IndexGroup[] indexGroups;

    public Face(String v1, String v2, String v3) {
        indexGroups = new IndexGroup[3];
        // Parse the lines
        indexGroups[0] = parse(v1);
        indexGroups[1] = parse(v2);
        indexGroups[2] = parse(v3);
    }

    private IndexGroup parse(String line) {
        IndexGroup indexGroup = new IndexGroup();

        String[] lineTokens = line.split("/");
        int length = lineTokens.length;
        indexGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
        if (length > 1) {
            // It can be empty if the obj does not define text coords
            String textCoord = lineTokens[1];
            indexGroup.idxTextCoord = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IndexGroup.NO_VALUE;
            if (length > 2) {
                indexGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
            }
        }

        return indexGroup;
    }

    public IndexGroup[] get_face_vertex_indices() {
        return indexGroups;
    }

    @Override
    public String toString() {
        return "Face{" +
                "indexGroups=" + Arrays.toString(indexGroups) +
                '}';
    }
}