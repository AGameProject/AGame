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
    float m_currentval = 0;
    float m_goalval = 0;
    float m_lastval = 0;
    float m_lerptime = 1;
    float m_lerpstep = 0;
    
    public float getCurrentValue() {
        return m_currentval;
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
        if(Math.abs(m_currentval - m_goalval) < 0.01f) {
            m_currentval = m_goalval;
            m_lerpstep = 0;
        } else if(m_lerpstep < 0 && m_currentval < m_goalval) {
            m_currentval = m_goalval;
            m_lerpstep = 0;
        } else if(m_lerpstep > 0 && m_currentval > m_goalval) {
            m_currentval = m_goalval;
            m_lerpstep = 0;
        }
    }
}
