/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.animation;

/**
 *
 * @author Fredie
 */
public class AnimRequest {
    private AnimLink m_anim;
    private int m_channels[];
    private AnimStatusListener m_listener = null;
    
    public AnimRequest(AnimLink anim, int channels[]) {
        m_anim = anim;
        m_channels = channels;
    }
    
    public void setStatusListener(AnimStatusListener listener) {
        m_listener = listener;
    }
    
    public AnimStatusListener getStatusListener() {
        return m_listener;
    }
    
    public AnimLink getAnim() {
        return m_anim;
    }
    
    public int[] getChannels() {
        return m_channels;
    }
}
