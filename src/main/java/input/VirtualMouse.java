package input;

import ecs.systems.InputSystem;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class VirtualMouse {
    private final int MOUSE_SIZE = 16;

    private int[] mouseButtonStates = new int[MOUSE_SIZE];
    private boolean[] activeMouseButtons = new boolean[MOUSE_SIZE];
    private long lastMouseNS = 0;
    private long mouseDoubleClickPeriodNS = 1000000000 / 5; //5th of a second for double click.

    protected GLFWMouseButtonCallback mouse = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            activeMouseButtons[button] = action != GLFW_RELEASE;
            mouseButtonStates[button] = action;
        }
    };

    public void reset() {
        for (int i = 0; i < mouseButtonStates.length; i++) {
            mouseButtonStates[i] = InputSystem.NO_STATE;
        }

        long now = java.lang.System.nanoTime();

        if (now - lastMouseNS > mouseDoubleClickPeriodNS)
            lastMouseNS = 0;
    }

    public boolean button_down(int button) {
        return activeMouseButtons[button];
    }

    public boolean button_pressed(int button) {
        return mouseButtonStates[button] == GLFW_RELEASE;
    }

    public boolean button_released(int button) {
        boolean flag = mouseButtonStates[button] == GLFW_RELEASE;

        if (flag)
            lastMouseNS = java.lang.System.nanoTime();

        return flag;
    }

    public boolean button_double_clicked(int button) {
        long last = lastMouseNS;
        boolean flag = button_released(button);

        long now = java.lang.System.nanoTime();

        if (flag && now - last < mouseDoubleClickPeriodNS) {
            lastMouseNS = 0;
            return true;
        }

        return false;
    }
}
