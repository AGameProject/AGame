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
import de.agame.entitys.AnimLink;
import de.agame.entitys.Entity;
import de.agame.entitys.EntityCompanion;
import de.agame.entitys.sets.EnviromentObservationSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.UserInterfaceSet;

/**
 *
 * @author Fredie
 */
public class CompanionSpawnHelper implements EntitySpawnHelper{

    public Entity createFromScratch(AssetManager assets, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset) {
        Node model = (Node) assets.loadModel("Models/characters/Test Charakter/Test Charakter_ready.j3o");
        
        BetterCharacterControl control = new BetterCharacterControl(0.5f, 1.8f, 1.0f);
        control.setJumpForce(new Vector3f(0.0f, 4.0f, 0.0f));
        control.setGravity(new Vector3f(0, 1.0f, 0));
        
        AnimControl animcontrol = model.getChild("Cube").getControl(AnimControl.class);
        
        model.addControl(control);
        
        SpatialControlSet spatset = new SpatialControlSet();
        spatset.setMovementControll(control);
        spatset.setAnimationControl(animcontrol);
        
        EntityCompanion companion = new EntityCompanion(model, spatset, enviromentobservationset, userinterfaceset);
        companion.setIdleAnim(new AnimLink("Stehen", true, 1.0f, 0.2f));
        companion.setWalkAnim(new AnimLink("Laufen", true, 2.0f, 0.2f));
        companion.setSprintAnim(new AnimLink("Sprinten", true, 2.0f, 0.2f));
        companion.setJumpAnim(new AnimLink("Springen", false, 2.0f, 0.05f));
        model.addControl(companion);
        
        return companion;
    }

    public void spawnEntityAt(Vector3f spawnpoint, Entity entity) {
        entity.teleportTo(spawnpoint.addLocal(0, 10, 0));
        entity.onAttach();
    }
    
}
