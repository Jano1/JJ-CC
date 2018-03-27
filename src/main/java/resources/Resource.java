package resources;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan-Frederik Leißner on 27.03.2018.
 */
public abstract class Resource<T> {
    String type;
    String name;

    public Resource(String type,String name) {
        this.type = type;
        this.name = name;
    }

    public File get_as_file() throws URISyntaxException {
        return new File(ResourceLoader.load(type+"/"+name).toURI());
    }

    public abstract T get_as_object();

    public String get_type() {
        return type;
    }

    public String get_name() {
        return name;
    }

    @Override
    public String toString() {
        return "Resource["+type+"|"+name+"]";
    }
}
