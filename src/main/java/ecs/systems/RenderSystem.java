package ecs.systems;

import ecs.ID;
import ecs.components.*;
import system.System;

import javax.jws.WebParam;
import java.util.List;

/**
 * Created by Jan-Frederik Leißner on 28.03.2018.
 */
public class RenderSystem extends System {

    public RenderSystem(){
        super(PositionComponent.class, MaterialComponent.class, ModelComponent.class, TextureComponent.class);
    }

    @Override
    public void handle(List<ID> list) {

    }
}
