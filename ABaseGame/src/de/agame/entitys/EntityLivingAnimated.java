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
import de.agame.misc.FloatInterpolator;
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
    private float m_walkspeed = 1000.0f;
    private boolean m_sprinting = false;
    private float m_sprintspeed = 2000.0f;
    private boolean m_isinAir = false;
    private boolean m_caplaying = false;
    
    private float m_ltpf;
    private FloatInterpolator m_wslerp = new FloatInterpolator();
    
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
        boolean shouldwalk = dir.lengthSquared() != 0;
        
        
        if(shouldwalk) {
            dir.normalizeLocal();
            m_spatialcontrolset.getMovementControl().setViewDirection(dir);
            dir.multLocal(m_ltpf * m_wslerp.getCurrentValue());
            m_spatialcontrolset.getMovementControl().setWalkDirection(dir);
        }
        
        if(!m_walking && shouldwalk) {
            if(m_sprinting) {
                m_wslerp.setGoal(m_sprintspeed, 1.0f);
                if(!m_caplaying) m_sprintanim.play(m_animchannel);
            } else {
                m_wslerp.setGoal(m_walkspeed, 0.4f);
                if(!m_caplaying) m_walkanim.play(m_animchannel);
            }
        } else if(m_walking && !shouldwalk) {
            if(m_sprinting) m_wslerp.setGoal(0, 0.3f);
            else m_wslerp.setGoal(0, 0.15f);
            if(!m_caplaying) m_idleanim.play(m_animchannel);
        }
        
        m_walking = shouldwalk;
    }
    
    public void jump() {
        m_jumpanim.play(m_animchannel);
        m_spatialcontrolset.getMovementControl().jump();
    }
    
    public void setWalkSpeed(float speed) {
        m_walkspeed = speed;
    }
    
    public float getWalkSpeed() {
        return m_walkspeed;
    }
    
    public void setSprintSpeed(float speed) {
        m_sprintspeed = speed;
    }
    
    public float getSprintSpeed() {
        return m_sprintspeed;
    }
    
    public void setSprinting(boolean sprint) {
        m_sprinting = sprint;
        
        if(m_walking) {
            if(m_sprinting) {
                m_sprintanim.play(m_animchannel);
                m_wslerp.setGoal(m_sprintspeed, 0.5f);
            } else {
                m_walkanim.play(m_animchannel);
                m_wslerp.setGoal(m_walkspeed, 0.5f);
            }
            
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
        m_wslerp.update(tpf);
//        m_isinAir = !m_spatialcontrolset.getMovementControl().isOnGround();
        Vector3f wdir = m_spatialcontrolset.getMovementControl().getWalkDirection().normalizeLocal().mult(tpf * m_wslerp.getCurrentValue());
        m_spatialcontrolset.getMovementControl().setWalkDirection(wdir);
        
        m_ltpf = tpf;
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
