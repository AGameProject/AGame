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
public class MovmementFlip implements MovementEvent {

    private AnimLink m_flipanim;
    
    private float m_flipangle = 0;
    private float m_flipduration = 0.75f;
    
    public void setFlipDuration(float time) {
        m_flipduration = time;
    }
    
    public void setAnims(AnimLink... anims) {
    }

    public String getName() {
        return "flip";
    }

    public void ensureParams(HashMap<String, Boolean> params) {
        params.put("isinAir", false);
        params.put("crouching", false);
    }

    public boolean executeEvent(HashMap<String, Boolean> params) {
        if(params.get("isinAir") && params.get("crouching")) return true;
        
        return false;
    }

    public boolean onMovementChanged(HashMap<String, Boolean> params) {
        if(!params.get("crouching")) return false;
        if(!params.get("isinAir")) return false;
        
        return true;
    }

    public void onUpdate(float dt, EntityLivingAnimated entity, Spatial spatial) {
        m_flipangle += 360.0f * dt / m_flipduration;
    }

    public void onStartEvent(HashMap<String, Boolean> params, Spatial spatial, EntityLivingAnimated living) {
        living.playMinorAnim(m_flipanim, true);
        m_flipangle = 0;
    }

    public MovementEvent onEndEvent(HashMap<String, Boolean> params, Spatial spatial, EntityLivingAnimated living) {
        return null;
    }
    
}
