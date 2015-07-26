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
public class HomeScreenController extends AbstractUIController{

    public void bind(Nifty nifty, Screen screen) {
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }
    
    public void play() {
        if(getListener() != null)
            getListener().onInteract("play");
    }
    
    public void settings() {
        if(getListener() != null)
            getListener().onInteract("settings");
    }
    
    public void quit() {
        if(getListener() != null)
            getListener().onInteract("quit");
    }
}
