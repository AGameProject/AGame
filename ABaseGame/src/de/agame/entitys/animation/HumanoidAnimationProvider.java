/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.animation;

import de.agame.Items.Item;
import de.agame.entitys.movement.MovementState;

/**
 *
 * @author Fredie
 */
public class HumanoidAnimationProvider implements AnimationProvider{
    
    private MovementState m_currentState = null;
    
    private Item m_heldItem = null;
    
    private AnimLink m_falling[] = new AnimLink[3];
    private AnimLink m_idle[] = new AnimLink[3];
    private AnimLink m_walking[] = new AnimLink[3];
    private AnimLink m_crawling[] = new AnimLink[3];
    private AnimLink m_crouching[] = new AnimLink[3];
    private AnimLink m_crouchwalking[] = new AnimLink[3];
    private AnimLink m_sprinting[] = new AnimLink[3];
    
    public void setMovementState(MovementState state) {
        m_currentState = state;
    }
    
    public void setFallAnims(AnimLink anims[]) {
        m_falling = anims;
    }
    
    public void setIdleAnims(AnimLink anims[]) {
        m_idle = anims;
    }
    
    public void setWalkingAnims(AnimLink anims[]) {
        m_walking = anims;
    }
    
    public void setCrawlingAnims(AnimLink anims[]) {
        m_crawling = anims;
    }
    
    public void setCrouchingAnims(AnimLink anims[]) {
        m_crouching = anims;
    }
    
    public void setCrouchWalkingAnims(AnimLink anims[]) {
        m_crouchwalking = anims;
    }
    
    public void setSprintingAnims(AnimLink anims[]) {
        m_sprinting = anims;
    }
    
    public AnimLink getBaseAnim() {
        if(m_currentState == null) return null;
        
        int animIndex = 0;
        if(m_heldItem != null) {
            animIndex++;
            if(m_heldItem.isTwoHanded()) animIndex++;
        }
        
        if(!m_currentState.onGround()) {
            return m_falling[animIndex];
        } else if(m_currentState.getAction() == MovementState.MovementAction.idle) {
            if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.crouching) return m_crouching[animIndex];
            return m_idle[animIndex];
        } else {
            if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.crawling) return m_crawling[animIndex];
            else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.crouching) return m_crouchwalking[animIndex];
            else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.walking) return m_walking[animIndex];
            else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.sprinting) return m_sprinting[animIndex];
        }
        
        return null;
    }

    public MovementState getMovementState() {
        return m_currentState;
    }

    public void setHeldItem(Item item) {
        m_heldItem = item;
    }

    public Item getHeldItem() {
        return m_heldItem;
    }
}
