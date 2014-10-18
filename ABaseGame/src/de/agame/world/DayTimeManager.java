/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.world;

import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Fredie
 */
public class DayTimeManager {
    
    private Spatial m_sky;
    private DirectionalLight m_sun;
    
    public DayTimeManager() {
        m_sun = new DirectionalLight();
        m_sun.setColor(new ColorRGBA(0.9f, 1.0f, 0.6f, 1.0f));
        m_sun.setDirection(new Vector3f(0.5f, -0.5f, 0.5f).normalizeLocal());
    }
    
    public Spatial getSkyBox() {
        return m_sky;
    }
    
    public Light getSun() {
        return m_sun;
    }
    
    public void onUpdate(float tpf) {
        //TODO: update according to day - night cycle (if used)
    }
}
