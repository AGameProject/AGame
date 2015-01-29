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
import java.util.Iterator;

/**
 *
 * @author Fredie
 */
public class EntityCompanion extends EntityCreature {
    
    private Entity m_master = null;
    
    public EntityCompanion(Spatial spatial, SpatialControlSet scset, EnviromentObservationSet esset, UserInterfaceSet uiset) {
        super(spatial, scset, esset, uiset);
    }
    
    private Entity findMaster() {
        Iterator<Entity> eiterator = m_enviromentobservationset.getEntityManager().getEntitys();
        while(eiterator.hasNext()) {
            Entity e = eiterator.next();
            if(e instanceof EntityPlayer) {
                return e;
            }
        }
        
        return null;
    }
    
    private Vector3f findNewDestination() {
        float nx = m_random.nextFloat() - 0.5f;
        nx = nx == 0 ? 0.1f : nx;

        float nz = m_random.nextFloat() - 0.5f;
        nz = nz == 0 ? 0.1f : nz;

        Vector3f newdest = new Vector3f(nx, 0, nz);
        newdest.normalizeLocal();
        newdest.multLocal(3 + m_random.nextFloat() * 5.0f);
        newdest.addLocal(m_master.getPosition());
        newdest.setY(0);
        
        return newdest;
    }
    
    @Override
    public void complexAIUpdate(float elapse) {
        super.complexAIUpdate(elapse);
        
        if(m_master == null) {
            m_master = findMaster();
        } else if(reachedDestination() && m_random.nextFloat() < 0.01f) {
            walkTo(findNewDestination());
        } else if(getDestination() == null) {
            walkTo(findNewDestination());
        } else if(getDestination().distanceSquared(m_master.getPosition()) > 64.0f) {
            walkTo(findNewDestination());
        }
        
        if(m_master != null && getDestination() != null) setSprinting(getDestination().distanceSquared(getPosition()) > 128.0f);
    }
}
