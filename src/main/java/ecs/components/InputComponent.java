package ecs.components;

import component.Component;
import input.Action;
import input.Context;
import input.Range;
import input.State;

import java.util.List;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class InputComponent extends Component<InputComponent> {

    public String context_name;
    public List<Action> actions;
    public List<State> states;
    public List<Range> ranges;

    @Override
    public boolean equal_values(InputComponent inputComponent) {
        return false;
    }

    @Override
    public InputComponent clone() {
        return null;
    }
}
