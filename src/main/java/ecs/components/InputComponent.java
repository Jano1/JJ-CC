package ecs.components;

import component.Component;
import input.Action;
import input.Context;
import input.Range;
import input.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class InputComponent extends Component<InputComponent> {

    public String context_name;
    public List<Action> actions;
    public List<State> states;
    public List<Range> ranges;

    public InputComponent(String context_name){
        this.context_name = context_name;
        actions = new ArrayList<>();
        states = new ArrayList<>();
        ranges = new ArrayList<>();
    }

    @Override
    public boolean equal_values(InputComponent inputComponent) {
        return false;
    }

    @Override
    public InputComponent clone() {
        return null;
    }
}
