/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Fredie
 */
public abstract class EntityBehavior extends AbstractControl{

    /**
     * how many complex updates per second are executed
     **/ 
    public static final int COMPLEX_UPDATES_PER_SECOND = 10;
    
    //calculate time between complex updates
    private float m_complexElapse = 1.0f / (float) COMPLEX_UPDATES_PER_SECOND;
    private float m_accu = 0;
    
    @Override
    protected void controlUpdate(float tpf) {
        //accumulate time passed since the last complex update sceduled
        m_accu += tpf;
        
        //do complex update only if necessary
        if(m_accu >= m_complexElapse) {
            m_accu -= m_complexElapse;
            complexAIUpdate(m_complexElapse);
        }
        
        //always do simple update
        simpleUpdate(tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
    /**
     * updates behavior of the entity
     * @param elapse the time since last comlpex update
     */
    public abstract void complexAIUpdate(float elapse);
    
    /**
     * updates stuff like animations health etc
     * @param tpf time since last update
     */
    public abstract void simpleUpdate(float tpf);
}
