/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.player;

import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import de.agame.entitys.Entity;
import de.agame.entitys.EntitySpawnHelper;
import de.agame.entitys.EnviromentObservationSet;
import de.agame.entitys.SpatialControlSet;
import de.agame.entitys.UserInterfaceSet;

/**
 *
 * @author Fredie
 */
public class PlayerSpawnHelper implements EntitySpawnHelper{

    public Entity createFromScratch(AssetManager assets, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset) {
        Box box = new Box(new Vector3f(0.0f, 0.9f, 0.0f), 0.5f, 0.9f, 0.5f);
        Node model = (Node) assets.loadModel("Models/characters/Test Charakter/Test Charakter_ready.j3o");
        
        Node wrapper = new Node();
        wrapper.attachChild(model);
        
        BetterCharacterControl control = new BetterCharacterControl(0.5f, 1.8f, 1.0f);
        control.setJumpForce(new Vector3f(0.0f, 4.0f, 0.0f));
        control.setGravity(new Vector3f(0, 1.0f, 0));
        
        AnimControl animcontrol = model.getChild("Cube").getControl(AnimControl.class);
        
        wrapper.addControl(control);
        
        SpatialControlSet spatset = new SpatialControlSet();
        spatset.setMovementControll(control);
        spatset.setAnimationControl(animcontrol);
        
        EntityPlayer player = new EntityPlayer(wrapper, spatset, enviromentobservationset, userinterfaceset);
        wrapper.addControl(player);
        
        return player;
    }

    public void spawnEntityAt(Vector3f spawnpoint, Entity entity) {
        entity.teleportTo(spawnpoint.addLocal(0, 10, 0));
        entity.onAttach();
    }
    
}
