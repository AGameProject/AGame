/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.animation;

import de.agame.entitys.movement.MovementState;

/**
 *
 * @author Fredie
 */
public class HumanoidAnimationProvider implements AnimationProvider{
    
    private MovementState m_currentState = null;
    
    private AnimLink m_falling[] = new AnimLink[3];
    private AnimLink m_idle[] = new AnimLink[3];
    private AnimLink m_walking[] = new AnimLink[3];
    private AnimLink m_crawling[] = new AnimLink[3];
    private AnimLink m_crouching[] = new AnimLink[3];
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
    
    public void setSprintingAnims(AnimLink anims[]) {
        m_sprinting = anims;
    }
    
    public AnimLink getBaseAnim(boolean holdingItem, boolean bothhanded) {
        if(m_currentState == null) return null;
        
        int animIndex = 0;
        if(holdingItem) {
            animIndex++;
            if(bothhanded) animIndex++;
        }
        
        if(!m_currentState.onGround()) {
            return m_falling[animIndex];
        } else if(m_currentState.getAction() == MovementState.MovementAction.idle) {
            return m_idle[animIndex];
        } else {
            if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.crawling) return m_crawling[animIndex];
            else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.crouching) return m_crouching[animIndex];
            else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.walking) return m_walking[animIndex];
            else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.sprinting) return m_sprinting[animIndex];
        }
        
        return null;
    }

    public MovementState getMovementState() {
        return m_currentState;
    }
}
