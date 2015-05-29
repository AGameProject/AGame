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
public interface AnimationProvider {
    public void setMovementState(MovementState state);
    public MovementState getMovementState();
    
    public AnimLink getBaseAnim(boolean holding, boolean bothhanded);
}
