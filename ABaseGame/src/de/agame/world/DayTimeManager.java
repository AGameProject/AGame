/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.world;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.shadow.DirectionalLightShadowFilter;

/**
 * this class will be switched to a abstract control later for easier saving
 * @author Fredie
 */
public class DayTimeManager {
    public static final float CYCLE_LENGTH = 1200;
    
    private float m_currenttime = 0;
    
    private Spatial m_sky;
    private DirectionalLight m_sun;
    private DirectionalLight m_moon;
    private AmbientLight m_ambient;
    
    private DirectionalLightShadowFilter m_sunshadows;
    
    public DayTimeManager(AssetManager assets) {
        m_sun = new DirectionalLight();
        m_sun.setColor(new ColorRGBA(0.9f, 1.0f, 0.6f, 1.0f));
        m_sun.setDirection(new Vector3f(0.5f, -0.5f, 0.5f).normalizeLocal());
        m_sunshadows = new DirectionalLightShadowFilter(assets, 1024, 2);
        m_sunshadows.setLight(m_sun);
        
        m_ambient = new AmbientLight();
        m_ambient.setColor(new ColorRGBA(0.2f, 0.2f, 0.2f, 0.2f));
    }
    
    public Spatial getSkyBox() {
        return m_sky;
    }
    
    public Light getSun() {
        return m_sun;
    }
    
    public Light getMoon() {
        return m_moon;
    }
    
    public Light getAmbient() {
        return m_ambient;
    }
    
    public boolean isDay() {
        return m_currenttime < 600;
    }
    
    public DirectionalLightShadowFilter getSunShadows() {
        return m_sunshadows;
    }
    
    /**
     * updates direction and rotation of light and sky
     * @param tpf time since last update
     */
    public void onUpdate(float tpf) {
        
    }
}
