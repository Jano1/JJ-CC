package ecs.components;

import component.Component;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class MovementComponent extends Component<MovementComponent> {


    @Override
    public boolean equal_values(MovementComponent movementComponent) {
        return false;
    }

    @Override
    public MovementComponent clone() {
        return null;
    }
}
