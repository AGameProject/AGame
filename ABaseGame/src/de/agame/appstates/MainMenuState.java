/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import de.agame.loading.MainLevelLoader;
import de.agame.nifty.screencontrollers.AbstractUIController;
import de.agame.nifty.screencontrollers.UIListener;
import de.agame.world.DayTimeManager;
import de.lessvoid.nifty.Nifty;

/**
 *
 * @author Fredie
 */
public class MainMenuState extends AbstractAppState implements UIListener{
    
    private Application m_app;
    private NiftyJmeDisplay m_gui;
    
    private Node m_root;
    private Camera m_cam;
    
    private Node m_content;
    private DayTimeManager m_daytime;
    private FilterPostProcessor m_processor;
    
    public MainMenuState(Node root, Node content, FilterPostProcessor processor, DayTimeManager daytime, NiftyJmeDisplay gui) {
        m_root = root;
        m_content = content;
        m_processor = processor;
        m_daytime = daytime;
        m_gui = gui;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        
        //display gui
        m_app = app;
        
        m_cam = m_app.getCamera();
        
        m_gui.getNifty().fromXml("UserInterface/Screens/HomeScreen.xml", "home");
        
        loadScreen("home");
        
        m_app.getGuiViewPort().addProcessor(m_gui);
        m_app.getInputManager().setCursorVisible(true);

        //display background scene
        m_cam.setLocation(new Vector3f(-100, 60, 100));
        m_cam.lookAt(new Vector3f(0, 10, 0), Vector3f.UNIT_Y);
        
        m_root.attachChild(m_content);
        
        m_app.getViewPort().addProcessor(m_processor);
    }
    
    public void loadScreen(String screen) {
        Nifty nifty = m_gui.getNifty();
        nifty.gotoScreen(screen);
        
        AbstractUIController controller = (AbstractUIController) nifty.getScreen(screen).getScreenController();
        controller.setListener(this);
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        if(!enabled && isEnabled()) {
            m_app.getInputManager().setCursorVisible(false);
            
            m_app.getViewPort().removeProcessor(m_processor);
            
            m_root.detachChild(m_content);
            
            m_gui.getNifty().exit();
        } else if(enabled && !isEnabled()) {
            if(isInitialized()) {
                loadScreen("home");
        
                m_app.getInputManager().setCursorVisible(true);
                
                m_app.getViewPort().getCamera().setLocation(new Vector3f(-100, 60, 100));
                m_app.getViewPort().getCamera().lookAt(new Vector3f(0, 10, 0), Vector3f.UNIT_Y);
                
                m_root.attachChild(m_content);
                
                m_app.getViewPort().addProcessor(m_processor);
            } else {
                initialize(m_app.getStateManager(), m_app);
            }
        }
    }
    
    @Override
    public void update(float tpf) {
        m_daytime.onUpdate(tpf);
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        
        m_gui = null;
        m_daytime = null;
        m_root = null;
        m_cam = null;
        m_processor = null;
        m_app = null;
    }

    public void onInteract(String action) {
        if(action.equalsIgnoreCase("play")) playGame();
        else if(action.equalsIgnoreCase("settings"));
        else if(action.equalsIgnoreCase("quit")) quitGame();
    }
    
    public void quitGame() {
        m_app.stop();
    }
    
    public void playGame() {
        Node rootnode = m_root;
        
        this.setEnabled(false);
        m_app.getStateManager().detach(this);
        
        m_app.getStateManager().attach(new LoadingState(new MainLevelLoader(m_app, m_root, m_gui), m_gui));
    }
}
