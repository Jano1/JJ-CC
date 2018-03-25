package ecs.systems;

import ecs.ID;
import ecs.components.ForceComponent;
import ecs.components.MassComponent;
import system.System;

import java.util.List;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class GravitySystem extends System {

    public GravitySystem(){
        super(ForceComponent.class, MassComponent.class);
    }

    @Override
    public void handle(List<ID> list) {

    }
}
