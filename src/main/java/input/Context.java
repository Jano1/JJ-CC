package input;

import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class Context {
    public static String DO_NOTHING = "do_nothing";
    public Map<Integer, Action> actions;
    public Map<Integer, State> states;
    public Map<Integer, Range> ranges;
    String name;

    public Context(String name, Map<Integer, Action> actions, Map<Integer, State> states, Map<Integer, Range> ranges) {
        this.name = name;
        this.actions = actions;
        this.states = states;
        this.ranges = ranges;
    }

    public Context(String name) {
        this(name, new HashedMap<>(), new HashedMap<>(), new HashedMap<>());
    }

    public String get_name() {
        return name;
    }

    public Action get_action(int code) {
        if (actions.get(code) == null) {
            return new Action(DO_NOTHING);
        }
        return actions.get(code);
    }

    public State get_state(int code) {
        if (states.get(code) == null) {
            return new State(DO_NOTHING);
        }
        return states.get(code);
    }

    public Range get_range(int code) {
        if (ranges.get(code) == null) {
            return new Range(DO_NOTHING, 0f);
        }
        return ranges.get(code);
    }

    @Override
    public String toString() {
        return "Context{" +
                "name='" + name + '\'' +
                ", actions=" + actions +
                ", states=" + states +
                ", ranges=" + ranges +
                '}';
    }
}
