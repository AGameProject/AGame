/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.nifty.progressbar;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.AbstractController;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;

/**
 *
 * @author Fredie
 */
public class ProgressBarControl extends AbstractController{
    
    private Element m_bar;
    private TextRenderer m_text;
    
    private float m_progress = 0;
    
    private boolean m_bound = false;
    
    @Override
    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes) {
        super.bind(element);
        m_bar = element.findElementByName("#inner");
        m_text = element.findElementByName("#text").getRenderer(TextRenderer.class);
        
        m_bound = true;
    }

    public void setProgress(float progress) {
        m_progress = progress;
        
        if(m_bound) {
            m_bar.setWidth((int) ((float) m_bar.getParent().getWidth() * m_progress));
            m_text.setText((int) (m_progress * 100.0f) + "%");
        }
    }
    
    public float getProgress() {
        return m_progress;
    }

    public void onStartScreen() {
    }

    public boolean inputEvent(NiftyInputEvent inputEvent) {
        return false;
    }
    
}
