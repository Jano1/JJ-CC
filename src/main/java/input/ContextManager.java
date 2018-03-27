package input;

import org.apache.commons.collections4.map.HashedMap;
import resources.ResourceLoader;
import resources.implemented.ContextResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class ContextManager {
    Map<String,Context> contexts;

    public ContextManager(){
        contexts = new HashedMap<>();
        load();
    }

    public void add(Context context){
        contexts.put(context.get_name(),context);
    }

    public Context get(String name){
        return contexts.get(name);
    }

    private void load(){
        for(ContextResource resource : ResourceLoader.load_all("context",ContextResource.class)){
            contexts.put(resource.get_name(),resource.get_as_object());
        }
    }

    public List<Context> contexts() {
        return new ArrayList<>(contexts.values());
    }

    @Override
    public String toString() {
        return "ContextManager{" +
                "contexts=" + contexts +
                '}';
    }
}
