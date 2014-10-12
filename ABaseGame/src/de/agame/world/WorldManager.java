/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.world;

import com.jme3.asset.AssetManager;
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
    
    private Spatial statics;
    private Node dynamics;
    
    private Node whole;
    
    private EntityWorld entitys;
    
    public void initialize(AssetManager assets) {
        statics = io.loadStaticWorld(StaticLocations.WORLD_MAIN_LEVEL, assets);
        dynamics = new Node("dynamics");
        whole = new Node("world");
        
        entitys = new EntityWorld();
    }
    
    public void onUpdate(float tpf) {
        
    }
    
    public Node getWholeWorld() {
        return whole;
    }
}
