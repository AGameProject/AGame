/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.data;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

/**
 *
 * @author Fredie
 */
public class LevelIO {
    
    public Spatial loadStaticWorld(String world, AssetManager assets) {
        return assets.loadModel(world);
    }
}
