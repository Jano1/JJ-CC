package resource.implemented;

import graphic.Face;
import graphic.Model;
import org.apache.commons.io.FileUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import resource.Resource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Jan-Frederik Leißner on 28.03.2018.
 */
public class ModelResource extends Resource<Model> {


    public ModelResource(String name) {
        super("asset/model", name);
    }

    @Override
    public Model get_as_object() throws URISyntaxException, IOException {
        Model model = new Model(get_name());
        List<String> lines = FileUtils.readLines(get_as_file(), "UTF-8");
        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v":
                    // Geometric vertex
                    Vector3f vec3f = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));
                    model.vertices().add(vec3f);
                    break;
                case "vt":
                    // Texture coordinate
                    Vector2f vec2f = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]));
                    model.textures().add(vec2f);
                    break;
                case "vn":
                    // Vertex normal
                    Vector3f vec3fNorm = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));
                    model.normals().add(vec3fNorm);
                    break;
                case "f":
                    Face face = new Face(tokens[1], tokens[2], tokens[3]);
                    model.faces().add(face);
                    break;
                default:
                    // Ignore other lines
                    break;
            }
        }
        return model;
    }
}