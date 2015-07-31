/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.combat;

import com.jme3.math.Vector3f;
import de.agame.item.Item;
import de.agame.entitys.EntityCreature;
import java.util.TimerTask;

/**
 *
 * @author Fredie
 */
public class MeleeAttackTask extends TimerTask{

    private EntityCreature m_target;
    
    private EntityCreature m_attacker;
    
    private Item m_weapon;
    
    private float m_range;
    
    private float m_hemi;
    
    public MeleeAttackTask(EntityCreature target, EntityCreature attacker, Item weapon, float range, float hemi) {
        m_target = target;
        m_attacker = attacker;
        m_weapon = weapon;
        m_range = range;
    }
    
    @Override
    public void run() {
        Vector3f relative = m_target.getPosition().subtract(m_attacker.getPosition());
        
        if(relative.lengthSquared() <= m_range * m_range) {
            Vector3f attackdir = m_attacker.getMovementManager().getCurrentViewDirection();
            attackdir.normalizeLocal();
            
            relative.setY(0);
            relative.normalizeLocal();
            float fronthemi = relative.dot(attackdir);
            if(fronthemi >= m_hemi) {
                m_target.attackFrom(m_attacker, m_weapon);
            }
        }
    }
}
