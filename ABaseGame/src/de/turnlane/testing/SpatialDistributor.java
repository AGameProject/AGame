package de.turnlane.testing;

import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 * 
 * @author Leonard
 */
public class SpatialDistributor {

        int count;
        float density;
        Node objects;
        Vector2f boundaries;
        Random random;
        Spatial baseObject;
        
        public SpatialDistributor(int objectCount, float objectDensity, Spatial base){
            count = objectCount;
            density = objectDensity;
            objects = new Node();
            float length = count / density;
            boundaries = new Vector2f(length, length);
            random = new Random();
            baseObject = base;
        }
        
        public SpatialDistributor(Vector2f bounds, float objectDensity, Spatial base){
            count = (int)(bounds.x * bounds.y * objectDensity);
            density = objectDensity;
            objects = new Node();
            boundaries = bounds;
            random = new Random();
            baseObject = base;
        }

        
    private float randomPosition(Random random, float bounds){
        return bounds*random.nextFloat()-0.5f*bounds;
    }
        
        public Node distribute(){
            
            for (int i = 0; i < count; i++) {
                Spatial thisObject = baseObject.clone();
                Transform treeTransform = new Transform();
                Vector3f treePos = new Vector3f();
                treePos.x = randomPosition(random, boundaries.x);
                treePos.z = randomPosition(random, boundaries.y);
                treeTransform.setTranslation(treePos);

                Vector3f treeScale = new Vector3f(1,1,1);
                treeTransform.setScale(treeScale);

                thisObject.setLocalTransform(treeTransform);
                
                objects.attachChild(thisObject);
            }
            
            return objects;
        } 
}
