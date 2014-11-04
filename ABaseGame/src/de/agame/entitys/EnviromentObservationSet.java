/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.scene.Node;

/**
 *
 * @author Fredie
 */
public class EnviromentObservationSet {
    
    private EntityManager m_entitymanager;
    private PhysicsSpace m_physicsspace;
    private Node m_rootnode;
    
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
