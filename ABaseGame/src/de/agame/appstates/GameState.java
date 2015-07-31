/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import de.agame.loading.MainMenuLoader;
import de.agame.nifty.screencontrollers.AbstractUIController;
import de.agame.nifty.screencontrollers.UIListener;
import de.agame.world.WorldManager;

/**
 *
 * @author Fredie
 */
public class GameState extends AbstractAppState implements UIListener, ActionListener{
    //process related globals
    private Application m_app;
    private AppStateManager m_stateManager;
    
    //game related globals
    private WorldManager m_worldManager;
    private BulletAppState m_physics;
    
    private NiftyJmeDisplay m_gui;
    private boolean m_guiVisible = false;
    
    //the lowest node in the scenegraph
    private Node m_root;
    
    public GameState(Node rootNode, NiftyJmeDisplay gui, WorldManager world, BulletAppState physics) {
        this.m_root = rootNode;
        m_worldManager = world;
        m_physics = physics;
        m_gui = gui;
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
        
        m_gui = null;
        m_app = null;
        m_physics = null;
        m_root = null;
        m_stateManager = null;
        m_worldManager = null;
    }
    
    /**
     * does the necessary stuff to enable gamestate
     */
    public void enable() {
        m_app.getInputManager().addMapping("pause", new KeyTrigger(KeyInput.KEY_ESCAPE));
        m_app.getInputManager().addListener(this, "pause");
        
        //init level and game data
        m_worldManager.setPhysicsSpace(m_physics.getPhysicsSpace());
        m_worldManager.freshLevel();
        m_worldManager.spawnFreshPlayer();
        m_root.attachChild(m_worldManager.getWholeWorld());
        m_app.getViewPort().addProcessor(m_worldManager.getFilters());
        
        m_gui.getNifty().fromXml("UserInterface/Screens/Hud.xml", "hud");
        m_gui.getNifty().addXml("UserInterface/Screens/IngameMenu.xml");
        
        setPaused(false);
    }
    
    /**
     * disables the gamestate
     */
    public void disable() {
        m_app.getInputManager().deleteMapping("pause");
        m_app.getInputManager().removeListener(this);
        
        //detach all level related data from scenegraph
        m_root.detachAllChildren();
        m_app.getViewPort().removeProcessor(m_worldManager.getFilters());
        m_worldManager.setPaused(true);
        m_worldManager.cleanup();
    }
    
    public void setPaused(boolean flag) {
        if(flag) showPause();
        else showHud();
        
        m_worldManager.setPaused(flag);
    }
    
    public void showPause() {
        m_guiVisible = true;
        
        m_gui.getNifty().gotoScreen("pause");
        AbstractUIController controller = (AbstractUIController) m_gui.getNifty().getScreen("pause").getScreenController();
        controller.setListener(this);
        
        m_app.getInputManager().setCursorVisible(true);
    }
    
    public void showHud() {
        m_guiVisible = false;
        
        m_gui.getNifty().gotoScreen("hud");
        AbstractUIController controller = (AbstractUIController) m_gui.getNifty().getScreen("hud").getScreenController();
        controller.setListener(this);
        
        m_app.getInputManager().setCursorVisible(false);
    }

    public void onInteract(String action) {
        if(action.equalsIgnoreCase("continuePlaying")) setPaused(false);
        else if(action.equalsIgnoreCase("settings"));
        else if(action.equalsIgnoreCase("title")) {
            setEnabled(false);
            m_app.getStateManager().detach(this);
            m_app.getStateManager().attach(new LoadingState(new MainMenuLoader(m_app, m_root, m_gui), m_gui));
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equalsIgnoreCase("pause") && isPressed) {
            setPaused(!m_guiVisible);
        }
    }
}
