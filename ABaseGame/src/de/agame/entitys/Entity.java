/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Fredie
 */
public class Entity extends AbstractControl{

    protected Spatial m_spatial;
    protected SpatialControlSet m_spatialcontrolset;
    protected EnviromentObservationSet m_enviromentobservationset;
    
    
    public Entity(Spatial spatial, SpatialControlSet spatialcontrolset, EnviromentObservationSet enviromentobservationset) {
        m_spatial = spatial;
        m_spatialcontrolset = spatialcontrolset;
        m_enviromentobservationset = enviromentobservationset;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //not used yet
    }
}
