/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.spawnhelpers;

import de.agame.entitys.EntityPlayer;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import de.agame.entitys.AnimLink;
import de.agame.entitys.Entity;
import de.agame.entitys.EntitySpawnHelper;
import de.agame.entitys.sets.EnviromentObservationSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.UserInterfaceSet;

/**
 *
 * @author Fredie
 */
public class PlayerSpawnHelper implements EntitySpawnHelper{

    public Entity createFromScratch(AssetManager assets, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset) {
        Box box = new Box(new Vector3f(0.0f, 0.9f, 0.0f), 0.5f, 0.9f, 0.5f);
        Node model = (Node) assets.loadModel("Models/characters/Test Charakter/Test Charakter_ready.j3o");
        
        BetterCharacterControl control = new BetterCharacterControl(0.5f, 1.8f, 1.0f);
        control.setJumpForce(new Vector3f(0.0f, 4.0f, 0.0f));
        control.setGravity(new Vector3f(0, 1.0f, 0));
        
        AnimControl animcontrol = model.getChild("Cube").getControl(AnimControl.class);
        
        model.addControl(control);
        
        SpatialControlSet spatset = new SpatialControlSet();
        spatset.setMovementControll(control);
        spatset.setAnimationControl(animcontrol);
        
        EntityPlayer player = new EntityPlayer(model, spatset, enviromentobservationset, userinterfaceset);
        player.setIdleAnim(new AnimLink("Stehen", true, 1.0f, 0.2f));
        player.setWalkAnim(new AnimLink("Laufen", true, 2.0f, 0.2f));
        player.setSprintAnim(new AnimLink("Sprinten", true, 2.0f, 0.2f));
        player.setJumpAnim(new AnimLink("Springen", false, 2.0f, 0.05f));
        model.addControl(player);
        
        return player;
    }

    public void spawnEntityAt(Vector3f spawnpoint, Entity entity) {
        entity.teleportTo(spawnpoint.addLocal(0, 10, 0));
        entity.onAttach();
    }
    
}
