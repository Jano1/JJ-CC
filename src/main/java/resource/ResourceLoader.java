package resource;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan-Frederik Leißner on 27.03.2018.
 */
public class ResourceLoader {

    public static URL load(String resource) {
        URL url;
        //1. Versuch, ThreadLoader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            url = classLoader.getResource(resource);
            if (url != null) {
                return url;
            }
        }
        //2. Versuch, ClassLoader
        classLoader = System.class.getClassLoader();
        if (classLoader != null) {
            url = classLoader.getResource(resource);
            if (url != null) {
                return url;
            }
        }
        //3. Versuch, ClassPath
        return ClassLoader.getSystemResource(resource);
    }

    public static String[] load_all_names(String type) {
        File directory = new File(load(type).getPath());
        if (directory.isDirectory()) {
            return directory.list();
        }
        return null;
    }

    public static <T extends Resource> List<T> load_all(String type, Class<T> resource_class) {
        List<T> loaded = new ArrayList<>();
        for (String name : load_all_names(type)) {
            Constructor<T> constructor = null;
            try {
                constructor = resource_class.getConstructor(String.class);
                loaded.add(constructor.newInstance(name));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return loaded;
    }
}
