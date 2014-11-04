/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.input.ChaseCamera;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

/**
 *
 * @author Fredie
 */
public class EnviromentObservationSet {
    
    private EntityManager m_entitymanager;
    private PhysicsSpace m_physicsspace;
    private Node m_rootnode;
    private ChaseCamera m_chasecam;
    private Camera m_cam;
    
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
    
    public void setChaseCam(ChaseCamera cam) {
        m_chasecam = cam;
    }
    
    public ChaseCamera getChaseCam() {
        return m_chasecam;
    }
    
    public void setCam(Camera cam) {
        m_cam = cam;
    }
    
    public Camera getCam() {
        return m_cam;
    }
}
