/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.sets;

import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import de.agame.nifty.screencontrollers.HudController;

/**
 *
 * @author Fredie
 */
public class UserInterfaceSet {
    private Camera m_cam;
    private ChaseCamera m_chasecam;
    private NiftyJmeDisplay m_guinode;
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
    
    public void setGuiNode(NiftyJmeDisplay guinode) {
        m_guinode = guinode;
    }
    
    public NiftyJmeDisplay getGuiNode() {
        return m_guinode;
    }
    
    public HudController getHudController() {
        return (HudController) m_guinode.getNifty().getScreen("hud").getScreenController();
    }
    
    public void setInputManager(InputManager inputmanager) {
        m_inputmanager = inputmanager;
    }
    
    public InputManager getInputManager() {
        return m_inputmanager;
    }
}
