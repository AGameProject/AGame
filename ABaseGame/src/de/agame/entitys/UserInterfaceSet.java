/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

/**
 *
 * @author Fredie
 */
public class UserInterfaceSet {
    private Camera m_cam;
    private ChaseCamera m_chasecam;
    private Node m_guinode;
    private InputManager m_inputmanager;
    
    public void setCam(Camera cam) {
        m_cam = cam;
    }
    
    public Camera getCam() {
        return m_cam;
    }
    
    public void setChaseCam(ChaseCamera chasecam) {
        m_chasecam = chasecam;
    }
    
    public ChaseCamera getChaseCam() {
        return m_chasecam;
    }
    
    public void setGuiNode(Node guinode) {
        m_guinode = guinode;
    }
    
    public Node getGuiNode() {
        return m_guinode;
    }
    
    public void setInputManager(InputManager inputmanager) {
        m_inputmanager = inputmanager;
    }
    
    public InputManager getInputManager() {
        return m_inputmanager;
    }
}
