/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.world;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.agame.StaticLocations;
import de.agame.data.LevelIO;
import de.agame.entitys.EntityWorld;

/**
 *
 * @author Fredie
 */
public class WorldManager {
    private LevelIO io = new LevelIO();
    private InputManager input;
    
    private DayTimeManager time;
    
    private Spatial statics;
    private Node dynamics;
    
    private Node whole;
    
    private EntityWorld entitys;
    
    public void initialize(AssetManager assets, InputManager inputManager) {
        input = inputManager;
        entitys = new EntityWorld();
        time = new DayTimeManager();

        statics = io.loadStaticWorld(StaticLocations.WORLD_MAIN_LEVEL, assets);
        dynamics = new Node("dynamics");
        whole = new Node("world");
        
        whole.attachChild(statics);
        whole.attachChild(dynamics);
        whole.addLight(time.getSun());
    }
    
    public InputManager getInput() {
        return input;
    }
    
    public void onUpdate(float tpf) {
        time.onUpdate(tpf);
    }
    
    public Node getWholeWorld() {
        return whole;
    }
}
