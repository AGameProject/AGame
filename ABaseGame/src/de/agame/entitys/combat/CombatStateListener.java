/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.combat;

import de.agame.entitys.animation.AnimRequest;

/**
 *
 * @author Fredie
 */
public interface CombatStateListener {
    public void handleAnimRequest(AnimRequest request);
}
