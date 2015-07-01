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
public class MovementJump extends MovementEvent {

    private AnimLink m_jumpAnim;
    
    public MovementJump(AnimationManager animmanager) {
        super(animmanager, new String[]{"LEGS"});
    }
    
    public void setAnims(AnimLink... anims) {
        m_jumpAnim = anims[0];
    }

    public String getName() {
        return "jump";
    }

    public void ensureParams(HashMap<String, Value> params) {
        params.put(BasicMovementParams.PARAM_SHOULD_JUMP, new Value<Boolean>(false));
        params.put(BasicMovementParams.PARAM_IS_IN_AIR, new Value<Boolean>(false));
    }

    public boolean executeEvent(HashMap<String, Value> params) {
        if((Boolean) params.get(BasicMovementParams.PARAM_SHOULD_JUMP).getValue() && !((Boolean) params.get(BasicMovementParams.PARAM_IS_IN_AIR).getValue())) {
            params.put(BasicMovementParams.PARAM_SHOULD_JUMP, new Value<Boolean>(false));
            return true;
        }
        
        return false;
    }

    public boolean onMovementChanged(HashMap<String, Value> params) {
        return false;
    }

    public AnimRequest onStartEvent(HashMap<String, Value> params, BetterCharacterControl control) {
        control.jump();
        
        return new AnimRequest(m_jumpAnim, getChannels());
    }

    public MovementEvent onEndEvent(HashMap<String, Value> params) {
        return null;
    }
    
}
