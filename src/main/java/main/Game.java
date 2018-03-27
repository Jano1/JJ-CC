package main;

import ecs.ECS;
import org.apache.commons.collections4.map.HashedMap;

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

    private void init() {
        ecs_list.put("play_level",new ECS(10000));
        to_tick.add("play_level");
    }

    protected void loop() {
        int ticks_per_second = 25;
        int max_frameskip = 5;
        int skip_ticks = 1000000000/ticks_per_second;

        long next_game_tick = System.nanoTime();

        int loops;
        float interpolation;

        XPSCounter ups = new XPSCounter("UPS");
        XPSCounter fps = new XPSCounter("FPS");

        boolean running = true;
        while (running) {
            loops = 0;
            while( System.nanoTime() > next_game_tick && loops < max_frameskip) {
                update();
                next_game_tick += skip_ticks;
                loops++;
                ups.tick();
            }
            interpolation = (System.nanoTime() + skip_ticks - next_game_tick) / skip_ticks;
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
        this(name,System.nanoTime());
    }

    public void tick(){
        if(System.nanoTime() <=  start+(1000000000)){
            xps++;
        }else{
            System.out.println(name+": "+xps);
            xps = 0;
            start = System.nanoTime();
        }
    }
}
