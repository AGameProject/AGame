/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.misc;

/**
 *
 * @author Fredie
 */
public class FloatInterpolator {
    float m_currentval = 0.0f;
    float m_goalval = 0.0f;
    float m_lastval = 0.0f;
    float m_lerptime = 1.0f;
    float m_lerpstep = 0.0f;
    
    public float getCurrentValue() {
        return m_currentval;
    }
    
    public float getGoal() {
        return m_goalval;
    }
    
    public void setGoal(float goal, float time) {
        m_lastval = m_currentval;
        m_goalval = goal;
        m_lerptime = time;
        m_lerpstep = (m_goalval - m_lastval) / m_lerptime;
    }
    
    public void update(float tpf) {
        if(m_lerpstep == 0) return;
        
        m_currentval += m_lerpstep * tpf;
        if(m_lerpstep < 0 && m_currentval < m_goalval) {
            m_currentval = m_goalval;
            m_lerpstep = 0;
        } else if(m_lerpstep > 0 && m_currentval > m_goalval) {
            m_currentval = m_goalval;
            m_lerpstep = 0;
        }
    }
}
