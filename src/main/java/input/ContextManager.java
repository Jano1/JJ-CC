package input;

import org.apache.commons.collections4.map.HashedMap;

import java.io.File;
import java.io.FileNotFoundException;
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
        File context_directory = new File("out/production/resources/contexts");
        if(context_directory.isDirectory()){
            for(File context_file : context_directory.listFiles()){
                try {
                    Scanner scanner = new Scanner(context_file);
                    Context to_generate = new Context(context_file.getName().split("\\.")[0]);
                    contexts.put(to_generate.name,to_generate);
                    while(scanner.hasNextLine()){
                        String line = scanner.nextLine();
                        if(line.startsWith("#") || line.isEmpty()){
                            continue;
                        }
                        String[] equal_split = line.split("=");
                        Integer key_code = Integer.valueOf(equal_split[0]);
                        String[] colon_split = equal_split[1].split(":");
                        if(colon_split[0].equals("action")){
                            to_generate.actions.put(key_code,new Action(colon_split[1]));
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Context> contexts() {
        return new ArrayList<>(contexts.values());
    }
}
