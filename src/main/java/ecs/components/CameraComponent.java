package ecs.components;

import component.Component;
import org.joml.Vector3f;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class CameraComponent extends Component<CameraComponent> {

    public Vector3f target;
    public Vector3f up;
    float field_of_view;
    float aspect_ratio;
    float distance_near;
    float distance_far;
    boolean active;

    public CameraComponent(Vector3f target, Vector3f up, float field_of_view, float aspect_ratio, float distance_near, float distance_far, boolean active) {
        this.target = target;
        this.up = up;
        this.field_of_view = field_of_view;
        this.aspect_ratio = aspect_ratio;
        this.distance_near = distance_near;
        this.distance_far = distance_far;
        this.active = active;
    }

    public float height(){
        return (float) (1.0/Math.tan(field_of_view/2.0));
    }

    public float width(){
        return height()/aspect_ratio;
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
        return new CameraComponent(target,up,field_of_view,aspect_ratio,distance_near,distance_far,active);
    }
}
