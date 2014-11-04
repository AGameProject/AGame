/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.animation.AnimControl;
import com.jme3.bullet.control.BetterCharacterControl;

/**
 *
 * @author Fredie
 */
public class SpatialControlSet {
    private AnimControl m_animations;
    private BetterCharacterControl m_movementcontrol;
    
    public void setAnimationControl(AnimControl animations) {
        m_animations = animations;
    }
    
    public void setMovementControll(BetterCharacterControl movementcontrol) {
        m_movementcontrol = movementcontrol;
    }
    
    public AnimControl getAnimationControl() {
        return m_animations;
    }
    
    public BetterCharacterControl getMovementControl() {
        return m_movementcontrol;
    }
}
