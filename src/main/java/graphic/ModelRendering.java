package graphic;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

/**
 * Created by Jan-Frederik Leißner on 28.03.2018.
 */
public class ModelRendering {
    public static void render(Model model, Texture texture, int method){
        model.init_render(texture);
        glDrawElements(method, model.vertex_count(), GL_UNSIGNED_INT, 0);
        model.end_render();
    }
    public static void render(Model model, Texture texture){
        render(model,texture,GL_TRIANGLES);
    }
}
