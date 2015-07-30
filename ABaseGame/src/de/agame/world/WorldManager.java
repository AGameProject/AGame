/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.world;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
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
    private MeshProvider m_meshprovider;
    private PhysicsSpace m_physicsspace;
    private EntityManager m_entitymanager;
    private ChaseCamera m_chasecam;
    private boolean m_paused = false;
    private Application m_app;
    private FilterPostProcessor m_filters;
    
    //levelrelated members
    private DayTimeManager m_time;
    private Spatial m_statics;
    private Node m_dynamics;
    private Node m_whole;
    
    private NiftyJmeDisplay m_gui;
    
    public void initialize(LevelIO io, InputManager input, AssetManager assets, MeshProvider meshprovider, EntityManager entitymanager, ChaseCamera chasecam, Application app, DayTimeManager daytime, Node dynamics, Node whole, NiftyJmeDisplay gui, FilterPostProcessor filters) {
        m_io = io;
        m_input = input;
        m_assets = assets;
        m_meshprovider = meshprovider;
        m_entitymanager = entitymanager;
        m_entitymanager.setWorldManager(this);
        m_chasecam = chasecam;
        m_app = app;
        m_time = daytime;
        m_dynamics = dynamics;
        m_whole = whole;
        m_gui = gui;
        
        m_filters = filters;
    }
    
    public void setPhysicsSpace(PhysicsSpace physics) {
        m_physicsspace = physics;
        m_entitymanager.setPhysicsSpace(physics);
    }
    
    public void initialize(AssetManager assets, InputManager inputManager, Camera cam, PhysicsSpace physicsspace, Application app) {
        m_input = inputManager;
        m_assets = assets;
        m_whole = new Node("world");
        m_whole.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        m_dynamics = new Node("dynamics");
        m_physicsspace = physicsspace;
        
        cam.setAxes(Vector3f.UNIT_X, Vector3f.UNIT_Y, Vector3f.UNIT_Z);
        m_chasecam = new ChaseCamera(cam, m_dynamics, m_input);
        m_chasecam.setDragToRotate(false);
        m_chasecam.setInvertVerticalAxis(true);
        m_chasecam.setDefaultDistance(5.0f);
        m_chasecam.setMaxDistance(7.0f);
        m_chasecam.setMinDistance(3.0f);
        m_chasecam.setMinVerticalRotation((float) Math.toRadians(-30.0d));
        
        m_entitymanager = new EntityManager();
//        m_entitymanager.finishInit(m_physicsspace, m_dynamics, m_gui, cam, m_chasecam, m_input, m_meshprovider);
        
        m_app = app;
    }
    
    /**
     * Loads data for a fresh untouched level
     */
    public void freshLevel() {
        //load levelstatics
        m_statics = m_meshprovider.getMesh("MainLevel");
        m_physicsspace.add(m_statics);

        //attach all leveldata to this worlds content
        m_whole.attachChild(m_statics);
        m_whole.attachChild(m_dynamics);
        m_whole.attachChild(m_time.getSkyBox());
        m_whole.addLight(m_time.getSun());
        m_whole.addLight(m_time.getAmbient());
        m_whole.addLight(m_time.getMoon());
    }
    
    /**
     * spawns a new EntityPlayer with a chasecam
     */
    public void spawnFreshPlayer() {
        Vector3f playerSpawnPoint = new Vector3f(0, 30, 0);
        Vector3f companionSpawnPoint;
        companionSpawnPoint = playerSpawnPoint.add((float) (Math.random() * 5f), 0f, (float) (Math.random() * 5f));
        
        Entity e = m_entitymanager.spawnEntityAt("Player", playerSpawnPoint);
        m_chasecam.setSpatial(e.getWrapperSpatial());
        m_chasecam.setSmoothMotion(false);
        m_chasecam.setLookAtOffset(new Vector3f(0, 1.7f, 0));
        
        m_entitymanager.spawnEntityAt("Companion", companionSpawnPoint);
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
        m_chasecam.setDragToRotate(m_paused);
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
        m_entitymanager.doPossibleDespawns();
    }
    
    /**
     * 
     * @return a node containing all data of this world
     */
    public Node getWholeWorld() {
        return m_whole;
    }
    
    public FilterPostProcessor getFilters() {
        return m_filters;
    }
    
    /**
     * @return a node containing the whole gui generated by the worlds contents
     */
    public NiftyJmeDisplay getGuiNode() {
        return m_gui;
    }
}
