/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.world;

import com.jme3.scene.Node;
import java.util.HashMap;

/**
 *
 * @author Fredie
 */
public class MeshProvider {
    private HashMap<String, Node> m_meshes = new HashMap<String, Node>();
    
    public void registerMesh(String name, Node mesh) {
        m_meshes.put(name, mesh);
    }
    
    public Node getMesh(String name) {
        return m_meshes.get(name);
    }
}
