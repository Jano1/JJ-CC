package resource.implemented;

import graphic.Shader;
import resource.Resource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

/**
 * Created by Jan-Frederik Leißner on 31.03.2018.
 */
public class ShaderResource extends Resource<Shader> {

    public ShaderResource(String type, String name) {
        super("shader/" + type, name);
    }

    @Override
    public Shader get_as_object() throws URISyntaxException, IOException {
        return new Shader(new String(Files.readAllBytes(get_as_file().toPath())));
    }
}
