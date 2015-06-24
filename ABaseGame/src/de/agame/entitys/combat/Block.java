/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.combat;

import de.agame.entitys.animation.AnimRequest;
import de.agame.entitys.animation.AnimStatusListener;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.movement.MovementManager;
import java.util.Arrays;

/**
 *
 * @author Fredie
 */
public abstract class Block implements AnimStatusListener{
    
    private boolean m_blocking = false;
    
    private String m_tag;
    
    private AnimationProvider m_animprovider;
    
    private int m_channels[];
    
    public Block(String tag, AnimationProvider animprovider) {
        m_tag = tag;
        m_animprovider = animprovider;
    }
    
    public void setChannels(int channels[]) {
        m_channels = channels;
    }
    
    public int[] getChannels(boolean useLegs) {
        if(useLegs) return m_channels;
        
        return Arrays.copyOf(m_channels, m_channels.length - 1);
    }
    
    public abstract AnimRequest getBlockAnim(AnimationProvider animprovider);

    public abstract float block(float impact, boolean sharp, MovementManager movementmanager);
    
    public String getTag() {
        return m_tag;
    }
    
    public boolean isBlocking() {
        return m_blocking;
    }
    
    public void onAnimStart() {
        m_blocking = true;
    }

    public void onAnimDone(boolean aborted) {
        m_blocking = false;
    }

    public void setEstimatedDuration(float duration) {
    }
}
