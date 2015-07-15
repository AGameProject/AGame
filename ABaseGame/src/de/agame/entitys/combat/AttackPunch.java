/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.combat;

import com.jme3.math.Vector3f;
import de.agame.entitys.EntityCreature;
import de.agame.entitys.animation.AnimLink;
import de.agame.entitys.animation.AnimRequest;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.movement.MovementState;
import de.agame.entitys.sets.EnviromentObservationSet;

/**
 *
 * @author Fredie
 */
public class AttackPunch extends MeleeAttack{
    
    private AnimLink m_comboSet[] = new AnimLink[3];
    
    public AttackPunch(AnimationProvider animprovider) {
        super("ATTACK_PUNCH", animprovider);
        
        m_range = 1.5f;
    }

    @Override
    public AnimRequest executeCombo(int combo, EntityCreature attacker, EnviromentObservationSet enviroment) {
        super.executeCombo(combo, attacker, enviroment);
        
        if(getMovState().getAdditionalArg() != MovementState.AdditionalMovementArg.walking) return null;
        
        boolean useLegs = getMovState().getAction() == MovementState.MovementAction.idle;
        
        //handle movement
        Vector3f knock = attacker.getMovementManager().getCurrentViewDirection().normalize();
        knock.multLocal(2.0f);
        attacker.getMovementManager().knock(knock, 0.4f);
        
        return getRequest(m_comboSet[combo], useLegs);
    }

    @Override
    public void prepareComboSet(AnimationProvider animprovider) {
        m_comboSet = animprovider.getRandomComboSet(getTag());
        m_maxCombo = m_comboSet.length;
    }
}
