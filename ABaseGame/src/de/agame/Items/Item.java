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
    
    private Node m_itemmodel;
    
    public void setMaxStackSize(int mstacksize) {
        m_maxstacksize = mstacksize;
    }
    
    public int getMaxStackSize() {
        return m_maxstacksize;
    }
    
    public float getHitDamage(float strength) {
        return 0;
    }
    
    public boolean isTwoHanded() {
        return false;
    }
    
    public Node getItemModel() {
        return m_itemmodel;
    }
}
