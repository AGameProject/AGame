/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.movement;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import de.agame.entitys.animation.AnimLink;
import de.agame.entitys.EntityLivingAnimated;
import de.agame.misc.Value;
import java.util.HashMap;

/**
 *
 * @author Fredie
 */
public class MovementFlip implements MovementEvent {

    private AnimLink m_flipanim;

    private float m_flipangle = 0;
    private float m_flipduration = 0.75f;
    
    private Quaternion m_initrotation;
    
    public void setFlipDuration(float time) {
        m_flipduration = time;
    }
    
    public void setAnims(AnimLink... anims) {
        m_flipanim = anims[0];
    }

    public String getName() {
        return "flip";
    }

    public void ensureParams(HashMap<String, Value> params) {
    }

    public boolean executeEvent(HashMap<String, Value> params) {
        if((Boolean) params.get(BasicMovementParams.PARAM_IS_IN_AIR).getValue() && (Boolean) params.get(BasicMovementParams.PARAM_IS_CROUCHING).getValue()) return true;
        
        return false;
    }

    public boolean onMovementChanged(HashMap<String, Value> params) {
        if(!((Boolean) params.get(BasicMovementParams.PARAM_IS_CROUCHING).getValue())) return false;
        if(!((Boolean) params.get(BasicMovementParams.PARAM_IS_IN_AIR).getValue())) return false;
        
        return true;
    }

    public void onUpdate(float dt, EntityLivingAnimated entity, Spatial spatial) {
        m_flipangle += 360.0f * dt / m_flipduration;
        
        Vector3f left = new Vector3f(1, 0, 0);
        left = m_initrotation.mult(left);
        
        Quaternion rotation = new Quaternion();
        rotation.fromAngleAxis(m_flipangle, left);
        
//        spatial.setLocalRotation(rotation);
        
    }

    public void onStartEvent(HashMap<String, Value> params, Spatial spatial, EntityLivingAnimated living) {
        living.playMinorAnim(m_flipanim, true);
        m_flipangle = 0;
        
        m_initrotation = new Quaternion(spatial.getLocalRotation());
    }

    public MovementEvent onEndEvent(HashMap<String, Value> params, Spatial spatial, EntityLivingAnimated living) {
        spatial.setLocalRotation(m_initrotation);
        
        if((Boolean) params.get(BasicMovementParams.PARAM_IS_IN_AIR).getValue()) living.playMinorAnim(living.getFallAnim(), true);
        
        return null;
    }
    
}
