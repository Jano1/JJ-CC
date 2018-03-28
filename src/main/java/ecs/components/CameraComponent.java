package ecs.components;

import component.Component;
import org.joml.Vector3f;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class CameraComponent extends Component<CameraComponent> {

    float field_of_view;
    float distance_near;
    float distance_far;
    boolean active;

    public CameraComponent(float field_of_view, float distance_near, float distance_far, boolean active) {
        this.field_of_view = field_of_view;
        this.distance_near = distance_near;
        this.distance_far = distance_far;
        this.active = active;
    }

    public boolean is_active() {
        return active;
    }

    @Override
    public boolean equal_values(CameraComponent cameraComponent) {
        return false;
    }

    @Override
    public CameraComponent clone() {
        return new CameraComponent(field_of_view,distance_near,distance_far,active);
    }
}
