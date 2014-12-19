/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.player;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.LoopMode;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import de.agame.entitys.Entity;
import de.agame.entitys.EnviromentObservationSet;
import de.agame.entitys.SpatialControlSet;
import de.agame.entitys.UserInterfaceSet;

/**
 *
 * @author Fredie
 */
public class EntityPlayer extends Entity implements ActionListener{
    
    private boolean m_left = false;
    private boolean m_right = false;
    private boolean m_forward = false;
    private boolean m_backward = false;
    
    private boolean m_laststanding = true;
    private boolean m_jumping = false;
    private boolean m_sprinting = false;
    private AnimChannel m_channel;
    
    private Vector3f m_walkdirection = new Vector3f();
    
    private float m_walkspeed = 1000f;
    
    public EntityPlayer(Spatial spatial, SpatialControlSet spatialcontrolset, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset) {
        super(spatial, spatialcontrolset, enviromentobservationset, userinterfaceset);
        
        m_channel = spatialcontrolset.getAnimationControl().createChannel();
    }
    
    public void setWalkSpeed(float speed) {
        m_walkspeed = speed;
    }
    
    public float getWalkSpeed() {
        return m_walkspeed;
    }
    
    @Override
    public boolean mayDespawn() {
        return false;
    }
    
    @Override
    public void onAttach() {
        super.onAttach();
        
        InputManager inputmanager = m_userinterfaceset.getInputManager();
        
        inputmanager.addMapping("forward", new KeyTrigger(KeyInput.KEY_W));
        inputmanager.addMapping("backward", new KeyTrigger(KeyInput.KEY_S));
        inputmanager.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
        inputmanager.addMapping("right", new KeyTrigger(KeyInput.KEY_D));
        inputmanager.addMapping("jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputmanager.addMapping("sprint", new KeyTrigger(KeyInput.KEY_LSHIFT));
        
        inputmanager.addListener(this, "forward", "backward", "left", "right", "jump");
        
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        
        InputManager inputmanager = m_userinterfaceset.getInputManager();
        
        inputmanager.deleteMapping("forward");
        inputmanager.deleteMapping("backward");
        inputmanager.deleteMapping("left");
        inputmanager.deleteMapping("right");
        inputmanager.deleteMapping("jump");
        inputmanager.deleteMapping("sprint");
        
        inputmanager.removeListener(this);
        
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("forward")) m_forward = isPressed;
        else if(name.equals("backward")) m_backward = isPressed;
        else if(name.equals("left")) m_left = isPressed;
        else if(name.equals("right")) m_right = isPressed;
        else if(name.equals("jump") && isPressed) {
            m_spatialcontrolset.getMovementControl().jump();
            m_channel.setAnim("Springen");
            m_channel.setSpeed(0.5f);
            m_channel.setLoopMode(LoopMode.DontLoop);
            m_jumping = true;
        } else if(name.equals("sprint")) m_sprinting = isPressed;
    }
    
    @Override
    public void controlUpdate(float tpf) {
        super.controlUpdate(tpf);
        
        if(m_jumping && m_spatialcontrolset.getMovementControl().isOnGround()) {
            m_jumping = false;
            
            if(m_laststanding) {
                m_channel.setAnim("Stehen", 0.5f);
                m_channel.setSpeed(1.0f);
                m_channel.setLoopMode(LoopMode.Loop);
            } else {
                m_channel.setAnim("Laufen", 0.5f);
                m_channel.setSpeed(2.0f);
                m_channel.setLoopMode(LoopMode.Loop);
            }
        }
        
        Vector3f camDir = m_userinterfaceset.getCam().getDirection();
        Vector3f camLeft = m_userinterfaceset.getCam().getLeft();
        camDir.setY(0);
        camLeft.setY(0);
        camDir.normalizeLocal();
        camLeft.normalizeLocal();
        
        m_walkdirection.set(0, 0, 0);
        
        if(m_left) m_walkdirection.addLocal(camLeft);
        if(m_right) m_walkdirection.addLocal(camLeft.negate());
        if(m_forward) m_walkdirection.addLocal(camDir);
        if(m_backward) m_walkdirection.addLocal(camDir.negate());
        
        m_walkdirection.multLocal(m_walkspeed).multLocal(tpf);
        if(!m_jumping) m_spatialcontrolset.getMovementControl().setWalkDirection(m_walkdirection);
        
        if(m_walkdirection.length() != 0) {
            m_spatialcontrolset.getMovementControl().setViewDirection(m_walkdirection.normalize());
            
            if(m_laststanding && !m_jumping) {
                m_laststanding = false;
                m_channel.setAnim("Laufen", 0.5f);
                m_channel.setSpeed(2.0f);
                m_channel.setLoopMode(LoopMode.Loop);
            }
        } else if(!m_laststanding && !m_jumping) {
            m_laststanding = true;
            m_channel.setAnim("Stehen", 0.5f);
            m_channel.setSpeed(1.0f);
            m_channel.setLoopMode(LoopMode.Loop);
        }
    }
}
