package de.turnlane.testing;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.PhysicsRayTestResult;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.List;
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
        Spatial[] baseObjects;
        int modelCount;
        
        public SpatialDistributor(int objectCount, float objectDensity, Spatial[] base){
            count = objectCount;
            density = objectDensity;
            objects = new Node();
            float length = count / density;
            boundaries = new Vector2f(length, length);
            random = new Random();
            baseObjects = base;
            modelCount = baseObjects.length;
        }
        
        public SpatialDistributor(Vector2f bounds, float objectDensity, Spatial[] base){
            count = (int)(bounds.x * bounds.y * objectDensity);
            density = objectDensity;
            objects = new Node();
            boundaries = bounds;
            random = new Random();
            baseObjects = base;
            modelCount = baseObjects.length;
        }

        
        private float randomPosition(Random random, float bounds){
            return bounds * (random.nextFloat() - 0.5f);
        }
        
        private PhysicsRayTestResult getTerrainTest(PhysicsSpace space, float x, float z) {
            List<PhysicsRayTestResult> results = space.rayTest(new Vector3f(x, 50, z), new Vector3f(x, -50, z));
            if(results.isEmpty()) return null;
            
            for(PhysicsRayTestResult result : results) {
                if(result.getCollisionObject().getCollisionGroup()  == PhysicsCollisionObject.COLLISION_GROUP_02)
                    return result;
            }
            
            return null;
        }
        
        private Vector3f interpretResultNormal(PhysicsRayTestResult result) {
            Vector3f normal = result.getHitNormalLocal();
            
            return result.getHitNormalLocal();
        }
        
        private float interpretResultHeight(PhysicsRayTestResult result) {
            return 50.0f - result.getHitFraction() * 100.0f;
        }
        
        private Vector3f randomScale(Random random, Vector3f min, Vector3f max){
            Vector3f scale;
            Vector3f rand = new Vector3f(
                    random.nextFloat(), random.nextFloat(), random.nextFloat());
            Vector3f delta = max.subtract(min);
            
            scale = min.add(delta.mult(rand));
            
            return scale;
        }    
        
        public Node distribute(PhysicsSpace physicsspace){
            
            for (int i = 0; i < count; i++) {
                int modelIndex = random.nextInt(modelCount);
                Spatial thisObject = baseObjects[modelIndex].clone();
                
                Transform treeTransform = new Transform();
                Vector3f treePos = new Vector3f();
                treePos.x = randomPosition(random, boundaries.x);
                treePos.z = randomPosition(random, boundaries.y);
                
                PhysicsRayTestResult result = getTerrainTest(physicsspace, treePos.x, treePos.z);
                
                if(result != null)
                    treePos.y = interpretResultHeight(result);
                treeTransform.setTranslation(treePos);

                Vector3f treeScale = randomScale(random, 
                        new Vector3f(0.8f,0.6f,0.8f), new Vector3f(1.0f,1.5f,1.0f));
                
                treeTransform.setScale(treeScale);
                Quaternion randomRot = new Quaternion().
                        fromAngleAxis(FastMath.PI*2*random.nextFloat(),
                        new Vector3f(0,1,0));
                treeTransform.setRotation(randomRot);
                thisObject.setLocalTransform(treeTransform);
                
                objects.attachChild(thisObject);
            }
            
            return objects;
        } 
}
