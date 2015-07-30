/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.nifty.screencontrollers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author Fredie
 */
public class IngameMenuController extends AbstractUIController{

    public void bind(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }
    
    public void continuePlaying() {
        if(getListener() != null)
            getListener().onInteract("continueplaying");
    }
    
    public void settings() {
        if(getListener() != null)
            getListener().onInteract("settings");
    }
    
    public void title() {
        if(getListener() != null)
            getListener().onInteract("title");
    }
}
