/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import de.agame.entitys.sets.EnviromentObservationSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.UserInterfaceSet;

/**
 *
 * @author Fredie
 */
public class EntityCreature extends EntityLivingAnimated {
    
    private Vector3f m_destination = null;
    private boolean m_arrived = true;
    private float m_dtolerance = 3.0f;
    
    public EntityCreature(Spatial spatial, SpatialControlSet scset, EnviromentObservationSet esset, UserInterfaceSet uiset) {
        super(spatial, scset, esset, uiset);
    }
    
    public void walkTo(Vector3f location) {
        location.setY(0);
        m_destination = location;
        m_arrived = false;
    }
    
    public boolean reachedDestination() {
        return m_arrived;
    }
    
    public void setDestinationTolerance(float tolerance) {
        m_dtolerance = tolerance;
    }
    
    public Vector3f getDestination() {
        return m_destination;
    }
    
    @Override
    public void complexAIUpdate(float elapse) {
        super.complexAIUpdate(elapse);
        
        if(!m_arrived) {
            m_arrived = getPosition().distanceSquared(m_destination) < m_dtolerance * m_dtolerance;
        
            if(!m_arrived) {
//                float dst = getPosition().distanceSquared(m_destination);
//                float loose = Math.max(100, dst) / 100.0f * m_random.nextFloat();
                Vector3f dir = m_destination.subtract(m_spatial.getWorldTranslation());
                dir.setY(0);

                setWalkDirection(dir);
            } else {
                setWalkDirection(new Vector3f(0, 0, 0));
            }
        }
    }
}
