/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import de.agame.world.WorldManager;

/**
 *
 * @author Fredie
 */
public interface Entity {
    
    public Node createFromScratch(AssetManager assets);
    public void readFromNode(Node node);
    public void writeToNode(Node node);
    
    public void onUpdate(float tpf);
    
    public void setWorldManager(WorldManager worldManager);
}
