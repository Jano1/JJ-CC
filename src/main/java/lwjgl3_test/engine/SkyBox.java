package lwjgl3_test.engine;

import lwjgl3_test.engine.graph.Material;
import lwjgl3_test.engine.graph.Mesh;
import lwjgl3_test.engine.graph.OBJLoader;
import lwjgl3_test.engine.graph.Texture;

public class SkyBox extends GameItem {

    public SkyBox(String objModel, String textureFile) throws Exception {
        super();
        Mesh skyBoxMesh = OBJLoader.loadMesh(objModel);
        Texture skyBoxtexture = new Texture(textureFile);
        skyBoxMesh.setMaterial(new Material(skyBoxtexture, 0.0f));
        setMesh(skyBoxMesh);
        setPosition(0, 0, 0);
    }
}
