/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.movement;

import com.jme3.scene.Spatial;
import de.agame.entitys.animation.AnimLink;
import de.agame.entitys.EntityLivingAnimated;
import de.agame.misc.Value;
import java.util.HashMap;

/**
 *
 * @author Fredie
 */
public class MovementJump implements MovementEvent {

    private AnimLink m_jumpAnim;
    
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

    public void onUpdate(float dt, EntityLivingAnimated entity, Spatial spatial) {
        //do nothing here
    }

    public void onStartEvent(HashMap<String, Value> params, Spatial spatial, EntityLivingAnimated living) {
        living.getSpatialControl().getMovementControl().jump();
        living.playMinorAnim(m_jumpAnim, true);
    }

    public MovementEvent onEndEvent(HashMap<String, Value> params, Spatial spatial, EntityLivingAnimated living) {
        return null;
    }
    
}
