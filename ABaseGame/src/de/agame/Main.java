package de.agame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import de.agame.appstates.GameState;
import de.agame.appstates.MainMenuState;

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
        // enable gamestate for debug purpose
        stateManager.attach(new GameState(rootNode));
//        stateManager.attach(new MainMenuState());
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
