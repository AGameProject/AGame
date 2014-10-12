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
    
    private Application app;
    private AppStateManager stateManager;
    
    private WorldManager worldManager;
    
    private Node root;
    
    public GameState(Node rootNode) {
        this.root = rootNode;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.app = app;
        this.stateManager = stateManager;
        
        this.worldManager = new WorldManager();
        
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
        worldManager.initialize(app.getAssetManager());
        root.attachChild(worldManager.getWholeWorld());
    }
    
    public void disable() {
        root.detachChild(worldManager.getWholeWorld());
    }
}
