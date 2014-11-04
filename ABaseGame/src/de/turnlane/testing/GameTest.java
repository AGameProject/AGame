package de.turnlane.testing;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Ein Test mit meinem Baum. Generiert einen grünen Wald.
 * 
 * @author TheLaseLeo
 */
public class GameTest extends SimpleApplication {

    private Spatial terrain;
    
    public static void main(String[] args) {
        GameTest app = new GameTest();
        app.start();
    }

    
    private Spatial loadModel(String modelPath){
        Spatial model = assetManager.loadModel("Models/"+modelPath+".j3o");
        
        return model;
    }
    
    
    @Override
    public void simpleInitApp() {       
        terrain = assetManager.loadModel("Scenes/world.j3o");
        rootNode.attachChild(terrain);

        Vector2f forestBounds = new Vector2f(200f,200f);
        
        Spatial[] treeModels = new Spatial[2];
        treeModels[0] = loadModel("vegetation/Dreiastbaum/Dreiastbaum_1");
        treeModels[1] = loadModel("vegetation/Dreiastbaum/Dreiastbaum_2");
        Node trees = new SpatialDistributor(forestBounds,0.25f,treeModels).distribute();
        rootNode.attachChild(trees);
        
        // Spatial windmühle = loadModel("buildings/Windmühle");
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.1f,0.1f,0.1f, 1f));

        rootNode.addLight(ambient);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}