package ecs.components;

import component.Component;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class MassComponent extends Component<MassComponent> {

    float mass;

    public MassComponent(float mass) {
        this.mass = mass;
    }

    @Override
    public boolean equal_values(MassComponent massComponent) {
        return Float.floatToIntBits(massComponent.mass) == Float.floatToIntBits(mass);
    }

    @Override
    public MassComponent clone() {
        return new MassComponent(mass);
    }
}
