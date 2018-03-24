package ecs.components;

import component.Component;
import org.joml.Vector3f;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class ForceComponent extends Component<ForceComponent> {

    Vector3f force;

    public ForceComponent(Vector3f force) {
        this.force = force;
    }

    @Override
    public boolean equal_values(ForceComponent forceComponent) {
        return forceComponent.force.equals(force);
    }

    @Override
    public ForceComponent clone() {
        return new ForceComponent(new Vector3f(force.x, force.y, force.z));
    }
}
