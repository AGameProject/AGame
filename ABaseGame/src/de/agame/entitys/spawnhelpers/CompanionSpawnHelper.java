/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.spawnhelpers;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import de.agame.entitys.animation.AnimLink;
import de.agame.entitys.Entity;
import de.agame.entitys.EntityCompanion;
import de.agame.entitys.animation.AnimationManager;
import de.agame.entitys.animation.HumanoidAnimationProvider;
import de.agame.entitys.combat.AttackPunch;
import de.agame.entitys.combat.BlockFist;
import de.agame.entitys.combat.CombatManager;
import de.agame.entitys.movement.MovementJump;
import de.agame.entitys.movement.MovementManager;
import de.agame.entitys.sets.EnviromentObservationSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.UserInterfaceSet;
import de.agame.world.MeshProvider;

/**
 *
 * @author Fredie
 */
public class CompanionSpawnHelper implements EntitySpawnHelper{

    public Entity createFromScratch(MeshProvider meshes, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset) {
        Node model = meshes.getMesh("Linggify").clone(false);
        
        Node rightitem = (Node) ((Node) ((Node) model.getChild("Armature")).getChild("righthand")).getChild("item");
        Node leftitem = (Node) ((Node) ((Node) model.getChild("Armature")).getChild("lefthand")).getChild("item");
        
        //init character control
        BetterCharacterControl control = new BetterCharacterControl(0.5f, 1.8f, 1);
        control.setGravity(new Vector3f(0f, -9.81f, 0f));
        control.setJumpForce(new Vector3f(0f, 6f, 0f));
        model.addControl(control);
        
        //init animcontrol
        AnimControl animcontrol = model.getChild("Character").getControl(AnimControl.class);
        
        //init spatial set
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
        AnimLink jump = new AnimLink("jump", false, 2.0f, 0.2f);
        
        AnimLink combatidle = new AnimLink("CombatIdle", true, 1.0f, 0.2f);
        AnimLink combatidle1 = new AnimLink("CombatIdle1", true, 1.0f, 0.2f);
        AnimLink combatidle2 = new AnimLink("CombatIdle2", true, 1.0f, 0.2f);
        AnimLink combatwalk = new AnimLink("CombatWalk", true, 2.0f, 0.2f);
        AnimLink combatwalk1 = new AnimLink("CombatWalk1", true, 2.0f, 0.2f);
        AnimLink combatwalk2 = new AnimLink("CombatWalk2", true, 2.0f, 0.2f);
        AnimLink combatsprint = new AnimLink("CombatSprint", true, 1.5f, 0.2f);
        AnimLink combatsprint1 = new AnimLink("CombatSprint1", true, 1.5f, 0.2f);
        AnimLink combatsprint2 = new AnimLink("CombatSprint2", true, 1.5f, 0.2f);
        AnimLink combatcrouch = new AnimLink("CombatCrouch", true, 1.0f, 0.2f);
        AnimLink combatcrouch1 = new AnimLink("CombatCrouch1", true, 1.0f, 0.2f);
        AnimLink combatcrouch2 = new AnimLink("CombatCrouch2", true, 1.0f, 0.2f);
        AnimLink combatcrouchwalk = new AnimLink("CombatCrouchWalk", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalk1 = new AnimLink("CombatCrouchWalk1", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalk2 = new AnimLink("CombatCrouchWalk2", true, 2.0f, 0.2f);
        AnimLink combatfall = new AnimLink("CombatFall", true, 1.0f, 0.2f);
        
        AnimLink stumbleback0 = new AnimLink("StumbleBack", true, 1.0f, 0.1f);
        AnimLink stumbleleft0 = new AnimLink("StumbleLeft", true, 1.0f, 0.1f);
        AnimLink stumbleright0 = new AnimLink("StumbleRight", true, 1.0f, 0.1f);
        AnimLink stumblefront0 = new AnimLink("StumbleFront", true, 1.0f, 0.1f);
        
        AnimLink punch0 = new AnimLink("AttackPunch0", false, 1.0f, 0.05f);
        AnimLink punch1 = new AnimLink("AttackPunch1", false, 1.0f, 0.05f);
        AnimLink punch2 = new AnimLink("AttackPunch2", false, 1.0f, 0.05f);
        
        AnimLink fistblock0 = new AnimLink("BlockFist0", false, 1.0f, 0.05f);
        
        //init animprovider
        HumanoidAnimationProvider animprovider = new HumanoidAnimationProvider();
        animprovider.setFallAnims(new AnimLink[]{fall, fall, fall});
        animprovider.setIdleAnims(new AnimLink[]{idle, idle1, idle2});
        animprovider.setWalkingAnims(new AnimLink[]{walk, walk1, walk2});
        animprovider.setSprintingAnims(new AnimLink[]{sprint, sprint1, sprint2});
        animprovider.setCrouchingAnims(new AnimLink[]{crouch, crouch1, crouch2});
        animprovider.setCrouchWalkingAnims(new AnimLink[]{crouchwalk, crouchwalk1, crouchwalk2});
        
        animprovider.setCombatFallAnims(new AnimLink[]{combatfall, combatfall, combatfall});
        animprovider.setCombatIdleAnims(new AnimLink[]{combatidle, combatidle1, combatidle2});
        animprovider.setCombatWalkingAnims(new AnimLink[]{combatwalk, combatwalk1, combatwalk2});
        animprovider.setCombatSprintingAnims(new AnimLink[]{combatsprint, combatsprint1, combatsprint2});
        animprovider.setCombatCrouchingAnims(new AnimLink[]{combatcrouch, combatcrouch1, combatcrouch2});
        animprovider.setCombatCrouchWalkingAnims(new AnimLink[]{combatcrouchwalk, combatcrouchwalk1, combatcrouchwalk2});
        
        animprovider.setStumbleBackAnims(new AnimLink[] {stumbleback0});
        animprovider.setStumbleFrontAnims(new AnimLink[] {stumblefront0});
        animprovider.setStumbleLeftAnims(new AnimLink[] {stumbleleft0});
        animprovider.setStumbleRightAnims(new AnimLink[] {stumbleright0});
        
        animprovider.addCombatCombo("ATTACK_PUNCH", new AnimLink[]{punch0, punch1, punch2});
        animprovider.addCombatBlock("BLOCK_FIST", fistblock0);
        
        //create player
        EntityCompanion companion = new EntityCompanion(animprovider, model, spatset, enviromentobservationset, userinterfaceset);
        
        //init animchannels
        AnimationManager animmanager = companion.getAnimationManager();

        AnimChannel head = animcontrol.createChannel();
        head.addBone("Neck");
        head.addBone("Head");
        animmanager.addChannel(head, "HEAD", true);
        
        AnimChannel arms = animcontrol.createChannel();
        arms.addFromRootBone("Shoulder.r");
        arms.addFromRootBone("Shoulder.l");
        animmanager.addChannel(arms, "ARMS", true);
        
        AnimChannel torso = animcontrol.createChannel();
        torso.addBone("LowerBack");
        torso.addBone("MiddleBack");
        torso.addBone("UpperBack");
        animmanager.addChannel(torso, "TORSO", true);
        
        AnimChannel legs = animcontrol.createChannel();
        legs.addFromRootBone("Hip.r");
        legs.addFromRootBone("Hip.l");
        animmanager.addChannel(legs, "LEGS", true);
        
        MovementJump movjump = new MovementJump(companion.getAnimationManager());
        movjump.setAnims(new AnimLink[] {jump});
        
        try {
            
            MovementManager playermovement = companion.getMovementManager();
            playermovement.unlockParams();
            
            playermovement.addEvent(movjump);
            
            playermovement.lockParams();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        CombatManager combat = companion.getCombatManager();
        combat.initChannels(animmanager);
        combat.addAttack("ATTACK_PUNCH", new AttackPunch(animprovider));
        combat.addBlock("BLOCK_FIST", new BlockFist(animprovider));
        
        model.addControl(companion);
        
        companion.setRightHand(rightitem);
        companion.setLeftHand(leftitem);
        
        return companion;
    }

    public void spawnEntityAt(Vector3f spawnpoint, Entity entity) {
        entity.teleportTo(spawnpoint.addLocal(0, 10, 0));
        entity.onAttach();
    }
    
}
