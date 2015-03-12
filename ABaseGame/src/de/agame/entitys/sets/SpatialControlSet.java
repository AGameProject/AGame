/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.sets;

import com.jme3.animation.AnimControl;
import com.jme3.bullet.control.CharacterControl;

/**
 *
 * @author Fredie
 */
public class SpatialControlSet {
    private AnimControl m_animations;
    private CharacterControl m_movementcontrol;
    
    public void setAnimationControl(AnimControl animations) {
        m_animations = animations;
    }
    
    public void setMovementControll(CharacterControl movementcontrol) {
        m_movementcontrol = movementcontrol;
    }
    
    public AnimControl getAnimationControl() {
        return m_animations;
    }
    
    public CharacterControl getMovementControl() {
        return m_movementcontrol;
    }
}
