/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.animation;

import de.agame.Items.Item;
import de.agame.entitys.movement.MovementState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Fredie
 */
public class HumanoidAnimationProvider implements AnimationProvider{
    
    private MovementState m_currentState = null;
    private boolean m_inCombat = false;
    
    private Item m_heldItem = null;
    
    private HashMap<String, AnimLink> m_combatAnims = new HashMap<String, AnimLink>();
    private HashMap<String, Integer> m_combatComboCounts = new HashMap<String, Integer>();
    
    private AnimLink m_falling[] = new AnimLink[3];
    private AnimLink m_idle[] = new AnimLink[3];
    private AnimLink m_walking[] = new AnimLink[3];
    private AnimLink m_crawling[] = new AnimLink[3];
    private AnimLink m_crouching[] = new AnimLink[3];
    private AnimLink m_crouchwalking[] = new AnimLink[3];
    private AnimLink m_sprinting[] = new AnimLink[3];
    
    private AnimLink m_fallingCombat[] = new AnimLink[3];
    private AnimLink m_idleCombat[] = new AnimLink[3];
    private AnimLink m_walkingCombat[] = new AnimLink[3];
    private AnimLink m_crawlingCombat[] = new AnimLink[3];
    private AnimLink m_crouchingCombat[] = new AnimLink[3];
    private AnimLink m_crouchwalkingCombat[] = new AnimLink[3];
    private AnimLink m_sprintingCombat[] = new AnimLink[3];
    
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
    
    public void setCombatFallAnims(AnimLink anims[]) {
        m_fallingCombat = anims;
    }
    
    public void setCombatIdleAnims(AnimLink anims[]) {
        m_idleCombat = anims;
    }
    
    public void setCombatWalkingAnims(AnimLink anims[]) {
        m_walkingCombat = anims;
    }
    
    public void setCombatCrawlingAnims(AnimLink anims[]) {
        m_crawlingCombat = anims;
    }
    
    public void setCombatCrouchingAnims(AnimLink anims[]) {
        m_crouchingCombat = anims;
    }
    
    public void setCombatCrouchWalkingAnims(AnimLink anims[]) {
        m_crouchwalkingCombat = anims;
    }
    
    public void setCombatSprintingAnims(AnimLink anims[]) {
        m_sprintingCombat = anims;
    }
    
    public void addCombatCombo(String tag, AnimLink anims[]) {
        if(!m_combatComboCounts.containsKey(tag)) m_combatComboCounts.put(tag, 1);
        else {
            int count = m_combatComboCounts.get(tag);
            m_combatComboCounts.put(tag, count + 1);
        }
        
        int index = m_combatComboCounts.get(tag) - 1;
        
        for(int i = 0; i < anims.length; i++) {
            m_combatAnims.put(tag + index + "." + i, anims[i]);
        }
    }
    
    public void addCombatBlock(String tag, AnimLink anim) {
        if(!m_combatComboCounts.containsKey(tag)) m_combatComboCounts.put(tag, 1);
        else {
            int count = m_combatComboCounts.get(tag);
            m_combatComboCounts.put(tag, count + 1);
        }
        
        int index = m_combatComboCounts.get(tag) - 1;
        m_combatAnims.put(tag + index, anim);
    }
    
    public AnimLink getBaseAnim() {
        if(m_currentState == null) return null;
        
        int animIndex = 0;
        if(m_heldItem != null) {
            animIndex++;
            if(m_heldItem.isTwoHanded()) animIndex++;
        }
        
        if(m_inCombat) {
            if(!m_currentState.onGround()) {
                return m_fallingCombat[animIndex];
            } else if(m_currentState.getAction() == MovementState.MovementAction.idle) {
                if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.crouching) return m_crouchingCombat[animIndex];
                return m_idleCombat[animIndex];
            } else {
                if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.crawling) return m_crawlingCombat[animIndex];
                else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.crouching) return m_crouchwalkingCombat[animIndex];
                else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.walking) return m_walkingCombat[animIndex];
                else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.sprinting) return m_sprintingCombat[animIndex];
            }
        } else {
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

    public AnimLink[] getRandomComboSet(String tag) {
        if(!m_combatComboCounts.containsKey(tag)) return null;
        int index = new Random().nextInt(m_combatComboCounts.get(tag));
        
        ArrayList<AnimLink> anims = new ArrayList<AnimLink>();
        
        int count = 0;
        boolean stop = false;
        do {
            AnimLink anim = m_combatAnims.get(tag + index + "." + count);
            if(anim != null)
                anims.add(anim);
            else {
                stop = true;
            }
            
            count++;
        } while(!stop);
        
        AnimLink animarray[] = new AnimLink[anims.size()];
        for(int i = 0; i < anims.size(); i++) animarray[i] = anims.get(i);
        
        return animarray;
    }

    public AnimLink getRandomBlock(String tag) {
        if(!m_combatComboCounts.containsKey(tag)) return null;
        
        int index = new Random().nextInt(m_combatComboCounts.get(tag));
        
        return m_combatAnims.get(tag + index);
    }

    public void setInCombatMode(boolean flag) {
        m_inCombat = flag;
    }

    public boolean isInCombatMode() {
        return m_inCombat;
    }
}
