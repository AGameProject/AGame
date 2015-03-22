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
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import de.agame.misc.QuarternionInterpolator;
import de.agame.misc.FloatInterpolator;
import java.util.ArrayList;

/**
 *
 * @author Fredie
 */
public class EntityLivingAnimated extends EntityLiving implements AnimEventListener{

    private Vector3f m_xforward = new Vector3f(1, 0, 0);
    private Vector3f m_zforward = new Vector3f(0, 0, 1);
    
    private AnimLink m_walkanim;
    private AnimLink m_sprintanim;
    private AnimLink m_idleanim;
    private AnimLink m_jumpanim;
    private AnimLink m_fallanim;
    private AnimLink m_rollanim;
    
    private boolean m_walking = false;
    private float m_walkspeed = 4.0f;
    private boolean m_sprinting = false;
    private float m_sprintspeed = 10.0f;
    private boolean m_isinAir = false;
    private float m_timeinAir = 0;
    private boolean m_crouching = false;
    private float m_crouchspeed = 2.0f;
    private boolean m_rolling = false;
    
    private boolean m_viewfwalk = true;
    
    private float m_ltpf;
    private FloatInterpolator m_wslerp = new FloatInterpolator();
    private float m_turnspeed = 2.0f;
    private QuarternionInterpolator m_wdlerp = new QuarternionInterpolator();
    
    private AnimLink m_cminoranim;
    private ArrayList<AnimChannel> m_animchannels = new ArrayList<AnimChannel>();
    private ArrayList<Boolean> m_caplaying = new ArrayList<Boolean>();
    
    public EntityLivingAnimated(Spatial spatial, SpatialControlSet scset, EnviromentObservationSet eoset, UserInterfaceSet uiset) {
        super(spatial, scset, eoset, uiset);
        scset.getAnimationControl().addListener(this);
    }
    
    private Vector3f calcWalkDirection() {
        return m_wdlerp.getCurrentValue().mult(m_xforward).multLocal(m_wslerp.getCurrentValue());
    }
    
    public void playMinorAnim(AnimLink anim, boolean log) {
        for(int i = 0; i < m_animchannels.size(); i++) {
            if(!m_caplaying.get(i)) anim.play(m_animchannels.get(i));
        }
        
        if(log) m_cminoranim = anim;
    }
    
    public void setAnimChannel(AnimChannel channel, int pos) {
        if(m_animchannels.size() - 1 < pos) {
            for(int i = m_animchannels.size(); i <= pos; i++) {
                m_animchannels.add(null);
                m_caplaying.add(false);
            }
        }
        
        m_animchannels.set(pos, channel);
    }
    
    
    public void playCustomAnim(AnimLink anim, int channel) {
        anim.play(m_animchannels.get(channel));
        m_caplaying.set(channel, true);
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
    
    public void setFallAnim(AnimLink anim) {
        m_fallanim = anim;
    }
    
    public AnimLink getFallAnim() {
        return m_fallanim;
    }
    
    public void setViewDirection(Vector3f dir) {
        m_viewfwalk = dir == null;
        if(!m_viewfwalk) m_spatialcontrolset.getMovementControl().setViewDirection(dir.normalizeLocal());
    }
    
    public void setWalkDirection(Vector3f dir) {
        boolean shouldwalk = dir.lengthSquared() != 0;
        
        
        if(shouldwalk) {
            dir.normalizeLocal();
            float rangle = m_wdlerp.getCurrentValue().mult(m_xforward).angleBetween(dir);
            float angleabs = (float) Math.acos(dir.dot(m_xforward));
            float angle = dir.dot(m_zforward) > 0 ? -angleabs : angleabs;
            
            Quaternion q = new Quaternion();
            q.loadIdentity();
            q.fromAngles(0, angle, 0);
            
            m_wdlerp.setGoal(q, rangle / m_turnspeed);
            
            dir = calcWalkDirection();
            
            if(m_viewfwalk) m_spatialcontrolset.getMovementControl().setViewDirection(dir);
            m_spatialcontrolset.getMovementControl().setWalkDirection(dir);
        }
        
        if(!m_walking && shouldwalk) {
            if(m_sprinting) {
                m_wslerp.setGoal(m_sprintspeed, 1.0f);
                if(!m_isinAir) playMinorAnim(m_sprintanim, true);
            } else {
                m_wslerp.setGoal(m_walkspeed, 0.4f);
                if(!m_isinAir) playMinorAnim(m_walkanim, true);
            }
        } else if(m_walking && !shouldwalk) {
            if(m_sprinting) m_wslerp.setGoal(0, 0.3f);
            else m_wslerp.setGoal(0, 0.15f);
            if(!m_isinAir) playMinorAnim(m_idleanim, true);
        }
        
        m_walking = shouldwalk;
    }
    
    public void jump() {
        if(m_isinAir) return;
        
        playMinorAnim(m_jumpanim, false);
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
        if(m_walking) {
            if(!m_sprinting && sprint) {
                if(!m_isinAir) playMinorAnim(m_sprintanim, true);
                m_wslerp.setGoal(m_sprintspeed, 0.5f);
            } else if(m_sprinting && !sprint){
                if(!m_isinAir) playMinorAnim(m_walkanim, true);
                m_wslerp.setGoal(m_walkspeed, 0.5f);
            }
        }

        m_sprinting = sprint;
    }
    
    public boolean isSprinting() {
        return m_sprinting;
    }
    
    public void setCrouching(boolean crouch) {
        
    }
    
    public boolean isCrouching() {
        return m_crouching;
    }
    
    public void setRolling(boolean rolling) {
        if(!m_isinAir && rolling) return;
        if(m_rolling == rolling) return;
        
        if(m_isinAir && !m_rolling) {
            playMinorAnim(m_rollanim, true);
        }
    }
    
    public boolean isRolling() {
        return m_rolling;
    }
    
    @Override
    public void complexAIUpdate(float elapse) {
    }

    @Override
    public void simpleUpdate(float tpf) {
        m_wslerp.update(tpf);
        m_wdlerp.update(tpf);
        
        boolean isinAir = !m_spatialcontrolset.getMovementControl().isOnGround();
        if(m_isinAir && !isinAir) {
            if(m_walking) {
                if(!m_sprinting) playMinorAnim(m_walkanim, true);
                else playMinorAnim(m_sprintanim, true);
            } else playMinorAnim(m_idleanim, true);
            m_timeinAir = 0;
        } else if(!m_isinAir && isinAir) {
            m_timeinAir = 0;
            playMinorAnim(m_fallanim, true);
        }
        
        m_isinAir = isinAir;
        if(m_isinAir) {
            m_timeinAir += tpf;
            for(AnimChannel channel : m_animchannels) {
                if(channel.getAnimationName().equals(m_fallanim.getName()))
                    channel.setSpeed(Math.min(m_timeinAir * 4.0f, 7.0f));
            }
        }
        
        
        Vector3f walkdir = calcWalkDirection();
        m_spatialcontrolset.getMovementControl().setWalkDirection(walkdir);
        if(m_viewfwalk && walkdir.lengthSquared() != 0) m_spatialcontrolset.getMovementControl().setViewDirection(walkdir);
        
        m_ltpf = tpf;
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        int index = m_animchannels.indexOf(channel);
        if(m_caplaying.get(index)) {
            m_cminoranim.play(channel);
            m_caplaying.set(index, false);
        }
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        if(animName == null) return;
        if(!animName.equals(m_walkanim.getName()) && !animName.equals(m_sprintanim.getName()) && !animName.equals(m_idleanim.getName()) && !animName.equals(m_fallanim.getName())) {
            m_caplaying.set(m_animchannels.indexOf(channel), true);
        }
        else {
            m_caplaying.set(m_animchannels.indexOf(channel), false);
        }
    }
}
