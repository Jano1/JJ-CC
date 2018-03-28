package graphic;

import com.google.gson.Gson;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by Jan-Frederik Leißner on 28.03.2018.
 */
public class Model {

    // Base variables
    String name;
    List<Vector3f> vertices;
    List<Vector2f> textures;
    List<Vector3f> normals;
    List<Face> faces;

    // Calculations for loading in opengl
    float[] vertices_array;
    float[] textures_array;
    float[] normals_array;
    int[] indices_array;

    // OpenGL information
    int vertex_array_id;
    int vertex_buffer_id;
    int vertex_count;
    List<Integer> vertex_buffer_ids;

    public Model(String name) {
        this.name = name;
        vertices = new ArrayList<>();
        textures = new ArrayList<>();
        normals = new ArrayList<>();
        faces = new ArrayList<>();
        vertex_array_id = -1;
        vertex_buffer_id = -1;
        vertex_count = -1;
        vertex_buffer_ids = new ArrayList<>();
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
        if(!is_calculated()){
            calculate_arrays();
        }
    }

    public int vertex_array_id() {
        check_loading();
        return vertex_array_id;
    }

    public int vertex_buffer_id() {
        check_loading();
        return vertex_buffer_id;
    }

    public int vertex_count() {
        check_loading();
        return vertex_count;
    }

    public List<Integer> vertex_buffer_ids() {
        check_loading();
        return vertex_buffer_ids;
    }

    public void check_loading(){
        if(!is_loaded_in_opengl()){
            load_into_opengl();
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
        int posIndex = index_group.position;
        indices.add(posIndex);
        if (index_group.texture_vertice >= 0) {
            Vector2f textCoord = textures.get(index_group.texture_vertice);
            textures_array[posIndex * 2] = textCoord.x;
            textures_array[posIndex * 2 + 1] = 1 - textCoord.y;
        }
        if (index_group.vector_normal >= 0) {
            Vector3f vecNorm = normals.get(index_group.vector_normal);
            normals_array[posIndex * 3] = vecNorm.x;
            normals_array[posIndex * 3 + 1] = vecNorm.y;
            normals_array[posIndex * 3 + 2] = vecNorm.z;
        }
    }

    public void load_into_opengl(){
        FloatBuffer posBuffer = null;
        FloatBuffer textCoordsBuffer = null;
        FloatBuffer vecNormalsBuffer = null;
        IntBuffer indicesBuffer = null;
        try {
            vertex_count = vertices_array().length;
            vertex_array_id = glGenVertexArrays();
            glBindVertexArray(vertex_array_id);

            // Position VBO
            vertex_buffer_id = glGenBuffers();
            vertex_buffer_ids.add(vertex_buffer_id);
            posBuffer = MemoryUtil.memAllocFloat(vertices_array().length);
            posBuffer.put(vertices_array()).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer_id);
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Texture coordinates VBO
            vertex_buffer_id = glGenBuffers();
            vertex_buffer_ids.add(vertex_buffer_id);
            textCoordsBuffer = MemoryUtil.memAllocFloat(textures_array().length);
            textCoordsBuffer.put(textures_array()).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer_id);
            glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

            // Vertex normals VBO
            vertex_buffer_id = glGenBuffers();
            vertex_buffer_ids.add(vertex_buffer_id);
            vecNormalsBuffer = MemoryUtil.memAllocFloat(normals_array().length);
            vecNormalsBuffer.put(normals_array()).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer_id);
            glBufferData(GL_ARRAY_BUFFER, vecNormalsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

            // Index VBO
            vertex_buffer_id = glGenBuffers();
            vertex_buffer_ids.add(vertex_buffer_id);
            indicesBuffer = MemoryUtil.memAllocInt(indices_array().length);
            indicesBuffer.put(indices_array()).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vertex_buffer_id);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (posBuffer != null) {
                MemoryUtil.memFree(posBuffer);
            }
            if (textCoordsBuffer != null) {
                MemoryUtil.memFree(textCoordsBuffer);
            }
            if (vecNormalsBuffer != null) {
                MemoryUtil.memFree(vecNormalsBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

    public boolean is_calculated(){
        return (vertices_array != null && textures_array != null && normals_array != null && indices_array != null);
    }

    public boolean is_loaded_in_opengl(){
        return (vertex_array_id != -1 && vertex_buffer_id != -1 && vertex_count != -1);
    }

    @Override
    public String toString() {
        Gson json = new Gson();
        return json.toJson(this);
    }
}
