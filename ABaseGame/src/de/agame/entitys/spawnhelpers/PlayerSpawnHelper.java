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
import de.agame.entitys.animation.AnimLink;
import de.agame.entitys.Entity;
import de.agame.entitys.movement.MovementFlip;
import de.agame.entitys.movement.MovementJump;
import de.agame.entitys.movement.MovementManager;
import de.agame.entitys.sets.EnviromentObservationSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.UserInterfaceSet;

/**
 *
 * @author Fredie
 */
public class PlayerSpawnHelper implements EntitySpawnHelper{

    public Entity createFromScratch(AssetManager assets, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset) {
        Node model = (Node) assets.loadModel("Models/characters/Parkour Test Character/Character_ready.j3o");
        
//        Node rightitem = (Node) ((Node) ((Node) model.getChild("Armature")).getChild("righthand")).getChild("item");
//        Node leftitem = (Node) ((Node) ((Node) model.getChild("Armature")).getChild("lefthand")).getChild("item");
        
        BetterCharacterControl control = new BetterCharacterControl(0.5f, 1.8f, 1);
        control.setGravity(new Vector3f(0f, -9.81f, 0f));
        control.setJumpForce(new Vector3f(0f, 6f, 0f));
        model.addControl(control);
        
        AnimControl animcontrol = model.getChild("Character").getControl(AnimControl.class);
        
        
        SpatialControlSet spatset = new SpatialControlSet();
        spatset.setMovementControll(control);
        spatset.setAnimationControl(animcontrol);
        
        EntityPlayer player = new EntityPlayer(model, spatset, enviromentobservationset, userinterfaceset);
        player.setIdleAnim(new AnimLink("Stehen", true, 1.0f, 0.2f));
        player.setWalkAnim(new AnimLink("Laufen", true, 2.0f, 0.2f));
        player.setSprintAnim(new AnimLink("Sprinten", true, 1.5f, 0.2f));
        player.setFallAnim(new AnimLink("Fallen", true, 7.0f, 0.2f));
        
        MovementJump jump = new MovementJump();
        jump.setAnims(new AnimLink[] { new AnimLink("Springen", false, 2.0f, 0.2f) });
        
        MovementFlip flip = new MovementFlip();
        flip.setAnims(new AnimLink[] { new AnimLink("Roll", true, 1.0f, 0.2f) });
        
        try {
            
            MovementManager playermovement = player.getMovementManager();
            playermovement.unlockParams();
            
            playermovement.addEvent(jump);
            playermovement.addEvent(flip);
            
            playermovement.lockParams();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        model.addControl(player);
        
        player.setAnimChannel(animcontrol.createChannel(), 0);
//        player.setRightHand(rightitem);
//        player.setLeftHand(leftitem);
        
        return player;
    }

    public void spawnEntityAt(Vector3f spawnpoint, Entity entity) {
        entity.teleportTo(spawnpoint.addLocal(0, 1, 0));
        entity.onAttach();
    }
    
}
