package ecs.components;

import component.Component;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class ActingForcesComponent extends Component<ActingForcesComponent> {

    List<Vector3f> forces;

    public ActingForcesComponent(List<Vector3f> forces) {
        this.forces = forces;
    }

    public ActingForcesComponent(){
        this(new ArrayList<>());
    }

    @Override
    public boolean equal_values(ActingForcesComponent actingForcesComponent) {
        if(forces.size()!=actingForcesComponent.forces.size()){
            return false;
        }
        for(Vector3f force : forces){
            if(!actingForcesComponent.forces.contains(force)){
                return false;
            }
        }
        return true;
    }

    @Override
    public ActingForcesComponent clone() {
        return new ActingForcesComponent(new ArrayList<>(forces));
    }
}
