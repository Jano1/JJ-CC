package input;

import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class Context {
    String name;

    Map<Integer,Action> actions;
    Map<Integer,State> states;
    Map<Integer,Range> ranges;

    public Context(String name,Map<Integer, Action> actions, Map<Integer, State> states, Map<Integer, Range> ranges) {
        this.name = name;
        this.actions = actions;
        this.states = states;
        this.ranges = ranges;
    }

    public Context(String name) {
        this(name,new HashedMap<>(),new HashedMap<>(),new HashedMap<>());
    }

    public String get_name() {
        return name;
    }

    public Action get_action(int code){
        return actions.get(code);
    }

    public State get_state(int code){
        return states.get(code);
    }

    public Range get_range(int code){
        return ranges.get(code);
    }
}
