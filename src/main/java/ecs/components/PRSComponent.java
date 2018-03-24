package ecs.components;

import component.HistoricBasedComponent;
import org.joml.Vector3f;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class PRSComponent extends HistoricBasedComponent<PRSComponent> {

    Vector3f position;
    Vector3f rotation;
    Vector3f scaling;

    public PRSComponent(Vector3f position, Vector3f rotation, Vector3f scaling) {
        this.position = position;
        this.rotation = rotation;
        this.scaling = scaling;
    }

    public PRSComponent(Vector3f position){
        this(position,new Vector3f(0,0,0),new Vector3f(0,0,0));
    }

    public PRSComponent(){
        this(new Vector3f(0,0,0));
    }

    @Override
    public PRSComponent absolute() {
        if(has_base()){
            PRSComponent base = base().absolute();
            return new PRSComponent(
                    position.add(base.position),
                    rotation.add(base.rotation),
                    scaling.add(base.scaling)
            );
        }
        return this;
    }

    @Override
    public void reset() {
        if(has_snap()){
            position = snap.position;
            rotation = snap.rotation;
            scaling = snap.scaling;
            snap = null;
        }
    }

    @Override
    public boolean equal_values(PRSComponent PRSComponent) {
        return position.equals(PRSComponent.position) && rotation.equals(PRSComponent.rotation) && scaling.equals(PRSComponent.scaling);
    }

    @Override
    public PRSComponent clone() {
        return new PRSComponent(
                new Vector3f(position.x,position.y,position.z),
                new Vector3f(rotation.x,rotation.y,rotation.z),
                new Vector3f(scaling.x,scaling.y,scaling.z)
        );
    }
}
