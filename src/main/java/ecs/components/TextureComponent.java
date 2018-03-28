package ecs.components;

import component.Component;

/**
 * Created by Jan-Frederik Leißner on 28.03.2018.
 */
public class TextureComponent extends Component<TextureComponent> {

    private String texture_image_name;

    public TextureComponent(String texture_image_name) {
        this.texture_image_name = texture_image_name;
    }

    @Override
    public boolean equal_values(TextureComponent textureComponent) {
        return textureComponent.texture_image_name.equals(texture_image_name);
    }

    @Override
    public TextureComponent clone() {
        return new TextureComponent(texture_image_name);
    }
}
