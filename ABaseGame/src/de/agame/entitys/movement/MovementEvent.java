/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.movement;

import com.jme3.bullet.control.BetterCharacterControl;
import de.agame.entitys.animation.AnimLink;
import de.agame.entitys.animation.AnimRequest;
import de.agame.entitys.animation.AnimationManager;
import de.agame.misc.Value;
import java.util.HashMap;

/**
 *
 * @author Fredie
 */
public abstract class MovementEvent {
    
    private int m_channels[];
    
    public MovementEvent(AnimationManager animmanager, String channeltags[]) {
        m_channels = animmanager.getChannels(channeltags);
    }
    
    public int[] getChannels() {
        return m_channels;
    }
    
    public abstract void setAnims(AnimLink... anims);

    public abstract String getName();
    
    /**
     * Puts all the params into the given HashMap to make sure they are available at runtime
     * @param params 
     */
    public abstract void ensureParams(HashMap<String, Value> params);

    /**
     * checks if the movementevent should be executed for a given set of params
     * @param params
     * @return true if the event should be executed
     */
    public abstract boolean executeEvent(HashMap<String, Value> params);
    
    /**
     * checks if the movement event is still executed for a given set of params
     * @param params
     * @return true if the movement event should be continued
     */
    public abstract boolean onMovementChanged(HashMap<String, Value> params);
    
    /**
     * Called when movementevent is started
     */
    public abstract AnimRequest onStartEvent(HashMap<String, Value> params, BetterCharacterControl control);
    
    /**
     * Called when movement event is concluded
     * @return the following event (e.g. stumbling if roll was not successful)
     */
    public abstract MovementEvent onEndEvent(HashMap<String, Value> params);
}
