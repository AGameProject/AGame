/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.animation;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
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
    private AnimLink m_walkingleft[] = new AnimLink[3];
    private AnimLink m_walkingright[] = new AnimLink[3];
    private AnimLink m_walkingfrontleft[] = new AnimLink[3];
    private AnimLink m_walkingfrontright[] = new AnimLink[3];
    private AnimLink m_walkingbackleft[] = new AnimLink[3];
    private AnimLink m_walkingbackright[] = new AnimLink[3];
    private AnimLink m_walkingback[] = new AnimLink[3];
    
    private AnimLink m_crawling[] = new AnimLink[3];
    
    private AnimLink m_crouching[] = new AnimLink[3];
    private AnimLink m_crouchwalking[] = new AnimLink[3];
    private AnimLink m_crouchwalkingleft[] = new AnimLink[3];
    private AnimLink m_crouchwalkingright[] = new AnimLink[3];
    private AnimLink m_crouchwalkingfrontleft[] = new AnimLink[3];
    private AnimLink m_crouchwalkingfrontright[] = new AnimLink[3];
    private AnimLink m_crouchwalkingbackleft[] = new AnimLink[3];
    private AnimLink m_crouchwalkingbackright[] = new AnimLink[3];
    private AnimLink m_crouchwalkingback[] = new AnimLink[3];
    
    private AnimLink m_sprinting[] = new AnimLink[3];
    
    private AnimLink m_fallingCombat[] = new AnimLink[3];
    private AnimLink m_idleCombat[] = new AnimLink[3];
    
    private AnimLink m_walkingCombat[] = new AnimLink[3];
    private AnimLink m_walkingleftCombat[] = new AnimLink[3];
    private AnimLink m_walkingrightCombat[] = new AnimLink[3];
    private AnimLink m_walkingfrontleftCombat[] = new AnimLink[3];
    private AnimLink m_walkingfrontrightCombat[] = new AnimLink[3];
    private AnimLink m_walkingbackleftCombat[] = new AnimLink[3];
    private AnimLink m_walkingbackrightCombat[] = new AnimLink[3];
    private AnimLink m_walkingbackCombat[] = new AnimLink[3];
    
    private AnimLink m_crawlingCombat[] = new AnimLink[3];

    private AnimLink m_crouchingCombat[] = new AnimLink[3];
    private AnimLink m_crouchwalkingCombat[] = new AnimLink[3];
    private AnimLink m_crouchwalkingleftCombat[] = new AnimLink[3];
    private AnimLink m_crouchwalkingrightCombat[] = new AnimLink[3];
    private AnimLink m_crouchwalkingfrontleftCombat[] = new AnimLink[3];
    private AnimLink m_crouchwalkingfrontrightCombat[] = new AnimLink[3];
    private AnimLink m_crouchwalkingbackleftCombat[] = new AnimLink[3];
    private AnimLink m_crouchwalkingbackrightCombat[] = new AnimLink[3];
    private AnimLink m_crouchwalkingbackCombat[] = new AnimLink[3];
    private AnimLink m_sprintingCombat[] = new AnimLink[3];
    
    private AnimLink m_stumbleBack[];
    private AnimLink m_stumbleLeft[];
    private AnimLink m_stumbleRight[];
    private AnimLink m_stumbleFront[];
    
    public void setMovementState(MovementState state) {
        m_currentState = state;
    }
    
    public void setStumbleBackAnims(AnimLink... anims) {
        m_stumbleBack = anims;
    }
    
    public void setStumbleLeftAnims(AnimLink... anims) {
        m_stumbleLeft = anims;
    }
        
    public void setStumbleRightAnims(AnimLink... anims) {
        m_stumbleRight = anims;
    }
    
    public void setStumbleFrontAnims(AnimLink... anims) {
        m_stumbleFront = anims;
    }
    
    public void setFallAnims(AnimLink... anims) {
        m_falling = anims;
    }
    
    public void setIdleAnims(AnimLink... anims) {
        m_idle = anims;
    }
    
    public void setWalkingAnims(AnimLink... anims) {
        m_walking = anims;
    }
    
    public void setWalkingLeftAnims(AnimLink... anims) {
        m_walkingleft = anims;
    }
    
    public void setWalkingRightAnims(AnimLink... anims) {
        m_walkingright = anims;
    }
    
    public void setWalkingFrontLeftAnims(AnimLink... anims) {
        m_walkingfrontleft = anims;
    }
    
    public void setWalkingFrontRightAnims(AnimLink... anims) {
        m_walkingfrontright = anims;
    }
    
    public void setWalkingBackLeftAnims(AnimLink... anims) {
        m_walkingbackleft = anims;
    }
    
    public void setWalkingBackRightAnims(AnimLink... anims) {
        m_walkingbackright = anims;
    }
    
    public void setWalkingBackAnims(AnimLink... anims) {
        m_walkingback = anims;
    }
    
    public void setCrawlingAnims(AnimLink... anims) {
        m_crawling = anims;
    }
    
    public void setCrouchingAnims(AnimLink... anims) {
        m_crouching = anims;
    }
    
    public void setCrouchWalkingAnims(AnimLink... anims) {
        m_crouchwalking = anims;
    }
    
    public void setCrouchWalkingLeftAnims(AnimLink... anims) {
        m_crouchwalkingleft = anims;
    }
    
    public void setCrouchWalkingRightAnims(AnimLink... anims) {
        m_crouchwalkingright = anims;
    }
    
    public void setCrouchWalkingFrontLeftAnims(AnimLink... anims) {
        m_crouchwalkingfrontleft = anims;
    }
    
    public void setCrouchWalkingFrontRightAnims(AnimLink... anims) {
        m_crouchwalkingfrontright = anims;
    }
    
    public void setCrouchWalkingBackLeftAnims(AnimLink... anims) {
        m_crouchwalkingbackleft = anims;
    }
    
    public void setCrouchWalkingBackRightAnims(AnimLink... anims) {
        m_crouchwalkingbackright = anims;
    }
    
    public void setCrouchWalkingBackAnims(AnimLink... anims) {
        m_crouchwalkingback = anims;
    }
    
    public void setSprintingAnims(AnimLink... anims) {
        m_sprinting = anims;
    }
    
    public void setCombatFallAnims(AnimLink... anims) {
        m_fallingCombat = anims;
    }
    
    public void setCombatIdleAnims(AnimLink... anims) {
        m_idleCombat = anims;
    }
    
    public void setCombatWalkingAnims(AnimLink... anims) {
        m_walkingCombat = anims;
    }
    
    public void setCombatWalkingLeftAnims(AnimLink... anims) {
        m_walkingleftCombat = anims;
    }
    
    public void setCombatWalkingRightAnims(AnimLink... anims) {
        m_walkingrightCombat = anims;
    }
    
    public void setCombatWalkingFrontLeftAnims(AnimLink... anims) {
        m_walkingfrontleftCombat = anims;
    }
    
    public void setCombatWalkingFrontRightAnims(AnimLink... anims) {
        m_walkingfrontrightCombat = anims;
    }
    
    public void setCombatWalkingBackLeftAnims(AnimLink... anims) {
        m_walkingbackleftCombat = anims;
    }
    
    public void setCombatWalkingBackRightAnims(AnimLink... anims) {
        m_walkingbackrightCombat = anims;
    }
    
    public void setCombatWalkingBackAnims(AnimLink... anims) {
        m_walkingbackCombat = anims;
    }
    
    public void setCombatCrawlingAnims(AnimLink... anims) {
        m_crawlingCombat = anims;
    }
    
    public void setCombatCrouchingAnims(AnimLink... anims) {
        m_crouchingCombat = anims;
    }
    
    public void setCombatCrouchWalkingLeftAnims(AnimLink... anims) {
        m_crouchwalkingleftCombat = anims;
    }
    
    public void setCombatCrouchWalkingRightAnims(AnimLink... anims) {
        m_crouchwalkingrightCombat = anims;
    }
    
    public void setCombatCrouchWalkingFrontLeftAnims(AnimLink... anims) {
        m_crouchwalkingfrontleftCombat = anims;
    }
    
    public void setCombatCrouchWalkingFrontRightAnims(AnimLink... anims) {
        m_crouchwalkingfrontrightCombat = anims;
    }
    
    public void setCombatCrouchWalkingBackLeftAnims(AnimLink... anims) {
        m_crouchwalkingbackleftCombat = anims;
    }
    
    public void setCombatCrouchWalkingBackRightAnims(AnimLink... anims) {
        m_crouchwalkingbackrightCombat = anims;
    }
    
    public void setCombatCrouchWalkingBackAnims(AnimLink... anims) {
        m_crouchwalkingbackCombat = anims;
    }
    
    public void setCombatCrouchWalkingAnims(AnimLink... anims) {
        m_crouchwalkingCombat = anims;
    }
    
    public void setCombatSprintingAnims(AnimLink... anims) {
        m_sprintingCombat = anims;
    }
    
    public void addCombatCombo(String tag, AnimLink... anims) {
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
        
        int dirindex = 0;
        
        if(m_currentState.getFaceDir() != null && m_currentState.getMovDir() != null) {
            Vector3f movdir = m_currentState.getMovDir().normalize();
            Vector3f facedir = m_currentState.getFaceDir().normalize();

            Quaternion rot = new Quaternion();
            rot.fromAngleAxis((float)Math.toRadians(-90.0f), Vector3f.UNIT_Y);
            Vector3f faceleft = rot.mult(facedir);

            float degree = (float) Math.toDegrees(movdir.angleBetween(facedir));
            if(movdir.dot(faceleft) > 0) degree = 360.0f - degree;

            float halfpart = 360.0f / 16.0f;

            dirindex = (int) (degree / halfpart);
            dirindex++;
            dirindex /= 2;

            dirindex = dirindex > 7 ? 0 : dirindex;
            
        }
        
        if(m_inCombat) {
            if(!m_currentState.onGround()) {
                return m_fallingCombat[animIndex];
            } else if(m_currentState.getAction() == MovementState.MovementAction.idle) {
                if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.crouching) return m_crouchingCombat[animIndex];
                return m_idleCombat[animIndex];
            } else {
                if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.crawling) return m_crawlingCombat[animIndex];
                else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.crouching) {
                    switch(dirindex) {
                            case 0: return m_crouchwalkingCombat[animIndex];
                            case 1: return m_crouchwalkingfrontleftCombat[animIndex];
                            case 2: return m_crouchwalkingleftCombat[animIndex];
                            case 3: return m_crouchwalkingbackleftCombat[animIndex];
                            case 4: return m_crouchwalkingbackCombat[animIndex];
                            case 5: return m_crouchwalkingbackrightCombat[animIndex];
                            case 6: return m_crouchwalkingrightCombat[animIndex];
                            case 7: return m_crouchwalkingfrontrightCombat[animIndex];
                    }
                }
                else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.walking) {
                    switch(dirindex) {
                            case 0: return m_walkingCombat[animIndex];
                            case 1: return m_walkingfrontleftCombat[animIndex];
                            case 2: return m_walkingleftCombat[animIndex];
                            case 3: return m_walkingbackleftCombat[animIndex];
                            case 4: return m_walkingbackCombat[animIndex];
                            case 5: return m_walkingbackrightCombat[animIndex];
                            case 6: return m_walkingrightCombat[animIndex];
                            case 7: return m_walkingfrontrightCombat[animIndex];
                    }
                }
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
                else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.crouching) {
                    switch(dirindex) {
                            case 0: return m_crouchwalking[animIndex];
                            case 1: return m_crouchwalkingfrontleft[animIndex];
                            case 2: return m_crouchwalkingleft[animIndex];
                            case 3: return m_crouchwalkingbackleft[animIndex];
                            case 4: return m_crouchwalkingback[animIndex];
                            case 5: return m_crouchwalkingbackright[animIndex];
                            case 6: return m_crouchwalkingright[animIndex];
                            case 7: return m_crouchwalkingfrontright[animIndex];
                    }
                }
                else if(m_currentState.getAdditionalArg() == MovementState.AdditionalMovementArg.walking) {
                    switch(dirindex) {
                            case 0: return m_walking[animIndex];
                            case 1: return m_walkingfrontleft[animIndex];
                            case 2: return m_walkingleft[animIndex];
                            case 3: return m_walkingbackleft[animIndex];
                            case 4: return m_walkingback[animIndex];
                            case 5: return m_walkingbackright[animIndex];
                            case 6: return m_walkingright[animIndex];
                            case 7: return m_walkingfrontright[animIndex];
                    }
                }
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
    
    public AnimLink getRandomStumbleAnim(float fronthemi, float lefthemi) {
        float frontimp = Math.abs(lefthemi);
        float leftimp = Math.abs(fronthemi);
        
        float quaterangle = (float) Math.cos(45);
        
        if(frontimp > quaterangle) {
            if(fronthemi < 0)return m_stumbleBack[new Random().nextInt(m_stumbleBack.length)];
            else return m_stumbleFront[new Random().nextInt(m_stumbleFront.length)];
        } else {
            if(lefthemi < 0) return m_stumbleRight[new Random().nextInt(m_stumbleRight.length)];
            else return m_stumbleLeft[new Random().nextInt(m_stumbleLeft.length)];
        }
    }
}
