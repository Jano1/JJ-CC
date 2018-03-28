package graphic;

import com.google.gson.Gson;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jan-Frederik Leißner on 28.03.2018.
 */
public class Model {

    String name;
    List<Vector3f> vertices;
    List<Vector2f> textures;
    List<Vector3f> normals;
    List<Face> faces;

    float[] vertices_array;
    float[] textures_array;
    float[] normals_array;
    int[] indices_array;

    public Model(String name) {
        this.name = name;
        vertices = new ArrayList<>();
        textures = new ArrayList<>();
        normals = new ArrayList<>();
        faces = new ArrayList<>();
    }

    public List<Vector3f> vertices() {
        return vertices;
    }

    public List<Vector2f> textures() {
        return textures;
    }

    public List<Vector3f> normals() {
        return normals;
    }

    public List<Face> faces(){
        return faces;
    }

    public float[] vertices_array() {
        check_calculations();
        return vertices_array;
    }

    public float[] textures_array() {
        check_calculations();
        return textures_array;
    }

    public float[] normals_array() {
        check_calculations();
        return normals_array;
    }

    public int[] indices_array() {
        check_calculations();
        return indices_array;
    }

    private void check_calculations(){
        if(vertices_array == null || textures_array == null || normals_array == null || indices_array == null){
            calculate_arrays();
        }
    }

    public void calculate_arrays(){
        List<Integer> indices = new ArrayList();
        vertices_array = new float[vertices.size() * 3];
        int i = 0;
        for (Vector3f vertice : vertices) {
            vertices_array[i * 3] = vertice.x;
            vertices_array[i * 3 + 1] = vertice.y;
            vertices_array[i * 3 + 2] = vertice.z;
            i++;
        }
        textures_array = new float[vertices.size() * 2];
        normals_array = new float[vertices.size() * 3];
        for (Face face : faces) {
            IndexGroup[] faceVertexIndices = face.get_face_vertex_indices();
            for (IndexGroup indValue : faceVertexIndices) {
                process_face_vertex(indValue, textures, normals, indices, textures_array, normals_array);
            }
        }
        indices_array = new int[indices.size()];
        indices_array = indices.stream().mapToInt((Integer v) -> v).toArray();
    }

    private void process_face_vertex(IndexGroup index_group, List<Vector2f> textures, List<Vector3f> normals, List<Integer> indices, float[] textures_array, float[] normals_array) {
        int posIndex = index_group.idxPos;
        indices.add(posIndex);
        if (index_group.idxTextCoord >= 0) {
            Vector2f textCoord = textures.get(index_group.idxTextCoord);
            textures_array[posIndex * 2] = textCoord.x;
            textures_array[posIndex * 2 + 1] = 1 - textCoord.y;
        }
        if (index_group.idxVecNormal >= 0) {
            Vector3f vecNorm = normals.get(index_group.idxVecNormal);
            normals_array[posIndex * 3] = vecNorm.x;
            normals_array[posIndex * 3 + 1] = vecNorm.y;
            normals_array[posIndex * 3 + 2] = vecNorm.z;
        }
    }

    @Override
    public String toString() {
        Gson json = new Gson();
        return json.toJson(this);
    }
}
