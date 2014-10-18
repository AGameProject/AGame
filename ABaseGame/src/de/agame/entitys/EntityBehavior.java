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

    public static final int COMPLEX_UPDATES_PER_SECOND = 10;
    
    private float m_complexElapse = 1.0f / (float) COMPLEX_UPDATES_PER_SECOND;
    private float m_accu = 0;
    
    @Override
    protected void controlUpdate(float tpf) {
        m_accu += tpf;
        
        if(m_accu >= m_complexElapse) {
            m_accu -= m_complexElapse;
            complexAIUpdate(m_complexElapse);
        }
        
        simpleUpdate(tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
    public abstract void complexAIUpdate(float elapse);
    
    public abstract void simpleUpdate(float tpf);
}
