/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.movement;

import de.agame.entitys.animation.AnimRequest;

/**
 *
 * @author Fredie
 */
public interface MovementStateChangeListener {
    
    public void onMovementStateChanged(MovementState state);
    
    public void setBaseAnimSpeedCoefficient(float coefficient);
    
    public void handleAnimRequest(AnimRequest request);
}
