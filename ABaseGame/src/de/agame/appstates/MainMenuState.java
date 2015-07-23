/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Node;
import de.agame.world.DayTimeManager;
import de.lessvoid.nifty.Nifty;

/**
 *
 * @author Fredie
 */
public class MainMenuState extends AbstractAppState {
    
    private Application m_app;
    private NiftyJmeDisplay m_gui;
    
    private Node m_root;
    private FlyByCamera m_cam;
    
    private DayTimeManager m_daytime;
    private FilterPostProcessor m_processor;
    
    public MainMenuState(Node root, FlyByCamera cam) {
        m_root = root;
        m_cam = cam;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        //display gui
        m_gui = new NiftyJmeDisplay(app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        m_app = app;
        
        Nifty nifty = m_gui.getNifty();
        nifty.fromXml("UserInterface/MainMenu/MainMenu.xml", "home");
        
        m_app.getGuiViewPort().addProcessor(m_gui);
        m_app.getInputManager().setCursorVisible(true);

        //display background scene
        m_cam.setEnabled(false);
        
        m_app.getViewPort().getCamera().setLocation(new Vector3f(-100, 60, 100));
        m_app.getViewPort().getCamera().lookAt(new Vector3f(0, 10, 0), Vector3f.UNIT_Y);
        
        m_root.attachChild(m_app.getAssetManager().loadModel("Scenes/mainmenu/mainmenu.j3o"));
        
        m_daytime = new DayTimeManager(m_app.getAssetManager());
        
        m_root.addLight(m_daytime.getAmbient());
        m_root.addLight(m_daytime.getSun());
        m_root.addLight(m_daytime.getMoon());
        
        m_root.attachChild(m_daytime.getSkyBox());
        
        m_processor = m_app.getAssetManager().loadFilter("Scenes/mainmenu/MainmenuFilters.j3f");
        m_processor.addFilter(m_daytime.getSunShadows());
        
        m_app.getViewPort().addProcessor(m_processor);
    }
    
    public void setEnabled(boolean enabled) {
        if(!enabled) {
            m_app.getInputManager().setCursorVisible(false);
            m_app.getGuiViewPort().removeProcessor(m_gui);
            
            m_app.getViewPort().removeProcessor(m_processor);
            
            m_root.removeLight(m_daytime.getAmbient());
            m_root.removeLight(m_daytime.getSun());
            m_root.removeLight(m_daytime.getMoon());
            
            m_root.detachAllChildren();
        }
    }
    
    @Override
    public void update(float tpf) {
        m_daytime.onUpdate(tpf);
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
    }
}
