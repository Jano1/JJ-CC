package ecs.systems;

import ecs.ID;
import ecs.components.AccelerationComponent;
import ecs.components.PositionComponent;
import ecs.components.TimeComponent;
import ecs.components.VelocityComponent;
import org.joml.Vector3f;
import system.System;

import java.util.List;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class MovementSystem extends System {

    public MovementSystem(){
        super(PositionComponent.class,VelocityComponent.class);
    }

    public void handle(List<ID> to_handle) {
        for (ID single_id : to_handle) {
            PositionComponent position = single_id.get(PositionComponent.class);
            position.snap();

            VelocityComponent velocity = single_id.get(VelocityComponent.class);
            AccelerationComponent acceleration = single_id.get(AccelerationComponent.class);
            boolean has_acceleration = (acceleration != null);

            float delta_t = single_id.get(TimeComponent.class).delta_t();
            position.position.add(velocity.position.mul(delta_t, new Vector3f(0,0,0)));
            position.rotation.add(velocity.rotation.mul(delta_t, new Vector3f(0,0,0)));
            position.scaling.add(velocity.scaling.mul(delta_t, new Vector3f(0,0,0)));

            if(has_acceleration){
                float delta_t2 = delta_t * delta_t;
                position.position.add(acceleration.position.mul(delta_t2, new Vector3f(0,0,0)));
                position.rotation.add(acceleration.rotation.mul(delta_t2, new Vector3f(0,0,0)));
                position.scaling.add(acceleration.scaling.mul(delta_t2, new Vector3f(0,0,0)));
            }
        }
    }
}
