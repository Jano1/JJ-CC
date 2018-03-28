package lwjgl3_test.game;

import lwjgl3_test.engine.graph.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import static org.lwjgl.glfw.GLFW.*;
import lwjgl3_test.engine.IGameLogic;
import lwjgl3_test.engine.MouseInput;
import lwjgl3_test.engine.Scene;
import lwjgl3_test.engine.SceneLight;
import lwjgl3_test.engine.Window;
import lwjgl3_test.engine.GameItem;
//import lwjgl3_test.engine.Terrain;

public class DummyGame implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final Camera camera;

    private Scene scene;

    private Hud hud;

    private static final float CAMERA_POS_STEP = 0.05f;

//    private Terrain terrain;

    private GameItem cubeGameItem;
    
    private float angleInc;
            
    private float lightAngle;

    public DummyGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
        angleInc = 0;
        lightAngle = 90;
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        scene = new Scene();

        float reflectance = 1f;
//        Mesh mesh = OBJLoader.loadMesh("/models/cube.obj");
//        Texture texture = new Texture("/textures/grassblock.png");
//        Material material = new Material(texture, reflectance);
//        mesh.setMaterial(material);
//
////        GameItem gameItem = new GameItem(mesh);
////        scene.setGameItem(gameItem);
////        gameItem.setPosition(0, 0, 0);
//
//        float blockScale = 0.5f;
//        float skyBoxScale = 50.0f;
//        float extension = 2.0f;
//
//        float startx = extension * (-skyBoxScale + blockScale);
//        float startz = extension * (skyBoxScale - blockScale);
//        float starty = -1.0f;
//        float inc = blockScale * 2;
//
//        float posx = startx;
//        float posz = startz;
//        float incy = 0.0f;
//        int NUM_ROWS = (int)(extension * skyBoxScale * 2 / inc);
//        int NUM_COLS = (int)(extension * skyBoxScale * 2/ inc);
//        GameItem[] gameItems  = new GameItem[NUM_ROWS * NUM_COLS];
//        for(int i=0; i<NUM_ROWS; i++) {
//            for(int j=0; j<NUM_COLS; j++) {
//                GameItem gameItem = new GameItem(mesh);
//                gameItem.setScale(blockScale);
////                incy = Math.random() > 0.9f ? blockScale * 2 : 0f;
//                incy = 0;
//                gameItem.setPosition(posx, starty + incy, posz);
//                gameItems[i*NUM_COLS + j] = gameItem;
//
//                posx += inc;
//            }
//            posx = startx;
//            posz -= inc;
//        }
//        scene.setGameItems(gameItems);

        Mesh cubeMesh = OBJLoader.loadMesh("/models/bunny.obj");
        Material cubeMaterial = new Material(new Vector4f(1, 0, 1, 1), reflectance);
        cubeMesh.setMaterial(cubeMaterial);
        cubeGameItem = new GameItem(cubeMesh);
        cubeGameItem.setPosition(0, 2, 0);
        cubeGameItem.setScale(0.5f);
        scene.setGameItem(cubeGameItem);

        Mesh quadMesh = OBJLoader.loadMesh("/models/plane.obj");
        Material quadMaterial = new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), reflectance);
        quadMesh.setMaterial(quadMaterial);
        GameItem quadGameItem = new GameItem(quadMesh);
        quadGameItem.setPosition(0, -1, 0);
        quadGameItem.setScale(50.0f);

        scene.setGameItems(new GameItem[]{cubeGameItem, quadGameItem});

        // Setup Lights
        setupLights();

        camera.getPosition().z = 2;
        hud = new Hud("");
    }

    private void setupLights() {
        SceneLight sceneLight = new SceneLight();
        scene.setSceneLight(sceneLight);

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
        sceneLight.setSkyBoxLight(new Vector3f(1.0f, 1.0f, 1.0f));

        //Point LIght
        PointLight pointLight = new PointLight(new Vector3f(1,1,1), new Vector3f(0,5,0), 1.0f);
        PointLight[] pointLights = new PointLight[1];
        pointLight.setShadowPosMult(10);
        pointLights[0] = pointLight;
//        sceneLight.setPointLightList(pointLights);

        // Directional Light
        float lightIntensity = 1.0f;
        Vector3f lightDirection = new Vector3f(0, 1, 1);
        DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), lightDirection, lightIntensity);
        directionalLight.setShadowPosMult(5);
        directionalLight.setOrthoCords(-5.0f, 5.0f, -5.0f, 5.0f, -1.0f, 20.0f);
        sceneLight.setDirectionalLight(directionalLight);     
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -5;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 5;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -5;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 5;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -5;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 5;
        }
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            scene.getSceneLight().getDirectionalLight().getOrthoCoords().bottom += 1f;
            scene.getSceneLight().getDirectionalLight().getOrthoCoords().top += 1f;
        } else if(window.isKeyPressed(GLFW_KEY_DOWN)) {
            scene.getSceneLight().getDirectionalLight().getOrthoCoords().bottom -= 1f;
            scene.getSceneLight().getDirectionalLight().getOrthoCoords().top -= 1f;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            angleInc -= 0.05f;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            angleInc += 0.05f;
        } else {
            angleInc = 0;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        // Update camera based on mouse            
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }

        hud.rotateCompass(camera.getRotation().y);

        // Update camera position
        Vector3f prevPos = new Vector3f(camera.getPosition());
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);
        // Check if there has been a collision. If true, set the y position to
        // the maximum height
//        float height = terrain != null ? terrain.getHeight(camera.getPosition()) : -Float.MAX_VALUE;
//        if (camera.getPosition().y <= height) {
//            camera.setPosition(prevPos.x, prevPos.y, prevPos.z);
//        }

        float rotY = cubeGameItem.getRotation().y;
        rotY += 0.5f;
        if ( rotY >= 360 ) {
            rotY -= 360;
        }
        cubeGameItem.getRotation().y = rotY;
        
        lightAngle += angleInc;
        if ( lightAngle < 0 ) {
            lightAngle = 0;
        } else if (lightAngle > 180 ) {
            lightAngle = 180;
        }
        float zValue = (float)Math.cos(Math.toRadians(lightAngle));
        float yValue = (float)Math.sin(Math.toRadians(lightAngle));
        Vector3f lightDirection = this.scene.getSceneLight().getDirectionalLight().getDirection();
        lightDirection.x = 0;
        lightDirection.y = yValue;
        lightDirection.z = zValue;
        lightDirection.normalize();
        float lightAngle = (float)Math.toDegrees(Math.acos(lightDirection.z));
        hud.setStatusText("LightAngle: " + lightAngle);
    }

    @Override
    public void render(Window window) {
        if (hud != null) {
            hud.updateSize(window);
        }
        renderer.render(window, camera, scene, hud);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        scene.cleanup();
        if (hud != null) {
            hud.cleanup();
        }
    }
}
