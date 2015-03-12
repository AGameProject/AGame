/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;

/**
 *
 * @author Fredie
 */
public class MainMenuState extends AbstractAppState {
    
    private Application m_app;
    private NiftyJmeDisplay m_gui;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        m_gui = new NiftyJmeDisplay(app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        m_app = app;
    }
    
    public void setEnabled(boolean enabled) {
        if(!enabled) {
            m_app.getInputManager().setCursorVisible(false);
            m_app.getGuiViewPort().removeProcessor(m_gui);
        }
    }
    
    @Override
    public void update(float tpf) {
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
    }
}
