/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.movement;

import java.util.ArrayList;

/**
 *
 * @author Fredie
 */
public class MovementManager {
    
    private ArrayList<MovementEvent> m_events = new ArrayList();
    
    public void addEvent(MovementEvent event) {
        for(MovementEvent oevent : m_events) {
            if(oevent.getName().equals(event.getName())) return;
        }
        m_events.add(event);
    }
    
    public void removeEvent(String name) {
        for(MovementEvent event : m_events) {
            if(name.equals(event.getName())) {
                m_events.remove(event);
                break;
            }
        }
    }
}
