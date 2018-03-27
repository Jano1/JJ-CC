package ecs.components;

import component.BasedComponent;
import ecs.ID;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class TimeComponent extends BasedComponent<TimeComponent>{
    long ticks_since_start;
    int ticks_per_second;
    float delta_t_factor;

    public TimeComponent(long ticks_since_start, int ticks_per_second, float delta_t_factor) {
        this.ticks_since_start = ticks_since_start;
        this.ticks_per_second = ticks_per_second;
        this.delta_t_factor = delta_t_factor;
    }

    public TimeComponent(int ticks_per_second){
        this(0,ticks_per_second,1);
    }

    public TimeComponent(ID base_id, float delta_t_factor){
        this(0,0,delta_t_factor);
        based_on(base_id);
    }

    public float delta_t(){
        return (1f/ticks_per_second)*delta_t_factor;
    }

    @Override
    public TimeComponent absolute() {
        if(has_base()) {
            TimeComponent base = base().absolute();
            return new TimeComponent(
                    ticks_since_start+base.ticks_since_start,
                    ticks_per_second+base.ticks_per_second,
                    delta_t_factor*base.delta_t_factor
            );
        }
        return this;
    }

    @Override
    public boolean equal_values(TimeComponent timeComponent) {
        return timeComponent.ticks_since_start==ticks_since_start && (Float.floatToIntBits(timeComponent.delta_t()) == Float.floatToIntBits(delta_t()));
    }

    @Override
    public TimeComponent clone() {
        return new TimeComponent(ticks_since_start,ticks_per_second,delta_t_factor);
    }
}
