/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.combat;

import de.agame.entitys.animation.AnimRequest;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.movement.MovementManager;

/**
 *
 * @author Fredie
 */
public class BlockFist extends Block{

    public BlockFist(AnimationProvider animprovider) {
        super("BLOCK_FIST", animprovider);
    }
    
    @Override
    public AnimRequest getBlockAnim(AnimationProvider animprovider) {
        AnimRequest request = new AnimRequest(animprovider.getRandomBlock(getTag()), getChannels(true));
        request.setStatusListener(this);
        
        return request;
    }

    @Override
    public float block(float impact, boolean sharp, MovementManager movementmanager) {
        
        return 1.0f;
    }
    
}
