package graphic;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.apache.commons.io.output.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jan-Frederik Leißner on 28.03.2018.
 */
public class Image {

    private BufferedImage buffered;
    private InputStream input_stream;
    private PNGDecoder decoded_png;
    private File image_file;

    public Image(File image_file) {
        this.image_file = image_file;
    }

    public BufferedImage as_buffered_image() throws IOException {
        if (buffered == null) {
            buffered = ImageIO.read(image_file);
        }
        return buffered;
    }

    public InputStream as_input_stream() throws IOException {
        if (input_stream == null) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(as_buffered_image(), "png", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());
            os.close();
            fis.close();
        }
        return input_stream;
    }

    public PNGDecoder as_png_decoded() throws IOException {
        if (decoded_png == null) {
            decoded_png = new PNGDecoder(as_input_stream());
        }
        return decoded_png;
    }

    public void reset(){
        buffered = null;
        decoded_png = null;
        try {
            input_stream.close();
            input_stream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
