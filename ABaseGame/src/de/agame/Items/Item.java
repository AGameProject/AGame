/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.Items;

import com.jme3.scene.Node;

/**
 *
 * @author Fredie
 */
public class Item {
    
    private int m_maxstacksize = 1;

    private float m_hitdamage;
    
    private boolean m_canblock;
    
    private boolean m_isTwoHanded;
    
    private Node m_itemmodel;
    
    public Item(Node itemmodel, int maxstacksize, float hitdamage, boolean canblock, boolean istwohanded) {
        m_itemmodel = itemmodel;
        m_maxstacksize = maxstacksize;
        m_hitdamage = hitdamage;
        m_canblock = canblock;
        m_isTwoHanded = istwohanded;
    }
    
    public int getMaxStackSize() {
        return m_maxstacksize;
    }
    
    public float getHitDamage(float strength) {
        return m_hitdamage * strength;
    }
    
    public boolean canBlock() {
        return m_canblock;
    }
    
    public boolean isTwoHanded() {
        return m_isTwoHanded;
    }
    
    public Node getItemModel() {
        return m_itemmodel;
    }
}
