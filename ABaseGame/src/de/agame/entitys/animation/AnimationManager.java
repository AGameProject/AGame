/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.animation;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import de.agame.Items.Item;
import de.agame.entitys.combat.CombatStateListener;
import de.agame.entitys.movement.MovementState;
import de.agame.entitys.movement.MovementStateChangeListener;
import java.util.ArrayList;

/**
 *
 * @author Fredie
 */
public class AnimationManager implements AnimEventListener, MovementStateChangeListener, CombatStateListener{
    
    //the basic movement of the character (as set by movement manager
    private AnimLink m_base;
    
    //the animation on top of movement (like attack and such)
    private AnimLink m_third;
    
    private AnimStatusListener m_thirdStatusListener;
    
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
    
    public void handleAnimRequest(AnimRequest request) {
        //play the request as a third
        playThird(request.getAnim(), request.getChannels(), request.getStatusListener());
    }
    
    public void setHeldItem(Item item) {
        //pass the held item through to the provider
        m_provider.setHeldItem(item);
    }
    
    public void updateBaseAnim() {
        //get the current animation from the provider
        AnimLink anim = m_provider.getBaseAnim();
        
        //if nothing changed do nothing
        if(anim == m_base) return;
        
        //cache the new base animation
        m_base = anim;
        
        //if there is no base animation do nothing
        if(m_base == null) {
            return;
        }
        
        //play the new base animation on all channels
        for(int i = 0; i < m_realChannels.size(); i++) {
            if(!m_thirdsPlaying.get(i) && m_playThirds.get(i)) {
                m_base.play(m_realChannels.get(i));
                m_realChannels.get(i).setSpeed(m_speedCoefficient * m_base.getTweak());
            }
        }
    }
    
    public void setMovementState(MovementState state) {
        //pass the new state through to the animationprovider
        m_provider.setMovementState(state);
        
        //update the base animation
        updateBaseAnim();
    }
    
    public void addChannel(AnimChannel channel, String tag, boolean playThirds) {
        m_realChannels.add(channel);
        m_channelTags.add(tag);
        m_playThirds.add(playThirds);
        m_thirdsPlaying.add(false);
    }
    
    public int[] getChannels(String[] tags) {
        //create an integer array with the same length as the given tags
        int indices[] = new int[tags.length];
        
        //store the channel indices in the array
        for(int i = 0; i < tags.length; i++) {
            indices[i] = m_channelTags.indexOf(tags[i]);
        }
        
        return indices;
    }

    public void abortThirdAnim() {
        if(m_third == null) return;
        
        if(m_thirdStatusListener != null) m_thirdStatusListener.onAnimDone(true);
        
        //check all channels if a third is running
        for(int i = 0; i < m_realChannels.size(); i++) {
            //if third anim is playing play base anim
            if(m_thirdsPlaying.get(i)) {
                m_base.play(m_realChannels.get(i));
                m_thirdsPlaying.set(i, false);
            }
        }
        
        //set the third anim to null
        m_third = null;
        m_thirdStatusListener = null;
    }
    
    public void clearThirdAnim() {
        if(m_third == null) return;
        
        if(m_thirdStatusListener != null) m_thirdStatusListener.onAnimDone(false);
        
        //check all channels if a third is running
        for(int i = 0; i < m_realChannels.size(); i++) {
            //if third anim is playing play base anim
            if(m_thirdsPlaying.get(i)) {
                m_base.play(m_realChannels.get(i));
                m_thirdsPlaying.set(i, false);
            }
        }
        
        //set the third anim to null
        m_third = null;
        m_thirdStatusListener = null;
    }
    
    public void playThird(AnimLink anim, int[] channels, AnimStatusListener listener) {
        //abort third anim if there is one running
        abortThirdAnim();
        
        //play the third anim on all listed channels
        float duration = 0;
        for(int i : channels) {
            if(m_playThirds.get(i)) {
                anim.play(m_realChannels.get(i));
                m_thirdsPlaying.set(i, true);
                duration = m_realChannels.get(i).getAnimMaxTime();
            }
        }
        
        m_third = anim;
        m_thirdStatusListener = listener;
        if(m_thirdStatusListener != null) {
            m_thirdStatusListener.onAnimStart();
            m_thirdStatusListener.setEstimatedDuration(duration);
        }
    }
    
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        int channelindex = m_realChannels.indexOf(channel);
        if(m_thirdsPlaying.get(channelindex)) {
            //test if a base animation is running and on which channel
            int basePlaying = -1;
            for(int i = 0; i < m_thirdsPlaying.size(); i++) {
                if(m_thirdsPlaying.get(i)) {
                    basePlaying = i;
                    break;
                }
            }
            
            //set flag of thirdplaying to false
            m_thirdsPlaying.set(channelindex, false);
            
            //play base animation on this channel
            if(m_base != null) {
                m_base.play(channel);
                channel.setSpeed(m_speedCoefficient * m_base.getTweak());
            }
            
            //if theres already another base animation running synchronize it
            if(basePlaying != -1) channel.setTime(m_realChannels.get(basePlaying).getTime());
            
            if(!m_thirdsPlaying.contains(true)) clearThirdAnim();
        }
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    public void onMovementStateChanged(MovementState state) {
        setMovementState(state);
    }

    public void setBaseAnimSpeedCoefficient(float coefficient) {
        //if the coefficient does not change, dont do anything
        if(m_speedCoefficient == coefficient) return;
        
        //cache the coefficient
        m_speedCoefficient = coefficient;

        //if there is a base anim set update the speed of the channels playing it
        if(m_base != null) {
            for(int i = 0; i < m_realChannels.size(); i++) {
                
                //if this channel is playing the base anim, set the speed
                if(!m_thirdsPlaying.get(i)) {
                    m_realChannels.get(i).setSpeed(m_base.getTweak() * m_speedCoefficient);
                }
            }
        }
    }
}
