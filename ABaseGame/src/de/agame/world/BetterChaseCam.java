/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.world;

import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

/**
 *
 * @author Fredie
 */
public class BetterChaseCam extends ChaseCamera {
    
    public BetterChaseCam(Camera cam, Spatial target, InputManager input) {
        super(cam, target, input);
    }
    
    @Override
    public void setEnabled(boolean flag) {
        super.setEnabled(flag);
        canRotate = flag;
    }
}
