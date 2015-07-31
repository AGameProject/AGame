/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.spawnhelpers;

import com.jme3.animation.AnimChannel;
import de.agame.entitys.EntityPlayer;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import de.agame.item.Item;
import de.agame.entitys.animation.AnimLink;
import de.agame.entitys.Entity;
import de.agame.entitys.animation.AnimationManager;
import de.agame.entitys.animation.HumanoidAnimationProvider;
import de.agame.entitys.combat.AttackPunch;
import de.agame.entitys.combat.AttackSwing;
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
public class PlayerSpawnHelper implements EntitySpawnHelper{

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
        AnimLink walkfrontleft = new AnimLink("WalkFrontLeft", true, 2.0f, 0.2f);
        AnimLink walkfrontleft1 = new AnimLink("WalkFrontLeft1", true, 2.0f, 0.2f);
        AnimLink walkfrontleft2 = new AnimLink("WalkFrontLeft2", true, 2.0f, 0.2f);
        AnimLink walkfrontright = new AnimLink("WalkFrontRight", true, 2.0f, 0.2f);
        AnimLink walkfrontright1 = new AnimLink("WalkFrontRight1", true, 2.0f, 0.2f);
        AnimLink walkfrontright2 = new AnimLink("WalkFrontRight2", true, 2.0f, 0.2f);
        AnimLink walkleft = new AnimLink("WalkLeft", true, 2.0f, 0.2f);
        AnimLink walkleft1 = new AnimLink("WalkLeft1", true, 2.0f, 0.2f);
        AnimLink walkleft2 = new AnimLink("WalkLeft2", true, 2.0f, 0.2f);
        AnimLink walkright = new AnimLink("WalkRight", true, 2.0f, 0.2f);
        AnimLink walkright1 = new AnimLink("WalkRight1", true, 2.0f, 0.2f);
        AnimLink walkright2 = new AnimLink("WalkRight2", true, 2.0f, 0.2f);
        AnimLink walkback = new AnimLink("WalkBack", true, 2.0f, 0.2f);
        AnimLink walkback1 = new AnimLink("WalkBack1", true, 2.0f, 0.2f);
        AnimLink walkback2 = new AnimLink("WalkBack2", true, 2.0f, 0.2f);
        AnimLink walkbackleft = new AnimLink("WalkBackLeft", true, 2.0f, 0.2f);
        AnimLink walkbackleft1 = new AnimLink("WalkBackLeft1", true, 2.0f, 0.2f);
        AnimLink walkbackleft2 = new AnimLink("WalkBackLeft2", true, 2.0f, 0.2f);
        AnimLink walkbackright = new AnimLink("WalkBackRight", true, 2.0f, 0.2f);
        AnimLink walkbackright1 = new AnimLink("WalkBackRight1", true, 2.0f, 0.2f);
        AnimLink walkbackright2 = new AnimLink("WalkBackRight2", true, 2.0f, 0.2f);
        AnimLink sprint = new AnimLink("Sprint", true, 1.5f, 0.2f);
        AnimLink sprint1 = new AnimLink("Sprint1", true, 1.5f, 0.2f);
        AnimLink sprint2 = new AnimLink("Sprint2", true, 1.5f, 0.2f);
        AnimLink crouch = new AnimLink("Crouch", true, 1.0f, 0.2f);
        AnimLink crouch1 = new AnimLink("Crouch1", true, 1.0f, 0.2f);
        AnimLink crouch2 = new AnimLink("Crouch2", true, 1.0f, 0.2f);
        AnimLink crouchwalk = new AnimLink("CrouchWalk", true, 2.0f, 0.2f);
        AnimLink crouchwalk1 = new AnimLink("CrouchWalk1", true, 2.0f, 0.2f);
        AnimLink crouchwalk2 = new AnimLink("CrouchWalk2", true, 2.0f, 0.2f);
        AnimLink crouchwalkfrontleft = new AnimLink("CrouchWalkFrontLeft", true, 2.0f, 0.2f);
        AnimLink crouchwalkfrontleft1 = new AnimLink("CrouchWalkFrontLeft1", true, 2.0f, 0.2f);
        AnimLink crouchwalkfrontleft2 = new AnimLink("CrouchWalkFrontLeft2", true, 2.0f, 0.2f);
        AnimLink crouchwalkfrontright = new AnimLink("CrouchWalkFrontRight", true, 2.0f, 0.2f);
        AnimLink crouchwalkfrontright1 = new AnimLink("CrouchWalkFrontRight1", true, 2.0f, 0.2f);
        AnimLink crouchwalkfrontright2 = new AnimLink("CrouchWalkFrontRight2", true, 2.0f, 0.2f);
        AnimLink crouchwalkleft = new AnimLink("CrouchWalkLeft", true, 2.0f, 0.2f);
        AnimLink crouchwalkleft1 = new AnimLink("CrouchWalkLeft1", true, 2.0f, 0.2f);
        AnimLink crouchwalkleft2 = new AnimLink("CrouchWalkLeft2", true, 2.0f, 0.2f);
        AnimLink crouchwalkright = new AnimLink("CrouchWalkRight", true, 2.0f, 0.2f);
        AnimLink crouchwalkright1 = new AnimLink("CrouchWalkRight1", true, 2.0f, 0.2f);
        AnimLink crouchwalkright2 = new AnimLink("CrouchWalkRight2", true, 2.0f, 0.2f);
        AnimLink crouchwalkback = new AnimLink("CrouchWalkBack", true, 2.0f, 0.2f);
        AnimLink crouchwalkback1 = new AnimLink("CrouchWalkBack1", true, 2.0f, 0.2f);
        AnimLink crouchwalkback2 = new AnimLink("CrouchWalkBack2", true, 2.0f, 0.2f);
        AnimLink crouchwalkbackleft = new AnimLink("CrouchWalkBackLeft", true, 2.0f, 0.2f);
        AnimLink crouchwalkbackleft1 = new AnimLink("CrouchWalkBackLeft1", true, 2.0f, 0.2f);
        AnimLink crouchwalkbackleft2 = new AnimLink("CrouchWalkBackLeft2", true, 2.0f, 0.2f);
        AnimLink crouchwalkbackright = new AnimLink("CrouchWalkBackRight", true, 2.0f, 0.2f);
        AnimLink crouchwalkbackright1 = new AnimLink("CrouchWalkBackRight1", true, 2.0f, 0.2f);
        AnimLink crouchwalkbackright2 = new AnimLink("CrouchWalkBackRight2", true, 2.0f, 0.2f);
        AnimLink fall = new AnimLink("Fall", true, 1.0f, 0.2f);
        AnimLink jump = new AnimLink("jump", false, 2.0f, 0.2f);
        AnimLink combatidle = new AnimLink("CombatIdle", true, 1.0f, 0.2f);
        AnimLink combatidle1 = new AnimLink("CombatIdle1", true, 1.0f, 0.2f);
        AnimLink combatidle2 = new AnimLink("CombatIdle2", true, 1.0f, 0.2f);
        AnimLink combatwalk = new AnimLink("CombatWalk", true, 2.0f, 0.2f);
        AnimLink combatwalk1 = new AnimLink("CombatWalk1", true, 2.0f, 0.2f);
        AnimLink combatwalk2 = new AnimLink("CombatWalk2", true, 2.0f, 0.2f);
        AnimLink combatwalkfrontleft = new AnimLink("CombatWalkFrontLeft", true, 2.0f, 0.2f);
        AnimLink combatwalkfrontleft1 = new AnimLink("CombatWalkFrontLeft1", true, 2.0f, 0.2f);
        AnimLink combatwalkfrontleft2 = new AnimLink("CombatWalkFrontLeft2", true, 2.0f, 0.2f);
        AnimLink combatwalkfrontright = new AnimLink("CombatWalkFrontRight", true, 2.0f, 0.2f);
        AnimLink combatwalkfrontright1 = new AnimLink("CombatWalkFrontRight1", true, 2.0f, 0.2f);
        AnimLink combatwalkfrontright2 = new AnimLink("CombatWalkFrontRight2", true, 2.0f, 0.2f);
        AnimLink combatwalkleft = new AnimLink("CombatWalkLeft", true, 2.0f, 0.2f);
        AnimLink combatwalkleft1 = new AnimLink("CombatWalkLeft1", true, 2.0f, 0.2f);
        AnimLink combatwalkleft2 = new AnimLink("CombatWalkLeft2", true, 2.0f, 0.2f);
        AnimLink combatwalkright = new AnimLink("CombatWalkRight", true, 2.0f, 0.2f);
        AnimLink combatwalkright1 = new AnimLink("CombatWalkRight1", true, 2.0f, 0.2f);
        AnimLink combatwalkright2 = new AnimLink("CombatWalkRight2", true, 2.0f, 0.2f);
        AnimLink combatwalkback = new AnimLink("CombatWalkBack", true, 2.0f, 0.2f);
        AnimLink combatwalkback1 = new AnimLink("CombatWalkBack1", true, 2.0f, 0.2f);
        AnimLink combatwalkback2 = new AnimLink("CombatWalkBack2", true, 2.0f, 0.2f);
        AnimLink combatwalkbackleft = new AnimLink("CombatWalkBackLeft", true, 2.0f, 0.2f);
        AnimLink combatwalkbackleft1 = new AnimLink("CombatWalkBackLeft1", true, 2.0f, 0.2f);
        AnimLink combatwalkbackleft2 = new AnimLink("CombatWalkBackLeft2", true, 2.0f, 0.2f);
        AnimLink combatwalkbackright = new AnimLink("CombatWalkBackRight", true, 2.0f, 0.2f);
        AnimLink combatwalkbackright1 = new AnimLink("CombatWalkBackRight1", true, 2.0f, 0.2f);
        AnimLink combatwalkbackright2 = new AnimLink("CombatWalkBackRight2", true, 2.0f, 0.2f);
        AnimLink combatsprint = new AnimLink("CombatSprint", true, 1.5f, 0.2f);
        AnimLink combatsprint1 = new AnimLink("CombatSprint1", true, 1.5f, 0.2f);
        AnimLink combatsprint2 = new AnimLink("CombatSprint2", true, 1.5f, 0.2f);
        AnimLink combatcrouch = new AnimLink("CombatCrouch", true, 1.0f, 0.2f);
        AnimLink combatcrouch1 = new AnimLink("CombatCrouch1", true, 1.0f, 0.2f);
        AnimLink combatcrouch2 = new AnimLink("CombatCrouch2", true, 1.0f, 0.2f);
        AnimLink combatcrouchwalk = new AnimLink("CombatCrouchWalk", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalk1 = new AnimLink("CombatCrouchWalk1", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalk2 = new AnimLink("CombatCrouchWalk2", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkfrontleft = new AnimLink("CombatCrouchWalkFrontLeft", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkfrontleft1 = new AnimLink("CombatCrouchWalkFrontLeft1", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkfrontleft2 = new AnimLink("CombatCrouchWalkFrontLeft2", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkfrontright = new AnimLink("CombatCrouchWalkFrontRight", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkfrontright1 = new AnimLink("CombatCrouchWalkFrontRight1", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkfrontright2 = new AnimLink("CombatCrouchWalkFrontRight2", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkleft = new AnimLink("CombatCrouchWalkLeft", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkleft1 = new AnimLink("CombatCrouchWalkLeft1", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkleft2 = new AnimLink("CombatCrouchWalkLeft2", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkright = new AnimLink("CombatCrouchWalkRight", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkright1 = new AnimLink("CombatCrouchWalkRight1", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkright2 = new AnimLink("CombatCrouchWalkRight2", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkback = new AnimLink("CombatCrouchWalkBack", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkback1 = new AnimLink("CombatCrouchWalkBack1", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkback2 = new AnimLink("CombatCrouchWalkBack2", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkbackleft = new AnimLink("CombatCrouchWalkBackLeft", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkbackleft1 = new AnimLink("CombatCrouchWalkBackLeft1", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkbackleft2 = new AnimLink("CombatCrouchWalkBackLeft2", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkbackright = new AnimLink("CombatCrouchWalkBackRight", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkbackright1 = new AnimLink("CombatCrouchWalkBackRight1", true, 2.0f, 0.2f);
        AnimLink combatcrouchwalkbackright2 = new AnimLink("CombatCrouchWalkBackRight2", true, 2.0f, 0.2f);
        AnimLink combatfall = new AnimLink("CombatFall", true, 1.0f, 0.2f);
        AnimLink stumbleback0 = new AnimLink("StumbleBack", true, 1.0f, 0.1f);
        AnimLink stumbleleft0 = new AnimLink("StumbleLeft", true, 1.0f, 0.1f);
        AnimLink stumbleright0 = new AnimLink("StumbleRight", true, 1.0f, 0.1f);
        AnimLink stumblefront0 = new AnimLink("StumbleFront", true, 1.0f, 0.1f);
        AnimLink punch0 = new AnimLink("AttackPunch0", false, 1.0f, 0.05f);
        AnimLink punch1 = new AnimLink("AttackPunch1", false, 1.0f, 0.05f);
        AnimLink punch2 = new AnimLink("AttackPunch2", false, 1.0f, 0.05f);
        AnimLink swing0 = new AnimLink("AttackSwing0", false, 1.0f, 0.05f);
        AnimLink swing1 = new AnimLink("AttackSwing1", false, 1.0f, 0.05f);
        AnimLink swing2 = new AnimLink("AttackSwing2", false, 1.0f, 0.05f);
        AnimLink fistblock0 = new AnimLink("BlockFist0", false, 1.0f, 0.05f);
        
        //init animprovider
        HumanoidAnimationProvider animprovider = new HumanoidAnimationProvider();
        animprovider.setFallAnims(fall, fall, fall);
        animprovider.setIdleAnims(idle, idle1, idle2);
        animprovider.setWalkingAnims(walk, walk1, walk2);
        animprovider.setWalkingFrontLeftAnims(walkfrontleft, walkfrontleft1, walkfrontleft2);
        animprovider.setWalkingFrontRightAnims(walkfrontright, walkfrontright1, walkfrontright2);
        animprovider.setWalkingLeftAnims(walkleft, walkleft1, walkleft2);
        animprovider.setWalkingRightAnims(walkright, walkright1, walkright2);
        animprovider.setWalkingBackLeftAnims(walkbackleft, walkbackleft1, walkbackleft2);
        animprovider.setWalkingBackRightAnims(walkbackright, walkbackright1, walkbackright2);
        animprovider.setWalkingBackAnims(walkback, walkback1, walkback2);
        animprovider.setSprintingAnims(sprint, sprint1, sprint2);
        animprovider.setCrouchingAnims(crouch, crouch1, crouch2);
        animprovider.setCrouchWalkingAnims(crouchwalk, crouchwalk1, crouchwalk2);
        animprovider.setCrouchWalkingFrontLeftAnims(crouchwalkfrontleft, crouchwalkfrontleft1, crouchwalkfrontleft2);
        animprovider.setCrouchWalkingFrontRightAnims(crouchwalkfrontright, crouchwalkfrontright1, crouchwalkfrontright2);
        animprovider.setCrouchWalkingLeftAnims(crouchwalkleft, crouchwalkleft1, crouchwalkleft2);
        animprovider.setCrouchWalkingRightAnims(crouchwalkright, crouchwalkright1, crouchwalkright2);
        animprovider.setCrouchWalkingBackLeftAnims(crouchwalkbackleft, crouchwalkbackleft1, crouchwalkbackleft2);
        animprovider.setCrouchWalkingBackRightAnims(crouchwalkbackright, crouchwalkbackright1, crouchwalkbackright2);
        animprovider.setCrouchWalkingBackAnims(crouchwalkback, crouchwalkback1, crouchwalkback2);
        animprovider.setCombatFallAnims(combatfall, combatfall, combatfall);
        animprovider.setCombatIdleAnims(combatidle, combatidle1, combatidle2);
        animprovider.setCombatWalkingAnims(combatwalk, combatwalk1, combatwalk2);
        animprovider.setCombatWalkingFrontLeftAnims(combatwalkfrontleft, combatwalkfrontleft1, combatwalkfrontleft2);
        animprovider.setCombatWalkingFrontRightAnims(combatwalkfrontright, combatwalkfrontright1, combatwalkfrontright2);
        animprovider.setCombatWalkingLeftAnims(combatwalkleft, combatwalkleft1, combatwalkleft2);
        animprovider.setCombatWalkingRightAnims(combatwalkright, combatwalkright1, combatwalkright2);
        animprovider.setCombatWalkingBackLeftAnims(combatwalkbackleft, combatwalkbackleft1, combatwalkbackleft2);
        animprovider.setCombatWalkingBackRightAnims(combatwalkbackright, combatwalkbackright1, combatwalkbackright2);
        animprovider.setCombatWalkingBackAnims(combatwalkback, combatwalkback1, combatwalkback2);
        animprovider.setCombatSprintingAnims(combatsprint, combatsprint1, combatsprint2);
        animprovider.setCombatCrouchingAnims(combatcrouch, combatcrouch1, combatcrouch2);
        animprovider.setCombatCrouchWalkingAnims(combatcrouchwalk, combatcrouchwalk1, combatcrouchwalk2);
        animprovider.setCombatCrouchWalkingFrontLeftAnims(combatcrouchwalkfrontleft, combatcrouchwalkfrontleft1, combatcrouchwalkfrontleft2);
        animprovider.setCombatCrouchWalkingFrontRightAnims(combatcrouchwalkfrontright, combatcrouchwalkfrontright1, combatcrouchwalkfrontright2);
        animprovider.setCombatCrouchWalkingLeftAnims(combatcrouchwalkleft, combatcrouchwalkleft1, combatcrouchwalkleft2);
        animprovider.setCombatCrouchWalkingRightAnims(combatcrouchwalkright, combatcrouchwalkright1, combatcrouchwalkright2);
        animprovider.setCombatCrouchWalkingBackLeftAnims(combatcrouchwalkbackleft, combatcrouchwalkbackleft1, combatcrouchwalkbackleft2);
        animprovider.setCombatCrouchWalkingBackRightAnims(combatcrouchwalkbackright, combatcrouchwalkbackright1, combatcrouchwalkbackright2);
        animprovider.setCombatCrouchWalkingBackAnims(combatcrouchwalkback, combatcrouchwalkback1, combatcrouchwalkback2);
        animprovider.setStumbleBackAnims(stumbleback0);
        animprovider.setStumbleFrontAnims(stumblefront0);
        animprovider.setStumbleLeftAnims(stumbleleft0);
        animprovider.setStumbleRightAnims(stumbleright0);
        animprovider.addCombatCombo("ATTACK_PUNCH", punch0, punch1, punch2);
        animprovider.addCombatCombo("ATTACK_SWING_ONE", swing0, swing1, swing2);
        animprovider.addCombatBlock("BLOCK_FIST", fistblock0);
        
        //create player
        EntityPlayer player = new EntityPlayer(animprovider, model, spatset, enviromentobservationset, userinterfaceset);
        
        //init animchannels
        AnimationManager animmanager = player.getAnimationManager();

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
        
        MovementJump movjump = new MovementJump(player.getAnimationManager());
        movjump.setAnims(new AnimLink[] {jump});
        
        try {
            
            MovementManager playermovement = player.getMovementManager();
            playermovement.unlockParams();
            
            playermovement.addEvent(movjump);
            
            playermovement.lockParams();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        CombatManager combat = player.getCombatManager();
        combat.initChannels(animmanager);
        combat.addAttack("ATTACK_PUNCH", new AttackPunch(animprovider));
        combat.addAttack("ATTACK_SWING", new AttackSwing(animprovider));
        combat.addBlock("BLOCK_FIST", new BlockFist(animprovider));
        
        model.addControl(player);
        
        player.setRightHand(rightitem);
        player.setLeftHand(leftitem);
        
        Node sword = meshes.getMesh("theSlasher");
        Item item = new Item(null, sword, 1, 30.0f, 1.5f, 2.5f, false, true, "ATTACK_SWING", null);
        
        player.setHeldItem(item);
        
        return player;
    }

    public void spawnEntityAt(Vector3f spawnpoint, Entity entity) {
        entity.teleportTo(spawnpoint.addLocal(0, 10, 0));
        entity.onAttach();
    }
}
