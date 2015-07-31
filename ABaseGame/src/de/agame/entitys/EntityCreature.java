/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import de.agame.item.Item;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.combat.CombatManager;
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
    
    private float m_health = 10.0f;
    
    private float m_strength = 3.0f;
    
    protected CombatManager m_combatManager;
    
    public EntityCreature(AnimationProvider provider, Spatial spatial, SpatialControlSet scset, EnviromentObservationSet esset, UserInterfaceSet uiset) {
        super(provider, spatial, scset, esset, uiset);
        
        m_combatManager = new CombatManager(this, provider, m_animationmanager, esset);
    }
    
    public CombatManager getCombatManager() {
        return m_combatManager;
    }
    
    public float getHealth() {
        return m_health;
    }
    
    public void setHealth(float health) {
        m_health = health;
    }
    
    public float getStrength() {
        return m_strength;
    }
    
    public void setStrength(float strength) {
        m_strength = strength;
    }
    
    public void attack() {
        m_combatManager.attack();
    }
    
    public void block() {
        m_combatManager.block();
    }
    
    public void attackFrom(EntityCreature attacker, Item weapon) {
        m_combatManager.attacked(attacker, weapon);
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
    public void simpleUpdate(float dt) {
        super.simpleUpdate(dt);
        
        m_combatManager.onUpdate(dt);
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

                getMovementManager().setMovementDirection(Vector3f.UNIT_X.mult(1.0f));
                getMovementManager().setViewDirection(dir);
            } else {
                getMovementManager().setMovementDirection(new Vector3f(0, 0, 0));
            }
        }
    }
}
