/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.loading;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Node;
import de.agame.appstates.MainMenuState;
import de.agame.world.DayTimeManager;

/**
 *
 * @author Fredie
 */
public class MainMenuLoader extends LoadingTask{

    private Application m_app;
    private Node m_root;
    
    private NiftyJmeDisplay m_gui;
    private DayTimeManager m_daytime;
    private Node m_content;
    private FilterPostProcessor m_processor;
    
    public MainMenuLoader(Application app, Node root, NiftyJmeDisplay gui) {
        m_app = app;
        m_root = root;
        m_content = new Node();
        m_gui = gui;
    }
    
    @Override
    protected void load() {
        //load background scene
        m_daytime = new DayTimeManager(m_app.getAssetManager());
        setProgress(0.2f);
        
        m_content.addLight(m_daytime.getAmbient());
        setProgress(0.4f);
        m_content.addLight(m_daytime.getSun());
        setProgress(0.5f);
        m_content.addLight(m_daytime.getMoon());
        setProgress(0.6f);
        m_content.attachChild(m_daytime.getSkyBox());
        setProgress(0.7f);
        
        m_content.attachChild(m_app.getAssetManager().loadModel("Scenes/mainmenu/mainmenu.j3o"));
        setProgress(0.8f);
        
        m_processor = m_app.getAssetManager().loadFilter("Scenes/mainmenu/MainmenuFilters.j3f");
        setProgress(0.9f);
        m_processor.addFilter(m_daytime.getSunShadows());
        setProgress(1.0f);
    }

    @Override
    public AbstractAppState getPreparedFollowUpState() {
        return new MainMenuState(m_root, m_content, m_processor, m_daytime, m_gui);
    }

    @Override
    public void cleanup() {
        m_app = null;
        m_content = null;
        m_daytime = null;
        m_gui = null;
        m_processor = null;
        m_root = null;
    }
    
}
