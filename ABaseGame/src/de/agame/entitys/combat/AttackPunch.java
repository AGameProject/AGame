/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.combat;

import de.agame.entitys.animation.AnimLink;
import de.agame.entitys.animation.AnimRequest;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.movement.MovementState;

/**
 *
 * @author Fredie
 */
public class AttackPunch extends Attack{
    
    private AnimLink m_comboSet[] = new AnimLink[3];
    
    public AttackPunch(AnimationProvider animprovider) {
        super("PUNCH", animprovider);
    }

    @Override
    public AnimRequest executeCombo(int combo) {
        if(getMovState().getAdditionalArg() != MovementState.AdditionalMovementArg.walking) return null;
        
        boolean useLegs = false;
        if(getMovState().getAction() == MovementState.MovementAction.idle) useLegs = true;
        
        AnimRequest request = new AnimRequest(m_comboSet[combo], getChannels(useLegs));
        request.setStatusListener(this);
        
        return request;
    }

    @Override
    public void prepareComboSet(AnimationProvider animprovider) {
        m_comboSet = animprovider.getRandomComboSet(getTag());
        m_maxCombo = m_comboSet.length;
    }
    
}
