/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.combat;

import de.agame.entitys.animation.AnimRequest;
import de.agame.entitys.animation.AnimStatusListener;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.movement.MovementManager;
import de.agame.entitys.movement.MovementState;
import de.agame.entitys.sets.EnviromentObservationSet;
import java.util.Arrays;

/**
 *
 * @author Fredie
 */
public abstract class Attack implements AnimStatusListener{
    
    //the tag the Attack is associated with
    private String m_tag;
    
    //the stage the attack is in
    private int m_combo = 0;
    
    protected int m_maxCombo = 3;
    
    //how long the combo counts in seconds
    private float m_comboTimeOut = 0.5f;
    
    //when the attack has last been executed
    private double m_lastHit = 0;
    
    //if this attack is currently being executed
    private boolean m_executing = false;
    
    //the animprovider used by this attack
    private AnimationProvider m_provider;
    
    //the channels to play the animation on
    private int m_channels[];
    
    public Attack(String tag, AnimationProvider provider) {
        m_tag = tag;
        m_provider = provider;
    }
    
    public MovementState getMovState() {
        return m_provider.getMovementState();
    }
    
    public void setChannels(int channels[]) {
        m_channels = channels;
    }
    
    public int[] getChannels(boolean useLegs) {
        if(useLegs) return m_channels;
        
        return Arrays.copyOf(m_channels, m_channels.length - 1);
    }
    
    public String getTag() {
        return m_tag;
    }
    
    public boolean isExecuting() {
        return m_executing;
    }
    
    public AnimRequest execute(EnviromentObservationSet enviroment, AnimationProvider animprovider, MovementManager movementmanager) {
        if(m_executing && m_combo < m_maxCombo && System.currentTimeMillis() / 1000.0d > m_lastHit) m_executing = false;
        
        if(!m_executing) {
            m_executing = true;
            
            if(m_combo >= m_maxCombo) m_combo = 0;
            
            if(m_combo == 0) prepareComboSet(m_provider);
            
            AnimRequest anim = executeCombo(m_combo, movementmanager);
            m_combo++;
            
            return anim;
        }
        
        return null;
    }
    
    public abstract AnimRequest executeCombo(int combo, MovementManager movementmanager);
    
    public abstract void prepareComboSet(AnimationProvider animprovider);
    
    public void abort() {
        m_combo = 0;
        m_lastHit = 0;
        m_executing = false;
    }

    public void onAnimStart() {
        //do nothing here
    }

    public void onAnimDone(boolean aborted) {
        if(!aborted) {
            m_combo = 0;
            m_executing = false;
        }
    }

    public void setEstimatedDuration(float duration) {
        m_lastHit = (System.currentTimeMillis() / 1000.0d) + (double) (duration / 2.0f);
    }
}
