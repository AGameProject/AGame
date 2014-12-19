/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.LoopMode;
import com.jme3.scene.Spatial;
import java.util.HashMap;

/**
 *
 * @author Fredie
 */
public class EntityLivingAnimated extends EntityLiving{

    private HashMap<String, AnimLink> m_camap = new HashMap<String, AnimLink>();
    
    private AnimLink m_walkanim;
    private AnimLink m_sprintanim;
    private AnimLink m_idleanim;
    private AnimLink m_jumpanim;
    
    private boolean m_lastwalking = false;
    private boolean m_walking = false;
    
    private AnimChannel m_animchannel;
    
    public EntityLivingAnimated(Spatial spatial, SpatialControlSet scset, EnviromentObservationSet eoset, UserInterfaceSet uiset) {
        super(spatial, scset, eoset, uiset);
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
    
    @Override
    public void complexAIUpdate(float elapse) {
    }

    @Override
    public void simpleUpdate(float tpf) {
        if(m_walking && !m_lastwalking) {
            m_walkanim.play(m_animchannel);
            m_lastwalking = true;
        } else if(!m_walking && m_lastwalking) {
            m_idleanim.play(m_animchannel);
            m_lastwalking = false;
        }
    }
}
