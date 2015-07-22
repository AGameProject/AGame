/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.combat;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import de.agame.entitys.Entity;
import de.agame.entitys.EntityCreature;
import de.agame.entitys.animation.AnimRequest;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.sets.EnviromentObservationSet;
import java.util.Iterator;
import java.util.Timer;

/**
 *
 * @author Fredie
 */
public abstract class MeleeAttack extends Attack{

    //the range multiplier of this attack in units
    protected float m_range = 1.0f;
    
    //the angle this attack covers (refers to the dotproduct) 
    protected float m_hemi = 0.5f;
    
    private EnviromentObservationSet m_enviroment;
    private EntityCreature m_attacker;
    
    public MeleeAttack(String tag, AnimationProvider provider) {
        super(tag, provider);
    }
    
    public void scheduleHitTimers(long attackmillis) {
        float range = m_range;
        if(getHeldItem() != null) range *= getHeldItem().getRange();
        
        Iterator<Entity> entitys = m_enviroment.getEntityManager().getEntitys();
        while(entitys.hasNext()) {
            Entity entity = entitys.next();
            if(entity == m_attacker) continue;
            
            if(entity instanceof EntityCreature){
                EntityCreature creature = (EntityCreature) entity;
                Vector3f relative = creature.getPosition().subtract(m_attacker.getPosition());
                relative.setY(0);
                relative.normalizeLocal();
                Vector3f attackdir = m_attacker.getMovementManager().getCurrentViewDirection();
                float fronthemi = relative.dot(attackdir);

                Quaternion rot = new Quaternion();
                rot.fromAngleAxis((float)Math.toRadians(-90.0f), Vector3f.UNIT_Y);

                Vector3f attackleft = rot.mult(attackdir);

                float lefthemi = relative.dot(attackleft);

                if(m_currentAnim.getProperty("HIT_LEFT") != null) {
                    boolean fromleft = (Boolean) m_currentAnim.getProperty("HIT_LEFT");
                    if(fromleft && lefthemi > 0) fronthemi *= -1.0f;
                    if(!fromleft && lefthemi < 0) fronthemi *= -1.0f;
                }

                float time = fronthemi / m_hemi + 1.0f;

                Timer attacktimer = new Timer();
                attacktimer.schedule(new MeleeAttackTask(creature, m_attacker, getHeldItem(), range, m_hemi), (long) (attackmillis * time));
            }
        }
    }
    
    @Override
    public AnimRequest executeCombo(int combo,  EntityCreature attacker, EnviromentObservationSet enviroment) {
        //cache the enviroment
        m_enviroment = enviroment;
        
        //cache the attacker
        m_attacker = attacker;
        
        return null;
    }
    
    @Override
    public void onAnimStart(float duration) {
        super.onAnimStart(duration);
        
        
        scheduleHitTimers((long) duration * 500);
    }
}
