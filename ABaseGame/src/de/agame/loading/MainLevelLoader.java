/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.loading;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.agame.appstates.GameState;
import de.agame.data.LevelIO;
import de.agame.data.StaticLocations;
import de.agame.entitys.EntityManager;
import de.agame.world.DayTimeManager;
import de.agame.world.MeshProvider;
import de.agame.world.WorldManager;

/**
 *
 * @author Fredie
 */
public class MainLevelLoader extends LoadingTask{
    
    //other members
    private Node m_root;
    private BulletAppState m_physics;
    
    private WorldManager m_worldmanager;
    
    //not levelrelated members
    private LevelIO m_io;
    private InputManager m_input;
    private AssetManager m_assets;
    private MeshProvider m_meshprovider;
    private EntityManager m_entitymanager;
    private ChaseCamera m_chasecam;
    private Application m_app;
    private FilterPostProcessor m_filters;
    
    //levelrelated members
    private DayTimeManager m_time;
    private Spatial m_statics;
    private Node m_dynamics;
    private Node m_whole;
    
    private NiftyJmeDisplay m_gui;
    
    public MainLevelLoader(Application app, Node root, NiftyJmeDisplay gui) {
        m_app = app;
        m_root = root;
        m_gui = gui;
    }
    
    @Override
    protected void load() {
        m_physics = m_app.getStateManager().getState(BulletAppState.class);
        
        if(m_physics == null) {
            m_physics = new BulletAppState();
            m_app.getStateManager().attach(m_physics);
        }
        
        setProgress(0.01f);
        
        m_io = new LevelIO();
        setProgress(0.02f);
        m_input = m_app.getInputManager();
        setProgress(0.03f);
        m_assets = m_app.getAssetManager();
        setProgress(0.04f);
        m_whole = new Node("world");
        setProgress(0.05f);
        m_whole.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        setProgress(0.06f);
        m_dynamics = new Node("dynamics");
        setProgress(0.1f);
        
        //setup camera
        Camera cam = m_app.getCamera();
        cam.setAxes(Vector3f.UNIT_X, Vector3f.UNIT_Y, Vector3f.UNIT_Z);
        m_chasecam = new ChaseCamera(cam, m_dynamics, m_input);
        m_chasecam.setDragToRotate(false);
        m_chasecam.setInvertVerticalAxis(true);
        m_chasecam.setDefaultDistance(5.0f);
        m_chasecam.setMaxDistance(7.0f);
        m_chasecam.setMinDistance(3.0f);
        m_chasecam.setMinVerticalRotation((float) Math.toRadians(-30.0d));
        
        setProgress(0.20f);
        
        
        //load meshes
        m_meshprovider = new MeshProvider();
        m_meshprovider.registerMesh("Linggify", (Node) m_assets.loadModel("Models/characters/Parkour Test Character/Character_ready.j3o"));
        setProgress(0.25f);
        m_meshprovider.registerMesh("theSlasher", (Node) m_assets.loadModel("Models/Items/Waffen/Kurz_Schwert_1/Kurz_Schwert_1.j3o"));
        setProgress(0.3f);
        m_meshprovider.registerMesh("MainLevel", (Node) m_io.loadStaticWorld(StaticLocations.WORLD_MAIN_LEVEL, m_assets));
        setProgress(0.55f);
        
        //setup entitymanager
        m_entitymanager = new EntityManager();
        m_entitymanager.finishInit(m_dynamics, m_gui, cam, m_chasecam, m_input, m_meshprovider);
        setProgress(0.65f);
        
        //prepare collisionshape
        Node level = m_meshprovider.getMesh("MainLevel");
        RigidBodyControl scenecontroll = new RigidBodyControl(CollisionShapeFactory.createMeshShape(level), 0);
        level.addControl(scenecontroll);
        setProgress(0.85f);
        
        //prepare daytime
        m_time = new DayTimeManager(m_assets);
        
        //prepare filters
        m_filters = new FilterPostProcessor(m_assets);
        m_filters.addFilter(m_time.getSunShadows());
        setProgress(0.95f);
        
        m_worldmanager = new WorldManager();
        m_worldmanager.initialize(m_io, m_input, m_assets, m_meshprovider, m_entitymanager, m_chasecam, m_app, m_time, m_dynamics, m_whole, m_gui, m_filters);
        
        setProgress(1.0f);
    }

    @Override
    public AbstractAppState getPreparedFollowUpState() {
        return new GameState(m_root, m_gui, m_worldmanager, m_physics);
    }

    @Override
    public void cleanup() {
        m_app = null;
        m_assets = null;
        m_chasecam = null;
        m_dynamics = null;
        m_entitymanager = null;
        m_filters = null;
        m_gui = null;
        m_input = null;
        m_io = null;
        m_meshprovider = null;
        m_physics = null;
        m_root = null;
        m_statics = null;
        m_time = null;
        m_whole = null;
        m_worldmanager = null;
    }
    
}
