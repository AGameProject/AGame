/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.movement;

import com.jme3.math.Vector3f;

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
    
    private Vector3f m_movdir;
    private Vector3f m_facedir;
    
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
    
    public void setMovDir(Vector3f dir) {
        m_movdir = dir;
    }
    
    public Vector3f getMovDir() {
        return m_movdir;
    }
    
    public void setFaceDir(Vector3f dir) {
        m_facedir = dir;
    }
    
    public Vector3f getFaceDir() {
        return m_facedir;
    }
}
