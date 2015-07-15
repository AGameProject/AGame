/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.animation;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.LoopMode;
import java.util.HashMap;

/**
 *
 * @author Fredie
 */
public class AnimLink {
    private String m_name;
    private float m_tweak = 1.0f;
    private boolean m_loop = false;
    private float m_fade = 0.5f;
    
    private HashMap<String, Object> m_properties = new HashMap<String, Object>();
    
    public AnimLink(String name, boolean loop, float tweak, float fade) {
        m_name = name;
        m_loop = loop;
        m_tweak = tweak;
        m_fade = fade;
        
        if(m_name == null) m_name = "-1";
    }
    
    public void putProperty(String name, Object value) {
        m_properties.put(name, value);
    }
    
    public Object getProperty(String name) {
        return m_properties.get(name);
    }
    
    public String getName() {
        return m_name;
    }
    
    public float getTweak() {
        return m_tweak;
    }
    
    public void play(AnimChannel channel) {
        if(m_name == "-1") return;
        
        channel.setAnim(m_name, m_fade);
        channel.setSpeed(m_tweak);
        if(m_loop) channel.setLoopMode(LoopMode.Loop);
        else channel.setLoopMode(LoopMode.DontLoop);
    }
}
