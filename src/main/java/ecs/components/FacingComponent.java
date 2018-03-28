package ecs.components;

import component.Component;
import org.joml.Vector3f;

/**
 * Created by Jan-Frederik Leißner on 28.03.2018.
 */
public class FacingComponent extends Component<FacingComponent> {

    Vector3f facing_direction;

    public FacingComponent(Vector3f facing_direction) {
        this.facing_direction = facing_direction;
    }

    public Vector3f direction() {
        return facing_direction;
    }

    @Override
    public boolean equal_values(FacingComponent facingComponent) {
        return facingComponent.direction().equals(direction());
    }

    @Override
    public FacingComponent clone() {
        return new FacingComponent(new Vector3f(facing_direction.x, facing_direction.y, facing_direction.z));
    }
}
