/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import de.agame.entitys.sets.UserInterfaceSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.EnviromentObservationSet;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.Random;

/**
 *
 * @author Fredie
 */
public class Entity extends AbstractControl{

    public static final float ENTITY_DESPAWN_TIME = 1200;
    
    protected Spatial m_spatial;
    protected SpatialControlSet m_spatialcontrolset;
    protected EnviromentObservationSet m_enviromentobservationset;
    protected UserInterfaceSet m_userinterfaceset;
    
    protected Random m_random = new Random();
    
    protected float m_timeexisted = 0;
    
    public Entity(Spatial spatial, SpatialControlSet spatialcontrolset, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset) {
        m_spatial = spatial;
        m_spatialcontrolset = spatialcontrolset;
        m_enviromentobservationset = enviromentobservationset;
        m_userinterfaceset = userinterfaceset;
    }
    
    public SpatialControlSet getSpatialControl() {
        return m_spatialcontrolset;
    }
    
    public EnviromentObservationSet getObservationSet() {
        return m_enviromentobservationset;
    }
    
    public UserInterfaceSet getUISet() {
        return m_userinterfaceset;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        m_timeexisted += tpf;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //not used yet
    }
    
    public boolean mayDespawn() {
        return m_timeexisted >= ENTITY_DESPAWN_TIME && !canBeSeenBy(null);
    }
    
    public boolean canBeSeenBy(Entity entity) {
        return false;
    }
    
    public boolean canBeSeenFrom(Vector3f point) {
        return false;
    }
    
    public Vector3f getPosition() {
        return m_spatial.getWorldTranslation();
    }
    
    public void teleportTo(Vector3f position) {
        m_spatialcontrolset.getMovementControl().warp(position);
    }
    
    public Spatial getWrapperSpatial() {
        return m_spatial;
    }
    
    public void onAttach() {
        m_enviromentobservationset.getPhysicsSpace().addAll(m_spatial);
        m_enviromentobservationset.getPhysicsSpace().add(m_spatialcontrolset.getMovementControl());
        m_enviromentobservationset.getRoot().attachChild(m_spatial);
    }
    
       public void onDetach() {
        m_enviromentobservationset.getPhysicsSpace().removeAll(m_spatial);
        m_enviromentobservationset.getPhysicsSpace().remove(m_spatialcontrolset.getMovementControl());
        m_enviromentobservationset.getRoot().detachChild(m_spatial);
    }
}
