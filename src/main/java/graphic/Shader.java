package graphic;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by Jan-Frederik Leißner on 31.03.2018.
 */
public class Shader {

    String content;
    int opengl_id = 0;

    public Shader(String content){
        this.content = content;
    }

    public void load_into_opengl(int shader_type) throws Exception {
        opengl_id = glCreateShader(shader_type);
        if (opengl_id == 0) {
            throw new Exception("Error creating shader. Type: " + shader_type);
        }
        glShaderSource(opengl_id, content);
        glCompileShader(opengl_id);
        if (glGetShaderi(opengl_id, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(opengl_id, 1024));
        }
    }

    public boolean loaded_into_opengl(){
        return opengl_id > 0;
    }

    public int id(){
        return opengl_id;
    }
}
