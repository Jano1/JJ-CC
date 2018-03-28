package ecs.components;

import component.Component;
import org.joml.Vector4f;

/**
 * Created by Jan-Frederik Leißner on 28.03.2018.
 */
public class MaterialComponent extends Component<MaterialComponent> {

    public static final Vector4f DEFAULT = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

    private Vector4f ambient_color;
    private Vector4f diffuse_color;
    private Vector4f specular_color;
    private float reflectance;

    public MaterialComponent(Vector4f ambient_color, Vector4f diffuse_color, Vector4f specular_color, float reflectance) {
        this.ambient_color = ambient_color;
        this.diffuse_color = diffuse_color;
        this.specular_color = specular_color;
        this.reflectance = reflectance;
    }

    @Override
    public boolean equal_values(MaterialComponent materialComponent) {
        return false;
    }

    @Override
    public MaterialComponent clone() {
        return new MaterialComponent(ambient_color, diffuse_color, specular_color, reflectance);
    }
}
