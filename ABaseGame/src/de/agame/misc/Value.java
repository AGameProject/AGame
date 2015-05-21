/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.misc;

/**
 *
 * @author Fredie
 */
public class Value<T> {
    
    private T m_value;
    
    public Value(T value) {
        setValue(value);
    }
    
    public void setValue(T value) {
        m_value = value;
    }
    
    public T getValue() {
        return m_value;
    }
}
