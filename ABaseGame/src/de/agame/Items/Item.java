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
    
    private float m_mass = 1;
    
    private boolean m_isSharp = false;
    
    private boolean m_isTwoHanded;
    
    private Node m_itemmodel;

    private String m_attacktag;
    
    private String m_blocktag;
    
    private float m_range;
    
    public Item(Node itemmodel, int maxstacksize, float hitdamage, float range, float mass, boolean istwohanded, boolean sharp, String attacktag, String blocktag) {
        m_itemmodel = itemmodel;
        m_maxstacksize = maxstacksize;
        m_hitdamage = hitdamage;
        m_range = range;
        m_isTwoHanded = istwohanded;
        m_isSharp = sharp;
        m_mass = mass;
        
        m_attacktag = attacktag;
        m_blocktag = blocktag;
    }
    
    public float getRange() {
        return m_range;
    }
    
    public int getMaxStackSize() {
        return m_maxstacksize;
    }
    
    public float getHitDamage() {
        return m_hitdamage;
    }
    
    public float getMass() {
        return m_mass;
    }
    
    public String getAttackTag() {
        return m_attacktag;
    }
    
    public String getBlockTag() {
        return m_blocktag;
    }
    
    public boolean isTwoHanded() {
        return m_isTwoHanded;
    }
    
    public boolean isSharp() {
        return m_isSharp;
    }
    
    public Node getItemModel() {
        return m_itemmodel;
    }
}
