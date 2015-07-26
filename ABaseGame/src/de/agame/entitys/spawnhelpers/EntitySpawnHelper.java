/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.spawnhelpers;

import de.agame.entitys.sets.UserInterfaceSet;
import de.agame.entitys.sets.EnviromentObservationSet;
import com.jme3.math.Vector3f;
import de.agame.entitys.Entity;
import de.agame.world.MeshProvider;

/**
 *
 * @author Fredie
 */
public interface EntitySpawnHelper {
    
    public Entity createFromScratch(MeshProvider meshes, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset);
    
    public void spawnEntityAt(Vector3f spawnpoint, Entity entity);
}
