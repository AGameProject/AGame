/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.combat;

import de.agame.entitys.EntityCreature;
import de.agame.entitys.animation.AnimLink;
import de.agame.entitys.animation.AnimRequest;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.movement.MovementManager;
import de.agame.entitys.movement.MovementState;
import de.agame.entitys.sets.EnviromentObservationSet;

/**
 *
 * @author puhzejo
 */
public class AttackSwing extends Attack{
    private AnimLink m_comboset[] = new AnimLink[3];

    public AttackSwing(AnimationProvider animprov){
        super("ATTACK_SWING", animprov);
    }
    
    @Override
    public AnimRequest executeCombo(int combo, EntityCreature attacker, EnviromentObservationSet enviroment) {
        if(getMovState().getAdditionalArg() != MovementState.AdditionalMovementArg.walking) return null;
        
        boolean uselegs = getMovState().getAction() == MovementState.MovementAction.idle;
        AnimRequest request = new AnimRequest(m_comboset[combo], getChannels(uselegs));
        request.setStatusListener(this);
        
        return request;
    }

    @Override
    public void prepareComboSet(AnimationProvider animprovider) {
        m_comboset = animprovider.getRandomComboSet(getTag());
        m_maxCombo = m_comboset.length;
    }
}














//what is up yoooo broos i've got some  bitches to handle over there, brazilian weed and detective holmes 
//is on the case. TurnUp!