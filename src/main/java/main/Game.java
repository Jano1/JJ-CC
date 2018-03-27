package main;

import ecs.Blueprint;
import ecs.ECS;
import ecs.ID;
import ecs.components.*;
import ecs.systems.InputSystem;
import ecs.systems.MovementSystem;
import org.apache.commons.collections4.map.HashedMap;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import system.System;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jan-Frederik Leißner on 27.03.2018.
 */
public class Game implements Runnable{

    public static void main(String[] args){
        Game game = new Game();
        Thread thread = new Thread(game);
        thread.start();
    }

    Map<String,ECS> ecs_list;
    List<String> to_tick;

    public Game(){
        ecs_list = new HashedMap<>();
        to_tick = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            init();
            loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void loop() {
        int ticks_per_second = 25;
        int max_frameskip = 5;
        int skip_ticks = 1000000000/ticks_per_second;

        long next_game_tick = java.lang.System.nanoTime();

        int loops;
        float interpolation;

        XPSCounter ups = new XPSCounter("UPS");
        XPSCounter fps = new XPSCounter("FPS");

        boolean running = true;
        while (running) {
            loops = 0;
            while( java.lang.System.nanoTime() > next_game_tick && loops < max_frameskip) {
                update();
                next_game_tick += skip_ticks;
                loops++;
                ups.tick();
            }
            interpolation = (java.lang.System.nanoTime() + skip_ticks - next_game_tick) / skip_ticks;
            render(interpolation);
            fps.tick();
        }

    }

    private void render(float interpolation) {
        for(String name : to_tick){
            ecs_list.get(name).render(interpolation);
        }
    }

    private void update() {
        for(String name : to_tick){
            ecs_list.get(name).tick();
        }
    }

    private void init() {
        ECS ecs = ECSLoader.test();
        ecs_list.put("play_level",ecs);
        to_tick.add("play_level");
    }
}

class XPSCounter{
    long start;
    int xps;
    String name;

    public XPSCounter(String name,long start){
        this.name = name;
        this.start = start;
        this.xps = 0;
    }

    public XPSCounter(String name){
        this(name,java.lang.System.nanoTime());
    }

    public void tick(){
        if(java.lang.System.nanoTime() <=  start+(1000000000)){
            xps++;
        }else{
            java.lang.System.out.println(name+": "+xps);
            xps = 0;
            start = java.lang.System.nanoTime();
        }
    }
}

class ECSLoader{
    public static ECS test(){
        long window = 1;

        ECS ecs = new ECS(10000);
        ecs.register_system_group("input",new System[]{new InputSystem(window)});
        ecs.register_system_group("movement_1",new System[]{new MovementSystem()});

            ID player = ecs.create_entity(Blueprint.empty_blueprint());
            player.add(new PositionComponent(new Vector3f(1,1,0)));
            player.add(new VelocityComponent(new Vector3f(1,0,0)));
            player.add(new TimeComponent(25));
            player.add(new InputComponent("base.context"));
            player.add(new CameraComponent(new Vector3f(1,1,0),new Vector3f(0,0,1),70,(16/9),0.1f,20.0f,true));
        return ecs;
    }
}