package resources.implemented;

import input.Action;
import input.Context;
import input.Range;
import input.State;
import resources.Resource;
import resources.ResourceLoader;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Jan-Frederik Leißner on 27.03.2018.
 */
public class ContextResource extends Resource<Context> {

    String name;

    public ContextResource(String name) {
        super("context", name);
        this.name = name;
    }


    @Override
    public Context get_as_object() {
        Context c = new Context(name);
        try {
            Scanner scanner = new Scanner(get_as_file());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }
                String[] equal_split = line.split("=");
                Integer key_code = Integer.valueOf(equal_split[0]);
                String[] colon_split = equal_split[1].split(":");
                if (colon_split[0].equals("action")) {
                    c.actions.put(key_code, new Action(colon_split[1]));
                } else if (colon_split[0].equals("state")) {
                    c.states.put(key_code, new State(colon_split[1]));
                } else if (colon_split[0].equals("range")) {
                    c.ranges.put(key_code, new Range(colon_split[1], 1));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return c;
    }

}
