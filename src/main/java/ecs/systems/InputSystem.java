package ecs.systems;

import input.Context;
import input.ContextManager;
import system.System;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class InputSystem extends System{
    ContextManager manager;

    public InputSystem(ContextManager manager){
        this.manager = manager;
    }
}
