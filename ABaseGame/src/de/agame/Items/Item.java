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
    
    private boolean m_isTwoHanded;
    
    private Node m_itemmodel;

    private String m_attacktag;
    
    private String m_blocktag;
    
    public Item(Node itemmodel, int maxstacksize, float hitdamage, boolean istwohanded, String attacktag, String blocktag) {
        m_itemmodel = itemmodel;
        m_maxstacksize = maxstacksize;
        m_hitdamage = hitdamage;
        m_isTwoHanded = istwohanded;
        
        m_attacktag = attacktag;
        m_blocktag = blocktag;
    }
    
    public int getMaxStackSize() {
        return m_maxstacksize;
    }
    
    public float getHitDamage(float strength) {
        return m_hitdamage * strength;
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
    
    public Node getItemModel() {
        return m_itemmodel;
    }
}
