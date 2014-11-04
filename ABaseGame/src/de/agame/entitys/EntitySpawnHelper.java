/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;

/**
 *
 * @author Fredie
 */
public interface EntitySpawnHelper {
    
    public Entity createFromScratch(AssetManager assets, EnviromentObservationSet enviromentobservationset);
    
    public void spawnEntityAt(Vector3f spawnpoint, Entity entity, InputManager input);
}
