/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.loading;

import com.jme3.app.state.AbstractAppState;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Fredie
 */
public abstract class LoadingTask implements Runnable{

    private float m_progress = 0.0f;
    private Lock m_progressLock = new ReentrantLock();
    
    protected abstract void load();
    
    public abstract AbstractAppState getPreparedFollowUpState();
    
    public abstract void cleanup();
    
    protected void setProgress(float progress) {
        m_progressLock.lock();
        
        m_progress = progress;
        
        m_progressLock.unlock();
    }
    
    public float getProgress() {
        m_progressLock.lock();
        
        float progress = m_progress;
        
        m_progressLock.unlock();
        
        return progress;
    }
    
    @Override
    public void run() {
        m_progress = 0;
        load();
    }
}
