package de.turnlane.testing;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.MotionState;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.LodControl;
import de.agame.controls.BetterLodControl;

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
    
    private LodControl createLodControl() {
        LodControl lc = new BetterLodControl();
        lc.setDistTolerance(10f);
        lc.setTrisPerPixel(0.02f);
        lc.setEnabled(true);
        
        return lc;
    }
    
    
    @Override
    public void simpleInitApp() {       
        BulletAppState physics = new BulletAppState();
        stateManager.attach(physics);
        
        terrain = assetManager.loadModel("Scenes/testing/world.j3o");
        RigidBodyControl terrainshape = new RigidBodyControl(CollisionShapeFactory.createMeshShape(terrain), 0);
        terrainshape.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
        terrain.addControl(terrainshape);
        
        rootNode.attachChild(terrain);
        physics.getPhysicsSpace().add(terrain);
        
        flyCam.setMoveSpeed(10);

        Vector2f forestBounds = new Vector2f(200f,200f);
        
        Node[] treeModels = new Node[2];
        boolean optimized = true;
        
        if(optimized) {
            treeModels[0] = (Node) loadModel("vegetation/Dreiastbaum/optimiert/Dreiastbaum_1");
            treeModels[0].getChild("Cube1").addControl(createLodControl());
            treeModels[0].getChild("Cube2").addControl(createLodControl());
            
            treeModels[1] = (Node) loadModel("vegetation/Dreiastbaum/optimiert/Dreiastbaum_2");
            treeModels[1].getChild("Cube.0011").addControl(createLodControl());
            treeModels[1].getChild("Cube.0012").addControl(createLodControl());
        } else {
            treeModels[0] = (Node) loadModel("vegetation/Dreiastbaum/Dreiastbaum_1");
            treeModels[1] = (Node) loadModel("vegetation/Dreiastbaum/Dreiastbaum_2");
        }
        Node trees = new SpatialDistributor(forestBounds,0.0625f,treeModels).distribute(physics.getPhysicsSpace());
        
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
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}