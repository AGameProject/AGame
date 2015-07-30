package de.agame;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import de.agame.appstates.LoadingState;
import de.agame.loading.MainMenuLoader;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private NiftyJmeDisplay m_gui;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        //disable quit on escape
        getInputManager().deleteMapping(INPUT_MAPPING_EXIT);
        
        //create gui display
        m_gui = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, viewPort);
        getGuiViewPort().addProcessor(m_gui);
        
        //disable flycam
        flyCam.setEnabled(false);
        
        //start loadingscreen
        stateManager.attach(new LoadingState(new MainMenuLoader(this, rootNode, m_gui), m_gui));
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
