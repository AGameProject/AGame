/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.Items;

/**
 *
 * @author Fredie
 */
public class ItemStack {
    
    private int m_itemid = -1;
    private int m_stacksize = 0;
    
    public ItemStack(int itemid) {
        m_itemid = itemid;
    }
    
    public void setSize(int size) {
        m_stacksize = size;
    }
    
    public int getSize() {
        return m_stacksize;
    }
    
    public int getItemID() {
        return m_itemid;
    }
}
