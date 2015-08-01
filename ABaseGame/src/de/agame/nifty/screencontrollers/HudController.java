/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.nifty.screencontrollers;

import de.agame.nifty.inventory.Inventory;
import de.agame.nifty.inventory.InventoryFactory;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author Fredie
 */
public class HudController extends AbstractUIController{

    private Nifty m_nifty;
    private Screen m_screen;

    private boolean m_bound = false;
    
    private Element m_contentpanel;
    
    public void bind(Nifty nifty, Screen screen) {
        m_nifty = nifty;
        m_screen = screen;
        
        m_bound = true;
        
        m_contentpanel = m_screen.findElementByName("content");
    }
    
    /**
     * shows the given content. if content is null, it removes all the content currently shown
     * @param content 
     */
    public Element addCustomUIContent(ElementBuilder builder) {
        return builder.build(m_nifty, m_screen, m_contentpanel);
    }
    
    /**
     * removes all shown content
     */
    public void removeCustomUIContent(Element element) {
        element.markForRemoval();
    }
    
    public void onStartScreen() {
    }

    public void onEndScreen() {
    }
}
