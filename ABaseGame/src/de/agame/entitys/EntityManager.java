/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import de.agame.player.PlayerSpawnHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Fredie
 */
public class EntityManager {
    private InputManager m_inputmanager;
    private AssetManager m_assets;
    
    private ArrayList<Entity> m_entitys = new ArrayList<Entity>();
    private HashMap<String, EntitySpawnHelper> m_spawnhelpers = new HashMap<String, EntitySpawnHelper>();
    private EnviromentObservationSet m_enviromentobservationset;
    
    public void finishInit(PhysicsSpace physicsspace, Node root, Camera cam, ChaseCamera chasecam, InputManager inputmanager, AssetManager assets) {
        m_enviromentobservationset = new EnviromentObservationSet();
        m_enviromentobservationset.setEntityManager(this);
        m_enviromentobservationset.setPhysicsSpace(physicsspace);
        m_enviromentobservationset.setRoot(root);
        m_enviromentobservationset.setChaseCam(chasecam);
        m_enviromentobservationset.setCam(cam);
        
        m_inputmanager = inputmanager;
        m_assets = assets;
        
        //register SpawnHelpers
        m_spawnhelpers.put("Player", new PlayerSpawnHelper());
    }
    
    public Entity getEntity(int i) {
        return m_entitys.get(i);
    }
    
    public Iterator<Entity> getEntitys() {
        return m_entitys.iterator();
    }
    
    public Entity spawnEntityAt(String entity, Vector3f position) {
        EntitySpawnHelper helper = m_spawnhelpers.get(entity);
        Entity e = helper.createFromScratch(m_assets, m_enviromentobservationset);
        
        helper.spawnEntityAt(position, e, m_inputmanager);
        m_entitys.add(e);
        
        return e;
    }
    
    public void despawnEntity(Entity entity) {
        entity.onDetach(m_inputmanager);
        m_entitys.remove(entity);
    }
    
    public void doPossibleDespawns() {
        for(Entity e : m_entitys) {
            if(e.mayDespawn()) despawnEntity(e);
        }
    }
}
