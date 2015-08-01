/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.nifty.inventory;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.AbstractController;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;

/**
 *
 * @author Fredie
 */
public class Inventory extends AbstractController{

    private Element m_title;
    private Element m_contentspace;
    
    private boolean m_bound = false;
    
    private int m_slots = 12;
    private int m_xslots = 4;
    
    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes) {
        super.bind(element);
        
        m_title = element.findElementByName("#title");
        m_contentspace = element.findElementByName("#contentpanel");
        
        m_bound = true;
    }
    
    public void setSlotProps(int slots, int xslots) {
        m_slots = slots;
        m_xslots = slots;
    }

    public void onStartScreen() {
    }

    public boolean inputEvent(NiftyInputEvent inputEvent) {
        return false;
    }
    
}
