/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.misc;

/**
 *
 * @author Fredie
 */
public class DegreeInterpolator {
    float m_currentval = 0.0f;
    float m_goalval = 0.0f;
    float m_lastval = 0.0f;
    float m_lerptime = 1.0f;
    float m_lerpstep = 0.0f;
    float m_ogoalval = 0.0f;
    
    public float getCurrentValue() {
        return m_currentval;
    }
    
    private void findClosestWay(float value) {
        float direct = Math.abs(m_currentval - value);
        float neg = Math.abs(m_currentval - (value - 360.0f));
        float pos = Math.abs(m_currentval - (value + 360.0f));
        
        if(direct < neg && direct < pos) {
            m_goalval = direct;
        } else if(neg < direct && neg < pos) {
            m_goalval = neg;
        } else  if(pos < neg && pos < direct) {
            m_goalval = pos;
        }
    }
    
    public void setGoal(float goal, float time) {
        findClosestWay(goal);
        
        m_lastval = m_currentval;
        m_ogoalval = goal;
        m_lerptime = time;
        m_lerpstep = (m_goalval - m_lastval) / m_lerptime;
    }
    
    public void update(float tpf) {
        if(m_lerpstep == 0) return;
        
        m_currentval += m_lerpstep * tpf;
        if(m_lerpstep < 0 && m_currentval < m_goalval) {
            m_currentval = m_ogoalval;
            m_lerpstep = 0;
        } else if(m_lerpstep > 0 && m_currentval > m_goalval) {
            m_currentval = m_ogoalval;
            m_lerpstep = 0;
        }
    }
}
