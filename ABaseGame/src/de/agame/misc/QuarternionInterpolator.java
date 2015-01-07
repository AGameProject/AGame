/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.misc;

import com.jme3.math.Quaternion;

/**
 *
 * @author Fredie
 */
public class QuarternionInterpolator {
    private Quaternion m_goal = new Quaternion();
    private Quaternion m_last = new Quaternion();
    private Quaternion m_current = new Quaternion();
    private float m_lerppos = 0;
    private float m_lerpstep = 0;
    
    public Quaternion getCurrentValue() {
        return m_goal;
    }
    
    public void setGoal(Quaternion goal, float time) {
        m_last.set(m_current);
        m_goal.set(goal);
        m_lerpstep = 1.0f / time;
        m_lerppos = 0.0f;
    }
    
    public void update(float tpf) {
        if(m_lerpstep == 0) return;
        
        m_lerppos += m_lerpstep * tpf;
        
        if(m_lerppos >= 1.0f) {
            m_lerpstep = 0.0f;
            m_lerppos = 1.0f;
            m_current.slerp(m_last, m_goal, 1.0f);
        } else {
            m_current.slerp(m_last, m_goal, m_lerppos);
        }
    }
}
