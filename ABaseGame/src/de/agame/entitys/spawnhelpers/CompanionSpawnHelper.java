/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.spawnhelpers;

import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import de.agame.entitys.animation.AnimLink;
import de.agame.entitys.Entity;
import de.agame.entitys.EntityCompanion;
import de.agame.entitys.animation.AnimationManager;
import de.agame.entitys.animation.HumanoidAnimationProvider;
import de.agame.entitys.sets.EnviromentObservationSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.UserInterfaceSet;

/**
 *
 * @author Fredie
 */
public class CompanionSpawnHelper implements EntitySpawnHelper{

    public Entity createFromScratch(AssetManager assets, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset) {
        Node model = (Node) assets.loadModel("Models/characters/Parkour Test Character/Character_ready.j3o");
        
        BetterCharacterControl control = new BetterCharacterControl(0.5f, 1.8f, 1);
        model.addControl(control);
        
        AnimControl animcontrol = model.getChild("Character").getControl(AnimControl.class);
        
        SpatialControlSet spatset = new SpatialControlSet();
        spatset.setMovementControll(control);
        spatset.setAnimationControl(animcontrol);
        
        //init animlinks
        AnimLink idle = new AnimLink("Idle", true, 1.0f, 0.2f);
        AnimLink idle1 = new AnimLink("Idle1", true, 1.0f, 0.2f);
        AnimLink idle2 = new AnimLink("Idle2", true, 1.0f, 0.2f);
        AnimLink walk = new AnimLink("Walk", true, 2.0f, 0.2f);
        AnimLink walk1 = new AnimLink("Walk1", true, 2.0f, 0.2f);
        AnimLink walk2 = new AnimLink("Walk2", true, 2.0f, 0.2f);
        AnimLink sprint = new AnimLink("Sprint", true, 1.5f, 0.2f);
        AnimLink sprint1 = new AnimLink("Sprint1", true, 1.5f, 0.2f);
        AnimLink sprint2 = new AnimLink("Sprint2", true, 1.5f, 0.2f);
        AnimLink crouch = new AnimLink("Crouch", true, 1.0f, 0.2f);
        AnimLink crouch1 = new AnimLink("Crouch1", true, 1.0f, 0.2f);
        AnimLink crouch2 = new AnimLink("Crouch2", true, 1.0f, 0.2f);
        AnimLink crouchwalk = new AnimLink("CrouchWalk", true, 2.0f, 0.2f);
        AnimLink crouchwalk1 = new AnimLink("CrouchWalk1", true, 2.0f, 0.2f);
        AnimLink crouchwalk2 = new AnimLink("CrouchWalk2", true, 2.0f, 0.2f);
        AnimLink fall = new AnimLink("Fall", true, 1.0f, 0.2f);
        
        //init animprovider
        HumanoidAnimationProvider animprovider = new HumanoidAnimationProvider();
        animprovider.setFallAnims(new AnimLink[]{fall, fall, fall});
        animprovider.setIdleAnims(new AnimLink[]{idle, idle1, idle2});
        animprovider.setWalkingAnims(new AnimLink[]{walk, walk1, walk2});
        animprovider.setSprintingAnims(new AnimLink[]{sprint, sprint1, sprint2});
        animprovider.setCrouchingAnims(new AnimLink[]{crouch, crouch1, crouch2});
        animprovider.setCrouchWalkingAnims(new AnimLink[]{crouchwalk, crouchwalk1, crouchwalk2});
        
        EntityCompanion companion = new EntityCompanion(animprovider, model, spatset, enviromentobservationset, userinterfaceset);

        //init animchannels
        AnimationManager animmanager = companion.getAnimationManager();
        animmanager.addChannel(animcontrol.createChannel(), "LEGS", true);
        
        model.addControl(companion);
        
        return companion;
    }

    public void spawnEntityAt(Vector3f spawnpoint, Entity entity) {
        entity.teleportTo(spawnpoint.addLocal(0, 1, 0));
        entity.onAttach();
    }
    
}
