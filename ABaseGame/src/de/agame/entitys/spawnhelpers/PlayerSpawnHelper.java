/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.spawnhelpers;

import de.agame.entitys.EntityPlayer;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import de.agame.entitys.AnimLink;
import de.agame.entitys.Entity;
import de.agame.entitys.sets.EnviromentObservationSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.UserInterfaceSet;

/**
 *
 * @author Fredie
 */
public class PlayerSpawnHelper implements EntitySpawnHelper{

    public Entity createFromScratch(AssetManager assets, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset) {
        Node model = (Node) assets.loadModel("Models/characters/Test Charakter/Test Charakter_ready.j3o");
        
        Node rightitem = (Node) ((Node) ((Node) model.getChild("Armature")).getChild("righthand")).getChild("item");
        Node leftitem = (Node) ((Node) ((Node) model.getChild("Armature")).getChild("lefthand")).getChild("item");
        
        CapsuleCollisionShape shape = new CapsuleCollisionShape(0.3f, 1.8f);
        CharacterControl control = new CharacterControl(shape, 0.2f);
        control.setJumpSpeed(15);
        model.addControl(control);
        
        AnimControl animcontrol = model.getChild("Cube").getControl(AnimControl.class);
        
        
        SpatialControlSet spatset = new SpatialControlSet();
        spatset.setMovementControll(control);
        spatset.setAnimationControl(animcontrol);
        
        EntityPlayer player = new EntityPlayer(model, spatset, enviromentobservationset, userinterfaceset);
        player.setIdleAnim(new AnimLink("Stehen", true, 1.0f, 0.2f));
        player.setWalkAnim(new AnimLink("Laufen", true, 2.0f, 0.2f));
        player.setSprintAnim(new AnimLink("Sprinten", true, 2.0f, 0.2f));
        player.setJumpAnim(new AnimLink("Springen", false, 2.0f, 0.05f));
        player.setFallAnim(new AnimLink("Fallen", true, 7.0f, 0.2f));
        model.addControl(player);
        
        player.setAnimChannel(animcontrol.createChannel(), 0);
        player.setRightHand(rightitem);
        player.setLeftHand(leftitem);
        
        return player;
    }

    public void spawnEntityAt(Vector3f spawnpoint, Entity entity) {
        entity.teleportTo(spawnpoint.addLocal(0, 20, 0));
        entity.onAttach();
    }
    
}
