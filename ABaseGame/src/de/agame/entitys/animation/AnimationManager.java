/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.animation;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import java.util.ArrayList;

/**
 *
 * @author Fredie
 */
public class AnimationManager implements AnimEventListener{
    
    //the basic movement of the character (as set by movement manager
    private AnimLink m_base;
    
    //the animation on top of movement (like attack and such)
    private AnimLink m_third;
    
    //a channel as reference for ending third animations
    private AnimChannel m_referenceChannel;
    
    //all channels that are actually on the model
    private ArrayList<AnimChannel> m_realChannels;
    private ArrayList<Boolean> m_playThirds;
    
    public AnimationManager() {
        //base and third are null as they arent used
        m_base = null;
        m_third = null;
        
        //create the arraylist to store the animchannels in
        m_realChannels = new ArrayList<AnimChannel>();
        m_playThirds = new ArrayList<Boolean>();
    }
    
    public void addChannel(AnimChannel channel, boolean playThirds) {
        m_realChannels.add(channel);
        m_playThirds.add(playThirds);
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }
}
