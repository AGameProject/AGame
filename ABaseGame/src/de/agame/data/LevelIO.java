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
    
    /**
     * loads the specified .j3o
     * @param world path to the file
     * @param assets the assetmanager to be used for loading
     * @return a spatial containing the .j3o's data
     */
    public Spatial loadStaticWorld(String world, AssetManager assets) {
        return assets.loadModel(world);
    }
}
