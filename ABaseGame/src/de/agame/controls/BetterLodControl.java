/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.controls;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.scene.control.LodControl;
import java.io.IOException;

/**
 *
 * @author Fredie
 */
public class BetterLodControl extends LodControl {
    //Any local variables should be encapsulated by getters/setters so they
    //appear in the SDK properties window and can be edited.
    //Right-click a local variable to encapsulate it with getters and setters.

    private float m_accu = 0;
    private float m_elapse = 0.2f;
    
    public void setUpdatesPerSecond(int updates) {
        m_elapse = 1.0f / (float) updates;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        m_accu += tpf;
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        if(m_accu >= m_elapse) {
            super.controlRender(rm, vp);
            m_accu = 0;
        }
    }
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return super.cloneForSpatial(spatial);
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
    }
}
