package ecs.components;

import component.Component;

/**
 * Created by Jan-Frederik Leißner on 28.03.2018.
 */
public class TextureComponent extends Component<TextureComponent> {

    private String image_name;
    private String heightmap_image_name;

    public TextureComponent(String image_name, String heightmap_image_name) {
        this.image_name = image_name;
        this.heightmap_image_name = heightmap_image_name;
    }

    @Override
    public boolean equal_values(TextureComponent textureComponent) {
        return textureComponent.image_name.equals(image_name) && textureComponent.heightmap_image_name.equals(heightmap_image_name);
    }

    @Override
    public TextureComponent clone() {
        return new TextureComponent(image_name, heightmap_image_name);
    }
}
