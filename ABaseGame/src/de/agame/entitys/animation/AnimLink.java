/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.animation;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.LoopMode;

/**
 *
 * @author Fredie
 */
public class AnimLink {
    private String m_name;
    private float m_tweak = 1.0f;
    private boolean m_loop = false;
    private float m_fade = 0.5f;
    
    public AnimLink(String name, boolean loop, float tweak, float fade) {
        m_name = name;
        m_loop = loop;
        m_tweak = tweak;
        m_fade = fade;
        
        if(m_name == null) m_name = "-1";
    }
    
    public String getName() {
        return m_name;
    }
    
    public void play(AnimChannel channel) {
        if(m_name == "-1") return;
        
        channel.setAnim(m_name, m_fade);
        channel.setSpeed(m_tweak);
        if(m_loop) channel.setLoopMode(LoopMode.Loop);
        else channel.setLoopMode(LoopMode.DontLoop);
    }
}
