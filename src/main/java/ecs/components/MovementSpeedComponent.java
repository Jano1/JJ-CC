package ecs.components;

import component.Component;
import org.joml.Vector3f;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class MovementSpeedComponent extends Component<MovementSpeedComponent> {

    public Vector3f position_speed; // m/s (metre/second)
    public Vector3f rotation_speed; // d/p (degree/second)
    public Vector3f scaling_speed;

    public MovementSpeedComponent(Vector3f position_speed, Vector3f rotation_speed, Vector3f scaling_speed) {
        this.position_speed = position_speed;
        this.rotation_speed = rotation_speed;
        this.scaling_speed = scaling_speed;
    }

    public MovementSpeedComponent(Vector3f position_speed) {
        this(position_speed, new Vector3f(), new Vector3f());
    }

    public MovementSpeedComponent(float position, float rotation, float scaling) {
        this(new Vector3f(position), new Vector3f(rotation), new Vector3f(scaling));
    }

    @Override
    public boolean equal_values(MovementSpeedComponent movementSpeedComponent) {
        return false;
    }

    @Override
    public MovementSpeedComponent clone() {
        return null;
    }

    public void clear() {
        position_speed.mul(0);
        rotation_speed.mul(0);
        scaling_speed.mul(0);
    }
}
