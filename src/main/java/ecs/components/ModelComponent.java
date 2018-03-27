package ecs.components;

import component.Component;

import java.io.File;
/**
 * Created by Jan-Frederik Leißner on 24.03.2018.
 */
public class ModelComponent extends Component<ModelComponent> {

    File model_file;

    public ModelComponent(File model_file) {
        this.model_file = model_file;
    }

    @Override
    public boolean equal_values(ModelComponent modelComponent) {
        return modelComponent.model_file.equals(model_file);
    }

    @Override
    public ModelComponent clone() {
        return new ModelComponent(new File(model_file.getPath()));
    }
}
