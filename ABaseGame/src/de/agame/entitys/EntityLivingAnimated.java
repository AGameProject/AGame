/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import de.agame.entitys.animation.AnimLink;
import de.agame.entitys.sets.UserInterfaceSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.EnviromentObservationSet;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import de.agame.entitys.movement.BasicMovementParams;
import de.agame.entitys.movement.MovementManager;
import de.agame.misc.Value;
import java.util.ArrayList;

/**
 *
 * @author Fredie
 */
public class EntityLivingAnimated extends EntityLiving implements AnimEventListener{

    private AnimLink m_walkanim;
    private AnimLink m_sprintanim;
    private AnimLink m_idleanim;
    private AnimLink m_fallanim;
    
    private boolean m_viewfwalk = true;
    
    private AnimLink m_cminoranim;
    private ArrayList<AnimChannel> m_animchannels = new ArrayList<AnimChannel>();
    private ArrayList<Boolean> m_caplaying = new ArrayList<Boolean>();
    
    private MovementManager m_movementManager;
    
    public EntityLivingAnimated(Spatial spatial, SpatialControlSet scset, EnviromentObservationSet eoset, UserInterfaceSet uiset) {
        super(spatial, scset, eoset, uiset);
        scset.getAnimationControl().addListener(this);
        
        m_movementManager = new MovementManager(getSpatial(), this);
        m_movementManager.lockParams();
    }
    
    public MovementManager getMovementManager() {
        return m_movementManager;
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
        m_movementManager.setMovementDirection(dir);
    }
    
    public void jump() {
        m_movementManager.onParamUpdated(BasicMovementParams.PARAM_SHOULD_JUMP, new Value<Boolean>(true));
    }
    
    @Override
    public void complexAIUpdate(float elapse) {
    }

    @Override
    public void simpleUpdate(float tpf) {
        boolean isinAir = !m_spatialcontrolset.getMovementControl().isOnGround();
        
        m_movementManager.setIsInAir(isinAir);
        
        m_movementManager.onUpdate(tpf);
        
        Vector3f walkdir = m_movementManager.getCurrentWalkDirection();
        m_spatialcontrolset.getMovementControl().setWalkDirection(walkdir);
        if(m_viewfwalk && walkdir.lengthSquared() != 0) m_spatialcontrolset.getMovementControl().setViewDirection(walkdir.normalize());
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
