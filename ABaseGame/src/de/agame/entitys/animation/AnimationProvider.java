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
public interface AnimationProvider {
    public void setMovementState(MovementState state);
    public MovementState getMovementState();
    
    public void setHeldItem(Item item);
    public Item getHeldItem();
    
    public AnimLink getBaseAnim();
    public AnimLink[] getRandomComboSet(String tag);
    public AnimLink getRandomBlock(String tag);
}
