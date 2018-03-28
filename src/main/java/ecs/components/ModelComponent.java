package ecs.components;

import component.Component;

import java.io.File;
/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class ModelComponent extends Component<ModelComponent> {

    String model_file_name;

    public ModelComponent(String model_file_name) {
        this.model_file_name = model_file_name;
    }

    @Override
    public boolean equal_values(ModelComponent modelComponent) {
        return modelComponent.model_file_name.equals(model_file_name);
    }

    @Override
    public ModelComponent clone() {
        return new ModelComponent(model_file_name);
    }
}
