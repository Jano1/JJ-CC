package ecs.components;

import component.Component;
import input.Action;
import input.Range;
import input.State;
import org.joml.Vector2d;

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
    public Vector2d mouse_position_velocity;
    public Vector2d mouse_scroll_velocity;

    public InputComponent(String context_name) {
        this.context_name = context_name;
        actions = new ArrayList<>();
        states = new ArrayList<>();
        ranges = new ArrayList<>();
        mouse_position_velocity = new Vector2d();
        mouse_scroll_velocity = new Vector2d();
    }

    public void remove_actions(String... names) {
        for (String name : names) {
            actions.remove(new Action(name));
        }
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
