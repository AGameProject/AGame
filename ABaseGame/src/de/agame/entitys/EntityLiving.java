/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import de.agame.entitys.sets.UserInterfaceSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.EnviromentObservationSet;
import com.jme3.scene.Spatial;
import de.agame.entitys.movement.MovementManager;

/**
 * BaseClass for all AI driven Characters in the Game
 * @author Fredie
 */
public abstract class EntityLiving extends Entity{

    /**
     * how many complex updates per second are executed
     **/ 
    public static final int COMPLEX_UPDATES_PER_SECOND = 10;
    
    //calculate time between complex updates
    private float m_complexElapse = 1.0f / (float) COMPLEX_UPDATES_PER_SECOND;
    private float m_accu = 0;
    
    protected MovementManager m_movementManager;
    
    public EntityLiving(Spatial spatial, SpatialControlSet spatialcontrolset, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset) {
        super(spatial, spatialcontrolset, enviromentobservationset, userinterfaceset);
        
        m_movementManager = new MovementManager(getSpatial(), this);
        m_movementManager.lockParams();
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        super.controlUpdate(tpf);
        
        if(m_enviromentobservationset.getWorldManager().isPaused()) return;
        
        //accumulate time passed since the last complex update sceduled
        m_accu += tpf;
        
        //do complex update only if necessary
        if(m_accu >= m_complexElapse) {
            m_accu -= m_complexElapse;
            complexAIUpdate(m_complexElapse);
        }
        
        //always do simple update
        simpleUpdate(tpf);
    }
    
    /**
     * updates behavior of the entity
     * @param elapse the time since last comlpex update
     */
    public abstract void complexAIUpdate(float elapse);
    
    /**
     * updates stuff like animations health etc
     * @param tpf time since last update
     */
    public abstract void simpleUpdate(float tpf);
}
