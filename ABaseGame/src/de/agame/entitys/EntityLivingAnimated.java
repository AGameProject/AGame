/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import de.agame.entitys.sets.UserInterfaceSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.EnviromentObservationSet;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.HashMap;

/**
 *
 * @author Fredie
 */
public class EntityLivingAnimated extends EntityLiving implements AnimEventListener{

    private HashMap<String, AnimLink> m_camap = new HashMap<String, AnimLink>();
    
    private AnimLink m_walkanim;
    private AnimLink m_sprintanim;
    private AnimLink m_idleanim;
    private AnimLink m_jumpanim;
    
    private boolean m_walking = false;
    private boolean m_sprinting = false;
    private float m_sprintfactor = 2.0f;
    private boolean m_isinAir = false;
    private boolean m_caplaying = false;
    
    private AnimChannel m_animchannel;
    
    public EntityLivingAnimated(Spatial spatial, SpatialControlSet scset, EnviromentObservationSet eoset, UserInterfaceSet uiset) {
        super(spatial, scset, eoset, uiset);
        
        m_animchannel = scset.getAnimationControl().createChannel();
        scset.getAnimationControl().addListener(this);
    }
    
    public void setWalkAnim(AnimLink anim) {
        m_walkanim = anim;
    }
    
    public AnimLink getWalkAnim() {
        return m_walkanim;
    }
    
    public void setSprintAnim(AnimLink anim) {
        m_sprintanim = anim;
    }
    
    public AnimLink getSprintAnim() {
        return m_sprintanim;
    }
    
    public void setIdleAnim(AnimLink anim) {
        m_idleanim = anim;
    }
    
    public AnimLink getIdleAnim() {
        return m_idleanim;
    }
    
    public void setJumpAnim(AnimLink anim) {
        m_jumpanim = anim;
    }
    
    public AnimLink getJumpAnim() {
        return m_sprintanim;
    }
    
    public void setCustomAnim(AnimLink anim) {
        m_camap.put(anim.getName(), anim);
    }
    
    public void removeCustomAnim(AnimLink anim) {
        m_camap.remove(anim.getName());
    }
    
    public void setWalkDirection(Vector3f dir) {
        if(m_sprinting) dir.multLocal(m_sprintfactor);
        m_spatialcontrolset.getMovementControl().setWalkDirection(dir);
        
        boolean shouldwalk = dir.lengthSquared() != 0;
        if(shouldwalk) m_spatialcontrolset.getMovementControl().setViewDirection(dir.normalize());
        
        if(!m_caplaying) {
            if(!m_walking && shouldwalk) {
                if(m_sprinting) m_sprintanim.play(m_animchannel);
                else m_walkanim.play(m_animchannel);
            } else if(m_walking && !shouldwalk) {
                m_idleanim.play(m_animchannel);
            }
        }
        
        m_walking = shouldwalk;
    }
    
    public void jump() {
        m_jumpanim.play(m_animchannel);
        m_spatialcontrolset.getMovementControl().jump();
    }
    
    public void setSprinting(boolean sprint) {
        m_sprinting = sprint;
        
        if(m_walking) {
            if(m_sprinting) m_sprintanim.play(m_animchannel);
            else m_walkanim.play(m_animchannel);
        }
    }
    
    public boolean isSprinting() {
        return m_sprinting;
    }
    
    @Override
    public void complexAIUpdate(float elapse) {
    }

    @Override
    public void simpleUpdate(float tpf) {
        m_isinAir = !m_spatialcontrolset.getMovementControl().isOnGround();
        
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if(m_caplaying) {
            if(m_walking) {
                if(m_sprinting) m_sprintanim.play(channel);
                else m_walkanim.play(channel);
            } else m_idleanim.play(channel);
        }
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        if(!animName.equals(m_walkanim.getName()) && !animName.equals(m_sprintanim.getName()) && !animName.equals(m_idleanim.getName()))
            m_caplaying = true;
        else m_caplaying = false;
    }
}
