package de.agame.testing;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
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

    public static void main(String[] args) {
        GameTest app = new GameTest();
        app.start();
    }

    
    private Spatial loadModel(String modelPath, String MatPath){
        /** Load a model. Uses model and texture from jme3-test-data library! */ 
        Spatial model = assetManager.loadModel("Models/"+modelPath+".j3o");
        //Material treeMat = assetManager.loadMaterial("Materials/"+path+".j3md");
        
        Material treeMat = assetManager.loadMaterial("MatDefs/"+MatPath+".j3m");
        treeMat.setBoolean("UseMaterialColors", true);
        model.setMaterial(treeMat);
        return model;
    }
    
    
    @Override
    public void simpleInitApp() {
        Spatial tree = loadModel("vegetation/laubbaum_1/laubbaum_1", "vegetation/laubbaum_1/baumstamm");
        
        Node trees = new SpatialDistributor(new Vector2f(100f,100f),0.25f,tree).distribute();
        
        rootNode.attachChild(trees);
        
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