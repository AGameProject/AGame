/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.movement;

import com.jme3.scene.Spatial;
import de.agame.entitys.EntityLivingAnimated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Fredie
 */
public class MovementManager {
    
    private ArrayList<MovementEvent> m_events = new ArrayList();
    
    private boolean m_paramsLocked = false;
    private HashMap<String, Boolean> m_params = new HashMap<String, Boolean>();
    
    private MovementEvent m_currentEvent = null;
    
    private Spatial m_spatial;
    private EntityLivingAnimated m_living;
    
    public MovementManager(Spatial spatial, EntityLivingAnimated living) {
        m_spatial = spatial;
        m_living = living;
    }
    
    public void ensureParams(String... params) throws Exception {
        //if params are locked, throw an exception to remind the developer of unlocking the params first
        if(m_paramsLocked) throw new Exception("params have to be unlocked to ensure params are available");
        
        //put all params into the map
        for(String param : params) {
            m_params.put(param, false);
        }
    }
    
    public void lockParams() {
        resetParams();
        
        m_paramsLocked = true;
    }
    
    public void unlockParams() {
        m_paramsLocked = false;
    }
    
    public void resetParams() {
        Iterator<String> pi = m_params.keySet().iterator();
        
        while(pi.hasNext()) {
            m_params.put(pi.next(), false);
        }
    }
    
    public void addEvent(MovementEvent event) throws Exception {
        //if params are locked, throw an exception to remind the developer of unlocking the params first
        if(m_paramsLocked) throw new Exception("params have to be unlocked to add new Events");
        
        //add the new event unless there is already one with the same name registered
        for(MovementEvent oevent : m_events) {
            if(oevent.getName().equals(event.getName())) return;
        }

        event.ensureParams(m_params);
        m_events.add(event);
    }
    
    public void removeEvent(String name) {
        //find event with the given name and remove it
        for(MovementEvent event : m_events) {
            if(name.equals(event.getName())) {
                m_events.remove(event);
                break;
            }
        }
    }
    
    public void onParamUpdated(String param, boolean value) {
        //if the value of the param has not changed, dont do anything
        if(m_params.get(param) == value) return;
        
        m_params.put(param, value);
        
        //if event currently running test if it is going to last
        if(m_currentEvent != null) {
            
            //get new event if current cannot be executed, as long as one can be executed or none is chosen
            while(!m_currentEvent.onMovementChanged(m_params)) {
                m_currentEvent = m_currentEvent.onEndEvent(m_params, m_spatial, m_living);
                if(m_currentEvent == null) break;
                
                m_currentEvent.onStartEvent(m_params, m_spatial, m_living);
            }
            
            //if there is already a new active event chosen skip choosing one from the list
            if(m_currentEvent != null) return;
        }
        
        //if no event is currently executed try to choose one from the registered events
        for(MovementEvent event : m_events) {
            if(event.executeEvent(m_params)) {
                m_currentEvent = event;
                m_currentEvent.onStartEvent(m_params, m_spatial, m_living);
                break;
            }
        }
    }
    
    public void onUpdate(float dt) {
        if(m_currentEvent != null)
            m_currentEvent.onUpdate(dt, m_living, m_spatial);
    }
}
