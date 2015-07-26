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
    
    public GameState(Node rootNode, WorldManager world, BulletAppState physics) {
        this.m_root = rootNode;
        m_worldManager = world;
        m_physics = physics;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        //initialize members
        this.m_app = app;
        this.m_stateManager = stateManager;
        
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
        m_worldManager.onUpdate(tpf);
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
        
        //init level and game data
        m_worldManager.setPhysicsSpace(m_physics.getPhysicsSpace());
        m_worldManager.freshLevel();
        m_worldManager.spawnFreshPlayer();
        m_root.attachChild(m_worldManager.getWholeWorld());
        m_app.getViewPort().addProcessor(m_worldManager.getFilters());
    }
    
    /**
     * disables the gamestate
     */
    public void disable() {
        //detach all level related data from scenegraph
        m_root.detachChild(m_worldManager.getWholeWorld());
        m_app.getViewPort().removeProcessor(m_worldManager.getFilters());
        m_worldManager.setPaused(true);
    }
}
