/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.animation;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import de.agame.entitys.movement.MovementState;
import de.agame.entitys.movement.MovementStateChangeListener;
import java.util.ArrayList;

/**
 *
 * @author Fredie
 */
public class AnimationManager implements AnimEventListener, MovementStateChangeListener{
    
    //the basic movement of the character (as set by movement manager
    private AnimLink m_base;
    
    //the animation on top of movement (like attack and such)
    private AnimLink m_third;
    
    //a channel as reference for ending third animations
    private AnimChannel m_referenceChannel;
    
    //all channels that are actually on the model
    private ArrayList<AnimChannel> m_realChannels;
    private ArrayList<String> m_channelTags;
    private ArrayList<Boolean> m_thirdsPlaying;
    private ArrayList<Boolean> m_playThirds;
    
    private float m_speedCoefficient = 1;
    
    private AnimationProvider m_provider;
    
    public AnimationManager(AnimationProvider provider) {
        //cache the AnimationProvider
        m_provider = provider;
        
        //base and third are null as they arent used
        m_base = null;
        m_third = null;
        
        //create the arraylist to store the animchannels in
        m_realChannels = new ArrayList<AnimChannel>();
        m_channelTags = new ArrayList<String>();
        m_playThirds = new ArrayList<Boolean>();
        m_thirdsPlaying = new ArrayList<Boolean>();
    }
    
    public void handleRequest(AnimRequest request) {
        playThird(request.getAnim(), request.getChannels());
    }
    
    public void updateBaseAnim() {
        AnimLink anim = m_provider.getBaseAnim(false, false);
        
        if(anim == m_base) return;
        
        m_base = anim;
        
        if(m_base == null) {
            for(AnimChannel channel : m_realChannels) {
                channel.setAnim(null);
            }
            
            return;
        }
        
        for(int i = 0; i < m_realChannels.size(); i++) {
            if(!m_thirdsPlaying.get(i) && m_playThirds.get(i)) {
                m_base.play(m_realChannels.get(i));
                m_realChannels.get(i).setSpeed(m_speedCoefficient * m_base.getTweak());
            }
        }
    }
    
    public void setMovementState(MovementState state) {
        m_provider.setMovementState(state);
        
        updateBaseAnim();
    }
    
    public void addChannel(AnimChannel channel, String tag, boolean playThirds) {
        m_realChannels.add(channel);
        m_channelTags.add(tag);
        m_playThirds.add(playThirds);
        m_thirdsPlaying.add(false);
    }
    
    public int[] getChannels(String[] tags) {
        int indices[] = new int[tags.length];
        
        for(int i = 0; i < tags.length; i++) {
            indices[i] = m_channelTags.indexOf(tags[i]);
        }
        
        return indices;
    }

    public void abortThirdAnim() {
        for(int i = 0; i < m_realChannels.size(); i++) {
            if(m_thirdsPlaying.get(i)) {
                m_base.play(m_realChannels.get(i));
                m_thirdsPlaying.set(i, false);
            }
        }
        
        m_third = null;
    }
    
    public void playThird(AnimLink anim, int[] channels) {
        abortThirdAnim();
        
        for(int i : channels) {
            if(m_playThirds.get(i)) {
                anim.play(m_realChannels.get(i));
                m_thirdsPlaying.set(i, true);
            }
        }
        
        m_third = anim;
    }
    
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        int channelindex = m_realChannels.indexOf(channel);
        if(m_thirdsPlaying.get(channelindex)) {
            m_thirdsPlaying.set(channelindex, false);
            if(m_base != null) m_base.play(channel);
        }
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    public void onMovementStateChanged(MovementState state) {
        setMovementState(state);
    }

    public void setBaseAnimSpeedCoefficient(float coefficient) {
        if(m_speedCoefficient == coefficient) return;
        
        m_speedCoefficient = coefficient;
        
        for(int i = 0; i < m_realChannels.size(); i++) {
            if(!m_thirdsPlaying.get(i)) {
                m_realChannels.get(i).setSpeed(m_base.getTweak() * m_speedCoefficient);
            }
        }
    }
}
