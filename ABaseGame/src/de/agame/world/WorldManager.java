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

/**
 *
 * @author Fredie
 */
public class WorldManager {
    private LevelIO m_io = new LevelIO();
    private InputManager m_input;
    
    private DayTimeManager m_time;
    
    private Spatial m_statics;
    private Node m_dynamics;
    
    private Node m_whole;
    
    
    public void initialize(AssetManager assets, InputManager inputManager) {
        m_input = inputManager;
        m_time = new DayTimeManager();

        m_statics = m_io.loadStaticWorld(StaticLocations.WORLD_MAIN_LEVEL, assets);
        m_dynamics = new Node("dynamics");
        m_whole = new Node("world");
        
        m_whole.attachChild(m_statics);
        m_whole.attachChild(m_dynamics);
        m_whole.addLight(m_time.getSun());
    }
    
    public InputManager getInput() {
        return m_input;
    }
    
    public void onUpdate(float tpf) {
        m_time.onUpdate(tpf);
    }
    
    public Node getWholeWorld() {
        return m_whole;
    }
}
