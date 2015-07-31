/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.agame.loading.LoadingTask;
import de.agame.nifty.progressbar.ProgressBarControl;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;

/**
 *
 * @author Fredie
 */
public class LoadingState<T extends LoadingTask> extends AbstractAppState{
    
    private Application m_app;
    
    private T m_task;
    private Thread m_thread;
    
    private NiftyJmeDisplay m_gui;
    
    private ProgressBarControl m_loadingbar;
    
    private int m_progressstate = 0;
    
    public LoadingState(T task, NiftyJmeDisplay gui) {
        m_task = task;
        m_gui = gui;
    }
    
    @Override
    public void initialize(AppStateManager states, Application app) {
        m_app = app;
        Nifty nifty = m_gui.getNifty();
        nifty.fromXml("UserInterface/Screens/LoadingScreen.xml", "load");
        
        nifty.gotoScreen("load");
        m_loadingbar = m_gui.getNifty().getScreen("load").findNiftyControl("bar", ProgressBarControl.class);
        m_loadingbar.setProgress(0);
        
        m_thread = new Thread(m_task);
        m_thread.start();
    }
    
    @Override
    public void update(float tpf) {
        if(m_task != null) {
            m_loadingbar.setProgress(m_task.getProgress());

            int prevstate = m_progressstate;
            m_progressstate = (int) (m_loadingbar.getProgress() / 0.2f);

            if(prevstate != m_progressstate) {
                Element e = m_gui.getNifty().getCurrentScreen().findElementByName("background" + (int) (m_progressstate * 20));
                e.setVisible(true);
            }

            if(m_loadingbar.getProgress() >= 1.0f) {
                m_app.getStateManager().detach(this);
                
                AbstractAppState newState = m_task.getPreparedFollowUpState();
                m_task.cleanup();
                
                m_app.getStateManager().attach(newState);
            }
        }
    }
    
    @Override
    public void cleanup() {
        m_task = null;
        m_thread = null;
        m_app = null;
        m_gui = null;
        m_loadingbar = null;
    }
}
