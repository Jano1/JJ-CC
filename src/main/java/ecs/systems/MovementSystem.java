package ecs.systems;

import ecs.ID;
import ecs.components.*;
import input.Action;
import org.joml.Vector3f;
import system.System;

import java.util.List;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class MovementSystem extends System {

    public MovementSystem() {
        super(PositionComponent.class, VelocityComponent.class, TimeComponent.class);
    }

    public void handle(List<ID> to_handle) {
        for (ID single_id : to_handle) {
            PositionComponent position = single_id.get(PositionComponent.class);
            PositionComponent cloned = position.clone();
            position.snap();

            VelocityComponent velocity_base = single_id.get(VelocityComponent.class);
            VelocityComponent velocity = velocity_base.clone();

            MovementSpeedComponent movement_speed = single_id.get(MovementSpeedComponent.class);
            if (movement_speed != null) {
                InputComponent input = single_id.get(InputComponent.class);
                if (input != null) {
                    double pitch = movement_speed.rotation_speed.y * java.lang.Math.signum(input.mouse_position_velocity.y);
                    double yaw = movement_speed.rotation_speed.x * java.lang.Math.signum(input.mouse_position_velocity.x);
                    double roll = 0;
                    for (Action action : input.actions) {
                        if (action.toString().equals("move_forward")) {
                            velocity.position.add(movement_speed.position_speed.mul(position.facing_vector(), new Vector3f()));
                        } else if (action.toString().equals("move_backward")) {
                            velocity.position.add(movement_speed.position_speed.mul(position.rotated_facing_vector(180), new Vector3f()));
                        } else if (action.toString().equals("move_left")) {
                            velocity.position.add(movement_speed.position_speed.mul(position.rotated_facing_vector(90), new Vector3f()));
                        } else if (action.toString().equals("move_right")) {
                            velocity.position.add(movement_speed.position_speed.mul(position.rotated_facing_vector(270), new Vector3f()));
                        } else if (action.toString().equals("rotate_right")) {
                            roll += movement_speed.rotation_speed.z;
                        } else if (action.toString().equals("rotate_left")) {
                            roll -= movement_speed.rotation_speed.z;
                        }
                    }
                    velocity.rotation.add((float) pitch, (float) yaw, (float) roll);
                }
            }

            AccelerationComponent acceleration = single_id.get(AccelerationComponent.class);
            boolean has_acceleration = (acceleration != null);

            float delta_t = single_id.get(TimeComponent.class).delta_t();
            position.position.add(velocity.position.mul(delta_t, new Vector3f(0, 0, 0)));
            position.rotation.add(velocity.rotation.mul(delta_t, new Vector3f(0, 0, 0)));
            position.scaling.add(velocity.scaling.mul(delta_t, new Vector3f(0, 0, 0)));

            if (has_acceleration) {
                float delta_t2 = delta_t * delta_t;
                position.position.add(acceleration.position.mul(delta_t2, new Vector3f(0, 0, 0)));
                position.rotation.add(acceleration.rotation.mul(delta_t2, new Vector3f(0, 0, 0)));
                position.scaling.add(acceleration.scaling.mul(delta_t2, new Vector3f(0, 0, 0)));
            }

            position.correct_rotation();

            if (!cloned.equal_values(position)) {
                java.lang.System.out.println(position.rotation);
            }
        }
    }
}
