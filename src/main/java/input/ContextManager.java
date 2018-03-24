package input;

import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class ContextManager {
    Map<String,Context> contexts;

    public ContextManager(){
        contexts = new HashedMap<>();
    }

    public void add(Context context){
        contexts.put(context.get_name(),context);
    }

    public Context get(String name){
        return contexts.get(name);
    }
}
