package main;

import de.matthiasmann.twl.utils.PNGDecoder;
import ecs.Blueprint;
import ecs.ECS;
import ecs.ID;
import ecs.components.*;
import ecs.systems.InputSystem;
import ecs.systems.MovementSystem;
import graphic.Image;
import graphic.Model;
import graphic.Texture;
import org.apache.commons.collections4.map.HashedMap;
import org.joml.Vector3f;
import resource.ResourceLoader;
import resource.implemented.ImageResource;
import resource.implemented.ModelResource;
import system.System;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
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
    Window window;

    public Game(){
        ecs_list = new HashedMap<>();
        to_tick = new ArrayList<>();
        window = new Window("test",100,100,true);
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
        while (running && !window.windowShouldClose()) {
            loops = 0;
            while( java.lang.System.nanoTime() > next_game_tick && loops < max_frameskip) {
                update();
                next_game_tick += skip_ticks;
                loops++;
                ups.tick(false);
            }
            interpolation = (java.lang.System.nanoTime() + skip_ticks - next_game_tick) / skip_ticks;
            render(interpolation);
            fps.tick(false);
        }

    }

    private void render(float interpolation) {
        for(String name : to_tick){
            ecs_list.get(name).render(interpolation);
        }
        window.update();
    }

    private void update() {
        for(String name : to_tick){
            ecs_list.get(name).tick();
        }
    }

    private void init() throws Exception {
        window.init();

        Texture t = new Texture(new ImageResource("grassblock.png").get_as_object());
        Model m = new ModelResource("cube.obj").get_as_object();
        m.load_into_opengl();
        java.lang.System.out.println(m);

        ECS ecs = ECSLoader.test(window);
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

    public void tick(boolean show){
        if(java.lang.System.nanoTime() <=  start+(1000000000)){
            xps++;
        }else{
            if(show){
                java.lang.System.out.println(name+": "+(xps));
            }
            xps = 0;
            start = java.lang.System.nanoTime();
        }
    }
}

class ECSLoader{
    public static ECS test(Window window){

        ECS ecs = new ECS(10000);
        ecs.register_system_group("input",new System[]{new InputSystem(window.getWindowHandle())});
        ecs.register_system_group("movement_1",new System[]{new MovementSystem()});

            ID player = ecs.create_entity(Blueprint.empty_blueprint());
            player.add(new PositionComponent(new Vector3f(1,1,0)));
            player.add(new VelocityComponent(new Vector3f(1,0,0)));
            player.add(new TimeComponent(25));
            player.add(new InputComponent("base.context"));
            player.add(new CameraComponent(70,0.1f,20.0f,true));

        return ecs;
    }
}