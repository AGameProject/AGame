/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.nifty.inventory;

import de.agame.item.ItemStack;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.AbstractController;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;

/**
 *
 * @author Fredie
 */
public class InventorySlot extends AbstractController{

    private ItemStack m_itemStack;
    
    private Element m_backdrop;
    private Element m_item;
    private Element m_desc;
    
    private Nifty m_nifty;
    private Screen m_screen;
    
    private boolean m_bound;
    
    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes) {
        super.bind(element);
        
        m_backdrop = element.findElementByName("#backdrop");
        m_item = element.findElementByName("#item");
        m_desc = element.findElementByName("#text");

        m_nifty = nifty;
        m_screen = screen;
        
        m_bound = true;
        
        updateSlotUI();
    }
    
    public void setItemStack(ItemStack itemstack) {
        m_itemStack = itemstack;
        
        if(m_bound)
            updateSlotUI();
    }
    
    private void updateSlotUI() {
        if(m_itemStack != null) {
            m_item.setVisible(true);
            m_desc.setVisible(true);
            
            String description = m_itemStack.getSize() + "";
            String thumbdir = m_itemStack.getItem().getThumbdir();

            //update text
            m_desc.getRenderer(TextRenderer.class).setText(description);

            //update thumb
            ImageRenderer imagerenderer = m_item.getRenderer(ImageRenderer.class);
            imagerenderer.setImage(m_nifty.getRenderEngine().createImage(m_screen, thumbdir, false));
        } else {
            m_item.setVisible(false);
            m_desc.setVisible(false);
        }
    }

    public void onStartScreen() {
    }

    public boolean inputEvent(NiftyInputEvent inputEvent) {
        return false;
    }
    
}
