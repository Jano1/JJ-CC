package main;

import ecs.ECS;

/**
 * Created by Jan-Frederik Leißner on 27.03.2018.
 */
public class Game implements Runnable{

    public static final int TARGET_FPS = 75;

    public static final int TARGET_UPS = 30;

    private ECS ecs;

    @Override
    public void run() {
        try {
            loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void loop() {
        int ticks_per_second = 25;
        int max_frameskip = 5;
        int skip_ticks = 1000000000/ticks_per_second;

        long next_game_tick = System.nanoTime();

        int loops = 0;
        float interpolation = 1f;

        boolean running = true;
        while (running) {
            input();
            loops = 0;
            while( System.nanoTime() > next_game_tick && loops < max_frameskip) {
                update();
                next_game_tick += skip_ticks;
                loops++;
            }
            interpolation = (System.nanoTime() + skip_ticks - next_game_tick) / skip_ticks;
            render(interpolation);
        }



    }

    private void input() {
        
    }

    private void update() {

    }

    private void render(float interpolation) {

    }
}
