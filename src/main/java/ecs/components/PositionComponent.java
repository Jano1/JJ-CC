package ecs.components;

import component.HistoricBasedComponent;
import org.joml.Vector3f;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class PositionComponent extends HistoricBasedComponent<PositionComponent> {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f scaling;

    public PositionComponent(Vector3f position, Vector3f rotation, Vector3f scaling) {
        this.position = position;
        this.rotation = rotation;
        this.scaling = scaling;
    }

    public PositionComponent(Vector3f position) {
        this(position, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
    }

    public PositionComponent() {
        this(new Vector3f(0, 0, 0));
    }

    @Override
    public PositionComponent absolute() {
        if (has_base()) {
            PositionComponent base = base().absolute();
            return new PositionComponent(
                    position.add(base.position),
                    rotation.add(base.rotation),
                    scaling.add(base.scaling)
            );
        }
        return this;
    }

    @Override
    public void reset() {
        if (has_snap()) {
            position = snap.position;
            rotation = snap.rotation;
            scaling = snap.scaling;
            snap = null;
        }
    }

    @Override
    public boolean equal_values(PositionComponent PositionComponent) {
        return position.equals(PositionComponent.position) && rotation.equals(PositionComponent.rotation) && scaling.equals(PositionComponent.scaling);
    }

    @Override
    public PositionComponent clone() {
        return new PositionComponent(
                new Vector3f(position.x, position.y, position.z),
                new Vector3f(rotation.x, rotation.y, rotation.z),
                new Vector3f(scaling.x, scaling.y, scaling.z)
        );
    }
}
