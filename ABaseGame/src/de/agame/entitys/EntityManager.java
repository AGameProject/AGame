/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import de.agame.entitys.spawnhelpers.EntitySpawnHelper;
import de.agame.entitys.sets.UserInterfaceSet;
import de.agame.entitys.sets.EnviromentObservationSet;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import de.agame.entitys.spawnhelpers.CompanionSpawnHelper;
import de.agame.entitys.spawnhelpers.PlayerSpawnHelper;
import de.agame.world.MeshProvider;
import de.agame.world.WorldManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Fredie
 */
public class EntityManager {
    private MeshProvider m_meshes;
    
    private ArrayList<Entity> m_entitys = new ArrayList<Entity>();
    private HashMap<String, EntitySpawnHelper> m_spawnhelpers = new HashMap<String, EntitySpawnHelper>();
    
    private EnviromentObservationSet m_enviromentobservationset;
    private UserInterfaceSet m_userinterfaceset;
    
    public void finishInit(Node root, NiftyJmeDisplay gui, Camera cam, ChaseCamera chasecam, InputManager inputmanager, MeshProvider meshes) {
        m_enviromentobservationset = new EnviromentObservationSet();
        m_enviromentobservationset.setEntityManager(this);
        m_enviromentobservationset.setRoot(root);
        
        m_userinterfaceset = new UserInterfaceSet();
        m_userinterfaceset.setCam(cam);
        m_userinterfaceset.setChaseCam(chasecam);
        m_userinterfaceset.setInputManager(inputmanager);
        m_userinterfaceset.setGuiNode(gui);
        
        m_meshes = meshes;
        
        //register SpawnHelpers
        m_spawnhelpers.put("Player", new PlayerSpawnHelper());
        m_spawnhelpers.put("Companion", new CompanionSpawnHelper());
    }
    
    public void setPhysicsSpace(PhysicsSpace space) {
        m_enviromentobservationset.setPhysicsSpace(space);
    }
    
    public void setWorldManager(WorldManager manager) {
        m_enviromentobservationset.setWorldManager(manager);
    }
    
    public Entity getEntity(int i) {
        return m_entitys.get(i);
    }
    
    public Iterator<Entity> getEntitys() {
        return m_entitys.iterator();
    }
    
    public Entity spawnEntityAt(String entity, Vector3f position) {
        EntitySpawnHelper helper = m_spawnhelpers.get(entity);
        Entity e = helper.createFromScratch(m_meshes, m_enviromentobservationset, m_userinterfaceset);
        
        helper.spawnEntityAt(position, e);
        m_entitys.add(e);
        
        return e;
    }
    
    public void despawnEntity(Entity entity) {
        entity.onDetach();
        m_entitys.remove(entity);
    }
    
    public void doPossibleDespawns() {
        for(Entity e : m_entitys) {
            if(e.mayDespawn()) despawnEntity(e);
        }
    }

    public void cleanup() {
        for(Entity e : m_entitys) {
            e.onDetach();
        }
    }
}
