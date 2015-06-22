/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.movement;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import de.agame.entitys.EntityLiving;
import de.agame.entitys.animation.AnimRequest;
import de.agame.misc.FloatInterpolator;
import de.agame.misc.QuarternionInterpolator;
import de.agame.misc.Value;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Fredie
 */
public class MovementManager {

    //Vectors indicating the coordinatesystems axes
    private final Vector3f m_xforward = new Vector3f(1, 0, 0);
    private final Vector3f m_zforward = new Vector3f(0, 0, 1);
    
    //the curret movementstate of the entity
    private MovementState m_state;
    
    //if the entity should be moving
    private boolean m_prevWalk = false;
    
    //how fast the entity should be moving
    private float m_prevSpeed = 0;

    //the listener listening to the movementstate
    private MovementStateChangeListener m_listener;
    
    //how fast the entity can turn
    private float m_turnspeed = 2.5f;
    
    //how fast the entity can walk
    private float m_walkspeed = 4.0f;
    
    //how fast the entity can sprint
    private float m_sprintspeed = 10.0f;
    
    //how fast the entity can crouch
    private float m_crouchspeed = 2.0f;
    
    //how fast the entity can crawl
    private float m_crawlspeed = 1.0f;
    
    //the time the entity has been in air
    private float m_timeInAir = 0;
    
    //a list containing all movementevents the entity can perform
    private ArrayList<MovementEvent> m_events = new ArrayList();
    
    //if it is possible to add or remove params
    private boolean m_paramsLocked = false;
    
    //a map containing all parameters needed for the event to work
    private HashMap<String, Value> m_params = new HashMap<String, Value>();
    
    //the movement event currently executed
    private MovementEvent m_currentEvent = null;
    
    //the spatial the animations are applied to
    private Spatial m_spatial;
    
    //the entity being animated by this movementmanager
    private EntityLiving m_living;
    
    //the interpolator used to interpolate speed
    private FloatInterpolator m_speedInterpolater;
    
    //the interpolator to interpolate quarternions
    private QuarternionInterpolator m_directionInterpolater;
    
    public MovementManager(Spatial spatial, EntityLiving living) {
        m_spatial = spatial;
        m_living = living;
        
        m_speedInterpolater = new FloatInterpolator();
        m_directionInterpolater = new QuarternionInterpolator();
        
        //add all parameters used by default
        m_params.put(BasicMovementParams.PARAM_IS_CROUCHING, new Value<Boolean>(false));
        m_params.put(BasicMovementParams.PARAM_IS_CRAWLING, new Value<Boolean>(false));
        m_params.put(BasicMovementParams.PARAM_IS_WALKING, new Value<Boolean>(false));
        m_params.put(BasicMovementParams.PARAM_IS_SPRINTING, new Value<Boolean>(false));
        m_params.put(BasicMovementParams.PARAM_IS_IN_AIR, new Value<Boolean>(false));
        m_params.put(BasicMovementParams.PARAM_SHOULD_JUMP, new Value<Boolean>(false));
        m_params.put(BasicMovementParams.PARAM_IS_MOVING, new Value<Boolean>(false));
        
        //init the default state
        m_state = new MovementState();
        m_state.setAction(MovementState.MovementAction.idle);
        m_state.setAdditionalArgument(MovementState.AdditionalMovementArg.walking);
        
        //set default movement action
        walk();
    }

    private void updateParams(MovementState state) {
        //get all the basic params from the state
        boolean walking = state.getAdditionalArg() == MovementState.AdditionalMovementArg.walking;
        boolean sprinting = state.getAdditionalArg() == MovementState.AdditionalMovementArg.sprinting;
        boolean crouching = state.getAdditionalArg() == MovementState.AdditionalMovementArg.crouching;
        boolean crawling = state.getAdditionalArg() == MovementState.AdditionalMovementArg.crawling;
        
        boolean moving = state.getAction() == MovementState.MovementAction.moving;
        boolean inAir = !state.onGround();
        
        //copy the state to the cached state used by this movementmanager
        m_state.setAction(state.getAction());
        m_state.setAdditionalArgument(state.getAdditionalArg());
        m_state.setOnGround(state.onGround());
        
        //update params if necessary
        if(((Value<Boolean>)m_params.get(BasicMovementParams.PARAM_IS_WALKING)).getValue() != walking)
                onParamUpdated(BasicMovementParams.PARAM_IS_WALKING, new Value<Boolean>(walking));
        if(((Value<Boolean>)m_params.get(BasicMovementParams.PARAM_IS_SPRINTING)).getValue() != sprinting)
            onParamUpdated(BasicMovementParams.PARAM_IS_SPRINTING, new Value<Boolean>(sprinting));
        if(((Value<Boolean>)m_params.get(BasicMovementParams.PARAM_IS_CROUCHING)).getValue() != crouching)
            onParamUpdated(BasicMovementParams.PARAM_IS_CROUCHING, new Value<Boolean>(crouching));
        if(((Value<Boolean>)m_params.get(BasicMovementParams.PARAM_IS_CRAWLING)).getValue() != crawling)
            onParamUpdated(BasicMovementParams.PARAM_IS_CRAWLING, new Value<Boolean>(crawling));
        if(((Value<Boolean>)m_params.get(BasicMovementParams.PARAM_IS_MOVING)).getValue() != moving)
            onParamUpdated(BasicMovementParams.PARAM_IS_MOVING, new Value<Boolean>(moving));
        if(((Value<Boolean>)m_params.get(BasicMovementParams.PARAM_IS_IN_AIR)).getValue() != inAir)
            onParamUpdated(BasicMovementParams.PARAM_IS_IN_AIR, new Value<Boolean>(inAir));
    }
    
    public MovementState getCurrentState() {
        return m_state;
    }
    
    public void setMovementStateChangeListener(MovementStateChangeListener listener) {
        m_listener = listener;
    }
    
    public void setTargetSpeed(float speed) {
        m_prevSpeed = speed;
    }
    
    public void setMovementDirection(Vector3f direction) {
        boolean shouldwalk = direction.lengthSquared() != 0;
        
        if(shouldwalk) {
            direction.normalizeLocal();
            float rangle = m_directionInterpolater.getCurrentValue().mult(m_xforward).angleBetween(direction);
            float angleabs = (float) Math.acos(direction.dot(m_xforward));
            float angle = direction.dot(m_zforward) > 0 ? -angleabs : angleabs;
            
            Quaternion q = new Quaternion();
            q.loadIdentity();
            q.fromAngles(0, angle, 0);
            
            m_directionInterpolater.setGoal(q, rangle / m_turnspeed);
        }
        
        m_prevWalk = shouldwalk;
    }
    
    public Vector3f getCurrentWalkDirection() {
        return m_directionInterpolater.getCurrentValue().mult(m_xforward).multLocal(m_speedInterpolater.getCurrentValue());
    }
    
    public Vector3f getCurrentViewDirection() {
        return m_directionInterpolater.getCurrentValue().mult(m_xforward);
    }
    
    public void setSprintSpeed(float speed) {
        m_sprintspeed = speed;
    }
    
    public void setWalkSpeed(float speed) {
        m_walkspeed = speed;
    }
       
    public void setCrouchSpeed(float speed) {
        m_crouchspeed = speed;
    }
    
    public void setCrawlSpeed(float speed) {
        m_crawlspeed = speed;
    }
    
    public float getSprintSpeed() {
        return m_sprintspeed;
    }
    
    public float getWalkSpeed() {
        return m_walkspeed;
    }
    
    public float getCrouchSpeed() {
        return m_crouchspeed;
    }
    
    public float getCrawlSpeed() {
        return m_crawlspeed;
    }
    
    public void sprint() {
        if(m_state.getAdditionalArg() != MovementState.AdditionalMovementArg.crawling)
            if(m_state.getAdditionalArg() != MovementState.AdditionalMovementArg.crouching)
                setTargetSpeed(m_sprintspeed);
    }
    
    public void walk() {
        if(m_state.getAdditionalArg() != MovementState.AdditionalMovementArg.crawling)
            if(m_state.getAdditionalArg() != MovementState.AdditionalMovementArg.crouching)
                setTargetSpeed(m_walkspeed);
    }
    
    public void crouch() {
        if(m_state.getAdditionalArg() == MovementState.AdditionalMovementArg.crouching) return;
        
        setTargetSpeed(m_crouchspeed);
        
        m_state.setAdditionalArgument(MovementState.AdditionalMovementArg.crouching);
        updateParams(m_state);
               
        if(m_listener != null)
            m_listener.onMovementStateChanged(m_state);
    }
    
    public void unCrouch() {
        if(m_state.getAdditionalArg() == MovementState.AdditionalMovementArg.crouching) {
            setTargetSpeed(m_walkspeed);

            m_state.setAdditionalArgument(MovementState.AdditionalMovementArg.walking);
            updateParams(m_state);

            if(m_listener != null)
                m_listener.onMovementStateChanged(m_state);
        }
    }
    
    public void crawl() {
        if(m_state.getAdditionalArg() == MovementState.AdditionalMovementArg.crawling) return;
        
        setTargetSpeed(m_crawlspeed);
        
        m_state.setAdditionalArgument(MovementState.AdditionalMovementArg.crawling);
        updateParams(m_state);
               
        if(m_listener != null)
            m_listener.onMovementStateChanged(m_state);
    }
    
    public void unCrawl() {
        if(m_state.getAdditionalArg() == MovementState.AdditionalMovementArg.crawling) {
            setTargetSpeed(m_walkspeed);

            m_state.setAdditionalArgument(MovementState.AdditionalMovementArg.walking);
            updateParams(m_state);

            if(m_listener != null)
                m_listener.onMovementStateChanged(m_state);
        }
    }
    
    public void jump() {
        onParamUpdated(BasicMovementParams.PARAM_SHOULD_JUMP, new Value<Boolean>(true));
    }
    
    public void setIsInAir(boolean flag) {
        m_state.setOnGround(!flag);
        updateParams(m_state);
        
        if(m_listener != null)
            m_listener.onMovementStateChanged(m_state);
    }
    
    public void ensureParam(String param, Value value) throws Exception {
        //if params are locked, throw an exception to remind the developer of unlocking the params first
        if (m_paramsLocked) {
            throw new Exception("params have to be unlocked to ensure params are available");
        }

        //put param into the map
        m_params.put(param, value);
    }

    public void lockParams() {
        m_paramsLocked = true;
    }

    public void unlockParams() {
        m_paramsLocked = false;
    }

    public void addEvent(MovementEvent event) throws Exception {
        //if params are locked, throw an exception to remind the developer of unlocking the params first
        if (m_paramsLocked) {
            throw new Exception("params have to be unlocked to add new Events");
        }

        //add the new event unless there is already one with the same name registered
        for (MovementEvent oevent : m_events) {
            if (oevent.getName().equals(event.getName())) {
                return;
            }
        }

        event.ensureParams(m_params);
        m_events.add(event);
    }

    public void removeEvent(String name) {
        //find event with the given name and remove it
        for (MovementEvent event : m_events) {
            if (name.equals(event.getName())) {
                m_events.remove(event);
                break;
            }
        }
    }

    public void onParamUpdated(String param, Value value) {
        //if the value of the param has not changed, dont do anything
        if (m_params.get(param).getValue() == value.getValue()) {
            return;
        }

        m_params.put(param, value);

        //if event currently running test if it is going to last
        if (m_currentEvent != null) {

            //get new event if current cannot be executed, as long as one can be executed or none is chosen
            while (!m_currentEvent.onMovementChanged(m_params)) {
                m_currentEvent = m_currentEvent.onEndEvent(m_params);
                if (m_currentEvent == null) {
                    break;
                }

                AnimRequest request = m_currentEvent.onStartEvent(m_params, m_living.getSpatialControl().getMovementControl());
                
                if(m_listener != null)
                    m_listener.handleAnimRequest(request);
            }

            //if there is already a new active event chosen skip choosing one from the list
            if (m_currentEvent != null) {
                return;
            }
        }

        //if no event is currently executed try to choose one from the registered events
        for (MovementEvent event : m_events) {
            if (event.executeEvent(m_params)) {
                m_currentEvent = event;
                AnimRequest request = m_currentEvent.onStartEvent(m_params, m_living.getSpatialControl().getMovementControl());
                
                if(m_listener != null)
                    m_listener.handleAnimRequest(request);
                
                break;
            }
        }
    }

    public void onUpdate(float dt) {
        //update speed and direction
        if(m_state.onGround()) {
            if(m_state.getAction() != MovementState.MovementAction.idle)
                m_directionInterpolater.update(dt);
            m_speedInterpolater.update(dt);
            m_timeInAir = 0;
        } else {
            m_timeInAir += dt;
        }
        
        //update movement
        if(!m_prevWalk && m_speedInterpolater.getGoal() != 0) {
            float time = m_speedInterpolater.getCurrentValue() / 15.0f;
            m_speedInterpolater.setGoal(0, time);
        } else if(m_prevWalk && m_speedInterpolater.getGoal() != m_prevSpeed) {
            float time = m_prevSpeed - m_speedInterpolater.getCurrentValue();
            time = time > 0 ? time / 10.0f : time / 15.0f;
            time = Math.abs(time);
            
            m_speedInterpolater.setGoal(m_prevSpeed, time);
        }
        
        //update movement state
        if(m_speedInterpolater.getCurrentValue() != 0 && m_state.getAction() == MovementState.MovementAction.idle) {
            m_state.setAction(MovementState.MovementAction.moving);
            updateParams(m_state);
                   
            if(m_listener != null)
                m_listener.onMovementStateChanged(m_state);
        } else if (m_speedInterpolater.getCurrentValue() == 0 && m_state.getAction() == MovementState.MovementAction.moving) {
            m_state.setAction(MovementState.MovementAction.idle);
            updateParams(m_state);
            
                   
            if(m_listener != null)
                m_listener.onMovementStateChanged(m_state);
        }
        
        if(m_state.getAction() == MovementState.MovementAction.moving) {
            if(m_state.getAdditionalArg() != MovementState.AdditionalMovementArg.crawling) {
                if(m_state.getAdditionalArg() != MovementState.AdditionalMovementArg.crouching) {
                    MovementState.AdditionalMovementArg prevArg = MovementState.AdditionalMovementArg.walking;
                    if(m_speedInterpolater.getCurrentValue() > m_walkspeed) prevArg = MovementState.AdditionalMovementArg.sprinting;
                    
                    if(m_state.getAdditionalArg() != prevArg) {
                        m_state.setAdditionalArgument(prevArg);
                        updateParams(m_state);
                        
                        if(m_listener != null)
                            m_listener.onMovementStateChanged(m_state);
                    }
                }
            }
            
        }
        
        //update anim speed coefficient
        if(m_state.getAction() == MovementState.MovementAction.moving && m_state.onGround()) {
            float prevspeed = 0.0f;
            
            if(m_state.getAdditionalArg() == MovementState.AdditionalMovementArg.crawling) prevspeed = m_crawlspeed;
            if(m_state.getAdditionalArg() == MovementState.AdditionalMovementArg.crouching) prevspeed = m_crouchspeed;
            if(m_state.getAdditionalArg() == MovementState.AdditionalMovementArg.walking) prevspeed = m_walkspeed;
            if(m_state.getAdditionalArg() == MovementState.AdditionalMovementArg.sprinting) prevspeed = m_sprintspeed;
            
            if(prevspeed != 0.0f) {
                float coefficient = m_speedInterpolater.getCurrentValue() / prevspeed;
                coefficient = coefficient > 0.5f ? coefficient : 0.5f;
                
                m_listener.setBaseAnimSpeedCoefficient(coefficient);
            }
        } else {
            m_listener.setBaseAnimSpeedCoefficient(1.0f);
        }
    }
}
