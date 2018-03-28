package graphic;

import de.matthiasmann.twl.utils.PNGDecoder.Format;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    private int id;
    private int width;
    private int height;

    public Texture(Image image) throws Exception {
        this.width = image.as_png_decoded().getWidth();
        this.height = image.as_png_decoded().getHeight();
        this.id = -1;
        load_into_opengl(image);
    }

    private void load_into_opengl(Image image) throws IOException {
        ByteBuffer buf = ByteBuffer.allocateDirect(4 * this.width * this.height);
        image.as_png_decoded().decode(buf, this.width * 4, Format.RGBA);
        buf.flip();
        this.id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, this.id);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public int get_width() {
        return this.width;
    }

    public int get_height() {
        return this.height;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int get_id() {
        return id;
    }

    public boolean is_loaded(){
        return id != -1;
    }

    public void cleanup() {
        glDeleteTextures(id);
        id = -1;
    }
}