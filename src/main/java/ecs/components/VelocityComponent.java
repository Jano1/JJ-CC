package ecs.components;

import component.BasedComponent;
import org.joml.Vector3f;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class VelocityComponent extends BasedComponent<VelocityComponent> {

    public Vector3f position; // per second
    public Vector3f rotation; // per second
    public Vector3f scaling;  // per second

    public VelocityComponent(Vector3f position, Vector3f rotation, Vector3f scaling) {
        this.position = position;
        this.rotation = rotation;
        this.scaling = scaling;
    }

    public VelocityComponent(Vector3f position){
        this(position,new Vector3f(0,0,0),new Vector3f(0,0,0));
    }

    public VelocityComponent(){
        this(new Vector3f(0,0,0));
    }

    @Override
    public VelocityComponent absolute() {
        if(has_base()){
            VelocityComponent base = base().absolute();
            return new VelocityComponent(
                    position.add(base.position),
                    rotation.add(base.rotation),
                    scaling.add(base.scaling)
            );
        }
        return this;
    }

    @Override
    public boolean equal_values(VelocityComponent velocityComponent) {
        return position.equals(velocityComponent.position) && rotation.equals(velocityComponent.rotation) && scaling.equals(velocityComponent.scaling);
    }

    @Override
    public VelocityComponent clone() {
        return new VelocityComponent(
                new Vector3f(position.x,position.y,position.z),
                new Vector3f(rotation.x,rotation.y,rotation.z),
                new Vector3f(scaling.x,scaling.y,scaling.z)
        );
    }
}
