/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.sets;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.scene.Node;
import de.agame.entitys.EntityManager;
import de.agame.world.WorldManager;

/**
 *
 * @author Fredie
 */
public class EnviromentObservationSet {
    
    private EntityManager m_entitymanager;
    private WorldManager m_worldmanager;
    private PhysicsSpace m_physicsspace;
    private Node m_rootnode;
    
    public void setWorldManager(WorldManager manager) {
        m_worldmanager = manager;
    }
    
    public WorldManager getWorldManager() {
        return m_worldmanager;
    }
    
    public void setEntityManager(EntityManager entitymanager) {
        m_entitymanager = entitymanager;
    }
    
    public EntityManager getEntityManager() {
        return m_entitymanager;
    }
    
    public void setPhysicsSpace(PhysicsSpace physicsspace) {
        m_physicsspace = physicsspace;
    }
    
    public PhysicsSpace getPhysicsSpace() {
        return m_physicsspace;
    }
    
    public void setRoot(Node node) {
        m_rootnode = node;
    }
    
    public Node getRoot() {
        return m_rootnode;
    }
}
