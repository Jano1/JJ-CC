package ecs.systems;

import input.ContextManager;
import input.VirtualKeyboard;
import input.VirtualMouse;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import system.System;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class InputSystem extends System {
    ContextManager manager;

    VirtualKeyboard keyboard;
    VirtualMouse mouse;

    private long window;

    public static final int NO_STATE = -1;

    protected void init(long window) {
        this.window = window;
        keyboard.reset();
        mouse.reset();
    }

    protected void update() {
        keyboard.reset();
        mouse.reset();
        glfwPollEvents();
    }
}
