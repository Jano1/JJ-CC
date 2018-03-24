package ecs.components;

import component.Component;
import org.joml.Vector3f;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class ViewComponent extends Component<ViewComponent> {

    Vector3f target;
    Vector3f up;
    float field_of_view;
    float aspect_ratio;
    float distance_near;
    float distance_far;

    public ViewComponent(Vector3f target, Vector3f up, float field_of_view, float aspect_ratio, float distance_near, float distance_far) {
        this.target = target;
        this.up = up;
        this.field_of_view = field_of_view;
        this.aspect_ratio = aspect_ratio;
        this.distance_near = distance_near;
        this.distance_far = distance_far;
    }

    @Override
    public boolean equal_values(ViewComponent viewComponent) {
        return false;
    }

    @Override
    public ViewComponent clone() {
        return new ViewComponent(target,up,field_of_view,aspect_ratio,distance_near,distance_far);
    }
}
