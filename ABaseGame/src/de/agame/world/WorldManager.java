/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.world;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import de.agame.StaticLocations;
import de.agame.data.LevelIO;

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
    private boolean m_paused = false;
    
    //levelrelated members
    private DayTimeManager m_time;
    private Spatial m_statics;
    private Node m_dynamics;
    private Node m_whole;
    private Spatial m_player;
    
    
    public void initialize(AssetManager assets, InputManager inputManager, PhysicsSpace physicsspace) {
        m_input = inputManager;
        m_assets = assets;
        m_whole = new Node("world");
        m_physicsspace = physicsspace;
    }
    
    /**
     * Loads data for a fresh untouched level
     */
    public void freshLevel() {
        //load levelstatics and prepare dynamics node
        m_statics = m_io.loadStaticWorld(StaticLocations.WORLD_MAIN_LEVEL, m_assets);
        m_dynamics = new Node("dynamics");
        m_time = new DayTimeManager();
        
        //attach all leveldata to this worlds content
        m_whole.attachChild(m_statics);
        m_whole.attachChild(m_dynamics);
        m_whole.addLight(m_time.getSun());
    }
    
    /**
     * loads a fresh unmodified player character
     */
    public void freshPlayer() {
        //create basic character
        Box box = new Box(1, 1.8f, 1);
        Geometry geom = new Geometry("player", box);
        geom.setLocalTranslation(0, 1.8f, 0);
        
        Material mat = new Material(m_assets, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        
        geom.setMaterial(mat);
        
        m_player = geom;
        
        //attach the player to this worlds content
        m_whole.attachChild(m_player);
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
