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
 * @author puhzejo
 */
public class AttackSwing extends MeleeAttack{
    private AnimLink m_comboset[] = new AnimLink[3];

    public AttackSwing(AnimationProvider animprov){
        super("ATTACK_SWING", animprov);
        
        m_range = 3.0f;
    }
    
    @Override
    public AnimRequest executeCombo(int combo, EntityCreature attacker, EnviromentObservationSet enviroment) {
        super.executeCombo(combo, attacker, enviroment);
        
        if(getMovState().getAdditionalArg() != MovementState.AdditionalMovementArg.walking) return null;
        
        boolean uselegs = getMovState().getAction() == MovementState.MovementAction.idle;
        
        //handle movement
        Vector3f knock = attacker.getMovementManager().getCurrentViewDirection().normalize();
        knock.multLocal(2.0f);
        attacker.getMovementManager().knock(knock, 0.4f);
        
        return getRequest(m_comboset[combo], uselegs);
    }

    @Override
    public void prepareComboSet(AnimationProvider animprovider) {
        String postfix = getHeldItem().isTwoHanded() ? "_TWO" : "_ONE";
        
        m_comboset = animprovider.getRandomComboSet(getTag() + postfix);
        m_maxCombo = m_comboset.length;
    }
}














//what is up yoooo broos i've got some  bitches to handle over there, brazilian weed and detective holmes 
//is on the case. TurnUp!