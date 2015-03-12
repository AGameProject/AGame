/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.agame.Items.Item;
import de.agame.entitys.sets.EnviromentObservationSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.UserInterfaceSet;

/**
 *
 * @author Fredie
 */
public class EntityCharacter extends EntityCreature {
    
    private Node m_lefthand;
    private Node m_righthand;
    
    private Item m_weapon;
    
    public EntityCharacter(Spatial spatial, SpatialControlSet scset, EnviromentObservationSet esset, UserInterfaceSet uiset) {
        super(spatial, scset, esset, uiset);
    }
    
    public void setLeftHand(Node node) {
        m_lefthand = node;
    }
    
    public void setRightHand(Node node) {
        m_righthand = node;
    }
    
    public Node getLeftHand() {
        return m_lefthand;
    }
    
    public Node getRightHand() {
        return m_righthand;
    }
    
    public void setWeapon(Item item) {
        m_weapon = item;
    }
    
    public Item getWeapon() {
        return m_weapon;
    }
}
