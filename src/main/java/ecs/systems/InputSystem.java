package ecs.systems;

import input.ContextManager;
import input.VirtualKeyboard;
import input.VirtualMouse;
import system.System;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class InputSystem extends System {
    ContextManager manager;

    VirtualKeyboard keyboard;
    VirtualMouse mouse;

    public static final int NO_STATE = -1;

    public InputSystem(long window){
        manager = new ContextManager();
        keyboard = new VirtualKeyboard(window);
        mouse = new VirtualMouse(window);
        init();
    }

    protected void init() {
        keyboard.reset();
        mouse.reset();
    }

    protected void update() {
        keyboard.reset();
        mouse.reset();
        glfwPollEvents();
    }
}
