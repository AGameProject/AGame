/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import de.agame.entitys.sets.UserInterfaceSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.EnviromentObservationSet;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import de.agame.entitys.animation.AnimationManager;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.movement.MovementManager;

/**
 *
 * @author Fredie
 */
public class EntityLivingAnimated extends EntityLiving {

    protected MovementManager m_movementManager;
    protected AnimationManager m_animationmanager;
    
    public EntityLivingAnimated(AnimationProvider provider, Spatial spatial, SpatialControlSet scset, EnviromentObservationSet eoset, UserInterfaceSet uiset) {
        super(spatial, scset, eoset, uiset);
        
        m_animationmanager = new AnimationManager(provider);
        m_spatialcontrolset.getAnimationControl().addListener(m_animationmanager);
        
        m_movementManager = new MovementManager(m_animationmanager, getSpatial(), this);
        m_movementManager.setMovementStateChangeListener(m_animationmanager);
        m_movementManager.lockParams();
    }
    
    public MovementManager getMovementManager() {
        return m_movementManager;
    }
    
    public AnimationManager getAnimationManager() {
        return m_animationmanager;
    }
    
    public void setWalkDirection(Vector3f dir) {
        m_movementManager.setMovementDirection(dir);
    }
    
    @Override
    public void complexAIUpdate(float elapse) {
    }

    @Override
    public void simpleUpdate(float tpf) {
        boolean isinAir = !m_spatialcontrolset.getMovementControl().isOnGround();
        
        m_movementManager.setIsInAir(isinAir);
        
        m_movementManager.onUpdate(tpf);
        
        m_spatialcontrolset.getMovementControl().setWalkDirection(m_movementManager.getCurrentWalkDirection());
        m_spatialcontrolset.getMovementControl().setViewDirection(m_movementManager.getCurrentViewDirection());
    }
}
