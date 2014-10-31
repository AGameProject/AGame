/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Fredie
 */
public class EntityManager {
    private ArrayList<Entity> m_entitys = new ArrayList<Entity>();
    
    public void addEntity(Entity entity) {
        m_entitys.add(entity);
    }
    
    public Entity getEntity(int i) {
        return m_entitys.get(i);
    }
    
    public Iterator<Entity> getEntitys() {
        return m_entitys.iterator();
    }
    
    public void removeEntity(int i) {
        m_entitys.remove(i);
    }
    
    public void removeEntity(Entity entity) {
        m_entitys.remove(entity);
    }
    
    public void doPossibleDespawns() {
        for(Entity e : m_entitys) {
            
        }
    }
}
