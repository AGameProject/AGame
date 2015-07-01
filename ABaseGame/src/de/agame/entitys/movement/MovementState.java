/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.movement;

/**
 *
 * @author Fredie
 */
public class MovementState {
    public enum MovementAction {
        idle, moving
    }
    
    public enum AdditionalMovementArg {
        walking, sprinting, crouching, crawling
    }
    
    private MovementAction m_action;
    private AdditionalMovementArg m_arg;
    private boolean m_onGround = true;
    
    public void setAction(MovementAction action) {
        m_action = action;
    }
    
    public MovementAction getAction() {
        return m_action;
    }
    
    public void setAdditionalArgument(AdditionalMovementArg arg) {
        m_arg = arg;
    }
    
    public AdditionalMovementArg getAdditionalArg() {
        return m_arg;
    }
    
    public void setOnGround(boolean flag) {
        m_onGround = flag;
    }
    
    public boolean onGround() {
        return m_onGround;
    }
}
