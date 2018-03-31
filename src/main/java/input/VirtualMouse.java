package input;

import ecs.systems.InputSystem;
import org.joml.Vector2d;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class VirtualMouse {
    private final int MOUSE_SIZE = 16;
    long window;
    private int[] mouseButtonStates = new int[MOUSE_SIZE];
    private boolean[] activeMouseButtons = new boolean[MOUSE_SIZE];
    protected GLFWMouseButtonCallback mouse = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            activeMouseButtons[button] = action != GLFW_RELEASE;
            mouseButtonStates[button] = action;
        }
    };
    private long lastMouseNS = 0;
    private long mouseDoubleClickPeriodNS = 1000000000 / 5; //5th of a second for double click.
    private Vector2d position = new Vector2d(0, 0);
    private Vector2d position_velocity = new Vector2d(0, 0);
    private long last_position_measure = 0;
    protected GLFWCursorPosCallback cursor = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double xpos, double ypos) {
            long time = System.nanoTime();
            Vector2d position_difference = new Vector2d(xpos - position.x, ypos - position.y);
            double time_difference = (double)(time - last_position_measure) / 1000000000d;
            position_velocity = position_difference.mul((1.0d/time_difference),new Vector2d());
            position.x = xpos;
            position.y = ypos;
            last_position_measure = time;
        }
    };
    private Vector2d scroll_offset = new Vector2d(0, 0);
    private Vector2d scroll_offset_velocity = new Vector2d(0, 0);
    private long last_scroll_offset_measure = 0;
    protected GLFWScrollCallback wheel = new GLFWScrollCallback() {
        @Override
        public void invoke(long window, double xoffset, double yoffset) {
            long time = System.nanoTime();
            Vector2d scroll_difference = new Vector2d(xoffset - scroll_offset.x, yoffset - scroll_offset.y);
            long time_difference = time - last_scroll_offset_measure;
            scroll_offset_velocity = scroll_difference.mul(1.0 / time_difference);
            scroll_offset.x = xoffset;
            scroll_offset.y = yoffset;
            last_scroll_offset_measure = time;
        }
    };
    private boolean cursor_in_window = true;
    protected GLFWCursorEnterCallback focus = new GLFWCursorEnterCallback() {
        @Override
        public void invoke(long window, boolean entered) {
            cursor_in_window = entered;
        }
    };

    public VirtualMouse(long window) {
        this.window = window;
        GLFW.glfwSetMouseButtonCallback(window, mouse);
        GLFW.glfwSetCursorPosCallback(window, cursor);
        GLFW.glfwSetScrollCallback(window, wheel);
        GLFW.glfwSetCursorEnterCallback(window, focus);
    }

    public void reset() {
        for (int i = 0; i < mouseButtonStates.length; i++) {
            mouseButtonStates[i] = InputSystem.NO_STATE;
        }
        long now = System.nanoTime();
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
            lastMouseNS = System.nanoTime();

        return flag;
    }

    public boolean button_double_clicked(int button) {
        long last = lastMouseNS;
        boolean flag = button_released(button);

        long now = System.nanoTime();

        if (flag && now - last < mouseDoubleClickPeriodNS) {
            lastMouseNS = 0;
            return true;
        }

        return false;
    }

    public Vector2d position() {
        return position;
    }

    public Vector2d scroll_offset() {
        return scroll_offset;
    }

    public boolean cursor_in_window() {
        return cursor_in_window;
    }

    public Vector2d position_velocity() {
        return position_velocity;
    }

    public Vector2d scroll_offset_velocity() {
        return scroll_offset_velocity;
    }
}
