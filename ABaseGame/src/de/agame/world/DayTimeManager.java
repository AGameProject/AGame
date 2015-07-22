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
    public static final int YEAR_LENGTH = 60;
    
    private float m_currenttime = 0;
    
    private Spatial m_sky;
    private Material m_skymat;
    
    private ColorRGBA m_suncolor;
    private ColorRGBA m_mooncolor;
    private ColorRGBA m_ambientcolor;
    
    private DirectionalLight m_sun;
    private DirectionalLight m_moon;
    private AmbientLight m_ambient;
    
    private Vector3f m_axis;
    private float m_latitude;
    private float m_season;
    private int m_daysinyear;
    
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
        m_sunshadows.setLambda(0.9f);
        
        m_moon = new DirectionalLight();
        m_moon.setColor(m_mooncolor);
        m_moon.setDirection(new Vector3f(0.5f, -0.5f, 0.5f).normalizeLocal());
        
        m_ambient = new AmbientLight();
        m_ambient.setColor(m_ambientcolor);
        
        Sphere sphere = new Sphere(3, 3, 100, true, true);
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
        
        setLatitude(48.0f);
        setSeason(0.5f);
        
        m_daysinyear = 0;
    }
    
    public void updateRotLevel() {
        Quaternion quat = new Quaternion();
        quat.fromAngleAxis((float) Math.toRadians(m_latitude - 46.88f * (m_season - 0.5f)), Vector3f.UNIT_Z);
        m_axis = quat.mult(Vector3f.UNIT_X);
    }
    
    public void setLatitude(float latitude) {
        m_latitude = latitude;
        
        updateRotLevel();
    }
    
    public void setSeason(float season) {
        m_season = season;
        m_daysinyear = (int) (YEAR_LENGTH / 2.0f * season);
        
        updateRotLevel();
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
        return m_currenttime < 0.5f * CYCLE_LENGTH;
    }
    
    public DirectionalLightShadowFilter getSunShadows() {
        return m_sunshadows;
    }
    
    /**
     * updates direction and rotation of light and sky
     * @param tpf time since last update
     */
    public void onUpdate(float tpf) {
        m_currenttime += tpf;
        if(m_currenttime >= CYCLE_LENGTH) {
            m_currenttime -= CYCLE_LENGTH;
            
            m_daysinyear++;
            m_season = m_daysinyear / (float) YEAR_LENGTH;
            m_season = m_season * 2.0f;
            m_season = m_season > 1.0f ? 1.0f - m_season : m_season;
            
            updateRotLevel();
        }
        
        float rot = ((float) Math.PI / (0.5f * CYCLE_LENGTH)) * m_currenttime;
        Quaternion quat = new Quaternion();
        quat.fromAngleAxis(rot, m_axis);
        Vector3f sundir = quat.mult(Vector3f.UNIT_Z);
        
        quat.fromAngleAxis(rot - (float) Math.PI, m_axis);
        Vector3f moondir = quat.mult(Vector3f.UNIT_Z);
        
        m_sun.setDirection(sundir);
        m_skymat.setVector3("sundir", sundir);
        
        m_moon.setDirection(moondir);
        m_skymat.setVector3("moondir", moondir);
        
        float sunintens = new Vector3f(0, -1, 0).dot(sundir);
        sunintens = Math.max(0, sunintens);
        
        m_sun.setColor(m_suncolor.mult(sunintens));
        m_moon.setColor(m_mooncolor.mult((1.0f - sunintens) * 0.4f));
        m_ambient.setColor(m_ambientcolor.mult(sunintens + 0.2f));
        
        m_sunshadows.setShadowIntensity(0.7f * sunintens);
        
        if(m_sunshadows.isEnabled() && !isDay()) m_sunshadows.setEnabled(false);
        else if(!m_sunshadows.isEnabled() && isDay()) m_sunshadows.setEnabled(true);
    }
}