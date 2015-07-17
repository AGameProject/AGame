/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.world;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingSphere;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.EdgeFilteringMode;

/**
 * this class will be switched to a abstract control later for easier saving
 * @author Fredie
 */
public class DayTimeManager {
    public static final float CYCLE_LENGTH = 1200;
    
    private float m_currenttime = 0;
    
    private Spatial m_sky;
    private Material m_skymat;
    
    private ColorRGBA m_suncolor;
    private ColorRGBA m_mooncolor;
    private ColorRGBA m_ambientcolor;
    
    private DirectionalLight m_sun;
    private DirectionalLight m_moon;
    private AmbientLight m_ambient;
    
    private DirectionalLightShadowFilter m_sunshadows;
    
    public DayTimeManager(AssetManager assets) {
        m_suncolor = new ColorRGBA(1.0f, 0.8f, 0.5f, 1.0f);
        m_ambientcolor = new ColorRGBA(0.7f, 0.8f, 0.8f, 0.3f);
        m_mooncolor = new ColorRGBA(0.3f, 0.3f, 0.3f, 0.3f);
        
        m_sun = new DirectionalLight();
        m_sun.setColor(m_suncolor);
        m_sun.setDirection(new Vector3f(0.5f, -0.5f, 0.5f).normalizeLocal());
        m_sunshadows = new DirectionalLightShadowFilter(assets, 1024, 2);
        m_sunshadows.setLight(m_sun);
        m_sunshadows.setEdgeFilteringMode(EdgeFilteringMode.PCF4);
        
        m_moon = new DirectionalLight();
        m_moon.setColor(m_mooncolor);
        m_moon.setDirection(new Vector3f(0.5f, -0.5f, 0.5f).normalizeLocal());
        
        m_ambient = new AmbientLight();
        m_ambient.setColor(m_ambientcolor);
        
        Sphere sphere = new Sphere(5, 5, 100, true, true);
        Geometry geom = new Geometry("Sky", sphere);
        geom.setCullHint(Spatial.CullHint.Never);
        geom.setQueueBucket(RenderQueue.Bucket.Sky);
        geom.setShadowMode(RenderQueue.ShadowMode.Off);
        geom.setModelBound(new BoundingSphere(Float.POSITIVE_INFINITY, new Vector3f(0, 0, 0)));
        
        m_skymat = new Material(assets, "MatDefs/Sky/DynamicSky.j3md");
        m_skymat.setColor("suncolor", m_suncolor);
        m_skymat.setColor("mooncolor", m_mooncolor);
        m_skymat.setColor("skycolor", new ColorRGBA(0.5f, 0.5f, 1.0f, 1.0f));
        m_skymat.setVector3("sundir", new Vector3f(0.5f, -0.5f, 0.5f));
        m_skymat.setTexture("starmap", assets.loadTexture("Textures/starmap.png"));
        geom.setMaterial(m_skymat);
        
        m_sky = geom;
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
        m_currenttime += 20.0f * tpf;
        if(m_currenttime >= 1200) m_currenttime -= 1200;
        
        float rot = ((float) Math.PI / 600.0f) * m_currenttime;
        Quaternion quat = new Quaternion();
        quat.fromAngleAxis(rot, Vector3f.UNIT_X);
        Vector3f sundir = quat.mult(Vector3f.UNIT_Z);
        
        quat.fromAngleAxis(rot - (float) Math.PI, Vector3f.UNIT_X);
        Vector3f moondir = quat.mult(Vector3f.UNIT_Z);
        
        m_sun.setDirection(sundir);
        m_skymat.setVector3("sundir", sundir);
        
        m_moon.setDirection(moondir);
        m_skymat.setVector3("moondir", moondir);
        
        float sunintens = 1.0f;
        
        if(isDay()) {
            if(m_currenttime > 500) sunintens = 1.0f - ((m_currenttime - 500.0f) / 100.0f);
            if(m_currenttime < 100) sunintens = m_currenttime / 100.0f;
        } else {
            sunintens = 0.0f;
        }
        
        m_sun.setColor(m_suncolor.mult(sunintens));
        m_moon.setColor(m_mooncolor.mult((1.0f - sunintens) * 0.4f));
        m_ambient.setColor(m_ambientcolor.mult(sunintens + 0.2f));
        
        m_sunshadows.setShadowIntensity(1.0f - (sunintens + 0.4f) * 0.4f);
        
        if(m_sunshadows.isEnabled() && !isDay()) m_sunshadows.setEnabled(false);
        else if(!m_sunshadows.isEnabled() && isDay()) m_sunshadows.setEnabled(true);
    }
}
