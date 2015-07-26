/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.nifty.screencontrollers;

import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Fredie
 */
public abstract class AbstractUIController implements ScreenController {
    
    private UIListener m_listener;
    
    public void setListener(UIListener listener) {
        m_listener = listener;
    }
    
    public UIListener getListener() {
        return m_listener;
    }
}
