/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import de.agame.world.WorldManager;

/**
 *
 * @author Fredie
 */
public class GameState extends AbstractAppState {
    
    private Application m_app;
    private AppStateManager m_stateManager;
    
    private WorldManager m_worldManager;
    
    private Node m_root;
    
    public GameState(Node rootNode) {
        this.m_root = rootNode;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.m_app = app;
        this.m_stateManager = stateManager;
        
        this.m_worldManager = new WorldManager();
        
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
    
    public void enable() {
        m_worldManager.initialize(m_app.getAssetManager(), m_app.getInputManager());
        m_root.attachChild(m_worldManager.getWholeWorld());
    }
    
    public void disable() {
        m_root.detachChild(m_worldManager.getWholeWorld());
    }
}
