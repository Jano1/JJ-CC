package ecs.systems;

import ecs.ID;
import ecs.components.InputComponent;
import input.Context;
import input.ContextManager;
import input.VirtualKeyboard;
import input.VirtualMouse;
import org.joml.Vector2d;
import system.System;

import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class InputSystem extends System {
    public static final int NO_STATE = -1;
    ContextManager manager;
    VirtualKeyboard keyboard;
    VirtualMouse mouse;

    public InputSystem(long window) {
        super(InputComponent.class);
        manager = new ContextManager();
        keyboard = new VirtualKeyboard(window);
        mouse = new VirtualMouse(window);
        init();
    }

    protected void init() {
        keyboard.reset();
        mouse.reset();
    }

    public void handle(List<ID> to_handle) {
        keyboard.reset();
        mouse.reset();
        glfwPollEvents();
        //Go over all entitys
        for (ID single_id : to_handle) {
            //Clear old input
            InputComponent input_component = single_id.get(InputComponent.class);
            input_component.mouse_position_velocity = mouse.position_velocity().mul(1,new Vector2d());
            mouse.position_velocity().set(0);
            input_component.mouse_scroll_velocity = mouse.scroll_offset_velocity().mul(1,new Vector2d());
            mouse.scroll_offset_velocity().set(0);
            input_component.actions.clear();
            input_component.ranges.clear();
            input_component.states.clear();
            //Load context
            Context context = manager.get(input_component.context_name);
            for (int key_id : context.actions.keySet()) {
                if (keyboard.down(key_id)) {
                    input_component.actions.add(context.get_action(key_id));
                }
            }
            for (int key_id : context.states.keySet()) {
                if (keyboard.down(key_id)) {
                    input_component.states.add(context.get_state(key_id));
                }
            }
            for (int key_id : context.ranges.keySet()) {
                if (keyboard.down(key_id)) {
                    input_component.ranges.add(context.get_range(key_id));
                }
            }
        }

    }
}
