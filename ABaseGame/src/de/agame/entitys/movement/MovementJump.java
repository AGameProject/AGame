/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.movement;

import com.jme3.scene.Spatial;
import de.agame.entitys.AnimLink;
import de.agame.entitys.EntityLivingAnimated;
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

    public void ensureParams(HashMap<String, Boolean> params) {
        params.put(BasicMovementParams.PARAM_SHOULD_JUMP, false);
        params.put(BasicMovementParams.PARAM_IS_IN_AIR, false);
    }

    public boolean executeEvent(HashMap<String, Boolean> params) {
        if(params.get(BasicMovementParams.PARAM_SHOULD_JUMP) && !params.get(BasicMovementParams.PARAM_IS_IN_AIR)) {
            params.put(BasicMovementParams.PARAM_SHOULD_JUMP, false);
            return true;
        }
        
        return false;
    }

    public boolean onMovementChanged(HashMap<String, Boolean> params) {
        return false;
    }

    public void onUpdate(float dt, EntityLivingAnimated entity, Spatial spatial) {
        //do nothing here
    }

    public void onStartEvent(HashMap<String, Boolean> params, Spatial spatial, EntityLivingAnimated living) {
        living.getSpatialControl().getMovementControl().jump();
        living.playMinorAnim(m_jumpAnim, false);
    }

    public MovementEvent onEndEvent(HashMap<String, Boolean> params, Spatial spatial, EntityLivingAnimated living) {
        return null;
    }
    
}
