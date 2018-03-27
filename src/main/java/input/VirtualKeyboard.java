package input;

import ecs.systems.InputSystem;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class VirtualKeyboard {
    private final int KEYBOARD_SIZE = 512;

    private int[] keyStates = new int[KEYBOARD_SIZE];
    private boolean[] activeKeys = new boolean[KEYBOARD_SIZE];

    long window;

    protected GLFWKeyCallback keyboard = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            activeKeys[key] = action != GLFW_RELEASE;
            keyStates[key] = action;
        }
    };

    public VirtualKeyboard(long window) {
        this.window = window;
        GLFW.glfwSetKeyCallback(window,keyboard);
    }

    public void reset() {
        for (int i = 0; i < keyStates.length; i++) {
            keyStates[i] = InputSystem.NO_STATE;
        }
    }

    public boolean down(int key) {
        return activeKeys[key];
    }

    public boolean pressed(int key) {
        return keyStates[key] == GLFW_PRESS;
    }

    public boolean released(int key) {
        return keyStates[key] == GLFW_RELEASE;
    }
}
