package ecs.components;

import component.BasedComponent;
import org.joml.Vector3f;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class AccelerationComponent extends BasedComponent<AccelerationComponent> {

    Vector3f position; // per second
    Vector3f rotation; // per second
    Vector3f scaling;  // per second

    public AccelerationComponent(Vector3f position, Vector3f rotation, Vector3f scaling) {
        this.position = position;
        this.rotation = rotation;
        this.scaling = scaling;
    }

    public AccelerationComponent(Vector3f position){
        this(position,new Vector3f(0,0,0),new Vector3f(0,0,0));
    }

    public AccelerationComponent(){
        this(new Vector3f(0,0,0));
    }

    @Override
    public AccelerationComponent absolute() {
        if(has_base()){
            AccelerationComponent base = base().absolute();
            return new AccelerationComponent(
                    position.add(base.position),
                    rotation.add(base.rotation),
                    scaling.add(base.scaling)
            );
        }
        return this;
    }

    @Override
    public boolean equal_values(AccelerationComponent accelerationComponent) {
        return position.equals(accelerationComponent.position) && rotation.equals(accelerationComponent.rotation) && scaling.equals(accelerationComponent.scaling);
    }

    @Override
    public AccelerationComponent clone() {
        return new AccelerationComponent(
                new Vector3f(position.x,position.y,position.z),
                new Vector3f(rotation.x,rotation.y,rotation.z),
                new Vector3f(scaling.x,scaling.y,scaling.z)
        );
    }
}
