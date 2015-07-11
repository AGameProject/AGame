/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.combat;

import de.agame.entitys.animation.AnimRequest;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.movement.MovementManager;
import de.agame.entitys.movement.MovementState;

/**
 *
 * @author Fredie
 */
public class BlockFist extends Block{

    private float m_maxImpact = 10;
    
    public BlockFist(AnimationProvider animprovider) {
        super("BLOCK_FIST", animprovider);
    }
    
    @Override
    public AnimRequest getBlockAnim(AnimationProvider animprovider) {
        if(getMovState().getAdditionalArg() != MovementState.AdditionalMovementArg.walking) return null;
        
        boolean useLegs = getMovState().getAction() == MovementState.MovementAction.idle;
        AnimRequest request = new AnimRequest(animprovider.getRandomBlock(getTag()), getChannels(useLegs));
        request.setStatusListener(this);
        
        return request;
    }

    @Override
    public float block(float impact, boolean sharp, MovementManager movementmanager) {
        
        return 1.0f;
    }
    
}
