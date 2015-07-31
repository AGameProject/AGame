/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.item;

/**
 *
 * @author Fredie
 */
public class ItemStack {
    
    private Item m_item;
    private int m_stacksize = 0;
    
    public ItemStack(Item item) {
        m_item = item;
    }
    
    public void setSize(int size) {
        m_stacksize = size;
    }
    
    public int getSize() {
        return m_stacksize;
    }
    
    public Item getItem() {
        return m_item;
    }
}
