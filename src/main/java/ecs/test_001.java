package ecs;

import input.Context;
import input.ContextManager;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class test_001 {
    public static void main(String[] args){
        ContextManager m = new ContextManager();

        Context b = m.get("base");

        System.out.println(b.get_action(GLFW_KEY_W));
        System.out.println(b.get_action(GLFW_KEY_Q));
        System.out.println(b.get_action(GLFW_KEY_E));
        System.out.println(b.get_action(GLFW_KEY_S));
        System.out.println(b.get_action(GLFW_KEY_A));
        System.out.println(b.get_action(GLFW_KEY_D));

        System.out.println(b.get_action(GLFW_KEY_F));

    }
}
