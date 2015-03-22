/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;
import de.agame.world.WorldManager;

/**
 *
 * @author Fredie
 */
public class GameState extends AbstractAppState {
    //process related globals
    private Application m_app;
    private AppStateManager m_stateManager;
    
    //game related globals
    private WorldManager m_worldManager;
    private BulletAppState m_physics;
    
    //the lowest node in the scenegraph
    private Node m_root;
    
    public GameState(Node rootNode) {
        this.m_root = rootNode;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        //initialize members
        this.m_app = app;
        this.m_stateManager = stateManager;
        
        this.m_physics = new BulletAppState();
        
        enable();
    }
    
    @Override
    public void setEnabled(boolean flag) {
        if(flag != isEnabled()) {
            if(flag) enable();
            else disable();
        }
        
        super.setEnabled(flag);
    }
    
    @Override
    public void update(float tpf) {
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        
        if(isEnabled()) disable();
    }
    
    /**
     * does the necessary stuff to enable gamestate
     */
    public void enable() {
        //enable physics
        m_stateManager.attach(m_physics);
        
        //init worldmanager
        this.m_worldManager = new WorldManager();
        m_worldManager.initialize(m_app.getAssetManager(), m_app.getInputManager(), m_app.getCamera(), m_physics.getPhysicsSpace(), m_app);
        
        //init level and game data
        m_worldManager.freshLevel();
        m_worldManager.spawnFreshPlayer();
        m_root.attachChild(m_worldManager.getWholeWorld());
    }
    
    /**
     * disables the gamestate
     */
    public void disable() {
        //detach all level related data from scenegraph
        m_root.detachChild(m_worldManager.getWholeWorld());
        m_worldManager.setPaused(true);
    }
}
