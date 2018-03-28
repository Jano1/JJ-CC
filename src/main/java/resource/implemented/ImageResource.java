package resource.implemented;

import graphic.Image;
import resource.Resource;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Created by Jan-Frederik Leißner on 28.03.2018.
 */
public class ImageResource extends Resource<Image>{

    public ImageResource(String name) {
        super("asset/image", name);
    }

    @Override
    public Image get_as_object() throws URISyntaxException {
        return new Image(get_as_file());
    }
}
