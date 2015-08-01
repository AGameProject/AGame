/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.nifty.inventory;

import de.lessvoid.nifty.builder.ControlBuilder;

/**
 *
 * @author Fredie
 */
public class InventoryFactory {
    
    public static ControlBuilder createInventory(final String id) {
        ControlBuilder builder = new ControlBuilder(id, "inventory") {{
            height("100%");
            width("100%");
            parameter("font", "UserInterface/fonts/Content.fnt");
            parameter("title", id);
        }};
        
        return builder;
    }
}
