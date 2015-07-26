package de.agame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import de.agame.appstates.LoadingState;
import de.agame.loading.MainMenuLoader;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        //disable flycam
        flyCam.setEnabled(false);
        
        //start loadingscreen
        stateManager.attach(new LoadingState(new MainMenuLoader(this, rootNode)));
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
