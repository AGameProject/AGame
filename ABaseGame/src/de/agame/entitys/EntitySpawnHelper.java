/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import de.agame.entitys.sets.UserInterfaceSet;
import de.agame.entitys.sets.EnviromentObservationSet;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;

/**
 *
 * @author Fredie
 */
public interface EntitySpawnHelper {
    
    public Entity createFromScratch(AssetManager assets, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset);
    
    public void spawnEntityAt(Vector3f spawnpoint, Entity entity);
}
