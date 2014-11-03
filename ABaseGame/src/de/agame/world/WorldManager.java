/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.world;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.agame.StaticLocations;
import de.agame.data.LevelIO;
import de.agame.entitys.Entity;
import de.agame.entitys.EntityManager;

/**
 *
 * @author Fredie
 */
public class WorldManager {
    //not levelrelated members
    private LevelIO m_io = new LevelIO();
    private InputManager m_input;
    private AssetManager m_assets;
    private PhysicsSpace m_physicsspace;
    private EntityManager m_entitymanager;
    private ChaseCamera m_chasecam;
    private boolean m_paused = false;
    
    //levelrelated members
    private DayTimeManager m_time;
    private Spatial m_statics;
    private Node m_dynamics;
    private Node m_whole;
    
    
    public void initialize(AssetManager assets, InputManager inputManager, Camera cam, PhysicsSpace physicsspace) {
        m_input = inputManager;
        m_assets = assets;
        m_whole = new Node("world");
        m_dynamics = new Node("dynamics");
        m_physicsspace = physicsspace;
        m_chasecam = new ChaseCamera(cam, m_dynamics, m_input);
        m_chasecam.setDragToRotate(false);
        m_chasecam.setInvertVerticalAxis(true);
        
        m_entitymanager = new EntityManager();
        m_entitymanager.finishInit(m_physicsspace, m_dynamics, cam, m_chasecam, m_input, m_assets);
    }
    
    /**
     * Loads data for a fresh untouched level
     */
    public void freshLevel() {
        //load levelstatics
        m_statics = m_io.loadStaticWorld(StaticLocations.WORLD_MAIN_LEVEL, m_assets);
        RigidBodyControl scenecontroll = new RigidBodyControl(CollisionShapeFactory.createMeshShape(m_statics), 0);
        m_statics.addControl(scenecontroll);
        m_physicsspace.add(m_statics);
        m_time = new DayTimeManager();
        
        //attach all leveldata to this worlds content
        m_whole.attachChild(m_statics);
        m_whole.attachChild(m_dynamics);
        m_whole.addLight(m_time.getSun());
    }
    
    /**
     * spawns a new EntityPlayer with a chasecam
     */
    public void spawnFreshPlayer() {
        Entity e = m_entitymanager.spawnEntityAt("Player", Vector3f.ZERO);
        m_chasecam.setSpatial(e.getWrapperSpatial());
    }
    
    /**
     * 
     * @return whether this world is paused or not
     */
    public boolean isPaused() {
        return m_paused;
    }
    
    /**
     * 
     * @param flag whether this world should be paused or not
     */
    public void setPaused(boolean flag) {
        m_paused = flag;
    }
    
    /**
     * 
     * @return the inputmanager of the current application
     */
    public InputManager getInput() {
        return m_input;
    }
    
    /**
     * updates the whole world
     * @param tpf the time passed since the last update
     */
    public void onUpdate(float tpf) {
        //only update if not paused
        if(m_paused) return;
        
        m_time.onUpdate(tpf);
    }
    
    /**
     * 
     * @return a node containing all data of this world
     */
    public Node getWholeWorld() {
        return m_whole;
    }
}
