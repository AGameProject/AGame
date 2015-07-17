/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.combat;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import de.agame.Items.Item;
import de.agame.entitys.EntityCreature;
import de.agame.entitys.animation.AnimRequest;
import de.agame.entitys.animation.AnimationManager;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.sets.EnviromentObservationSet;
import java.util.HashMap;

/**
 *
 * @author Fredie
 */
public class CombatManager{
    
    //the enviroment this CombatManager is bound to act in
    private EnviromentObservationSet m_enviroment;
    
    //the item currently held by the Entity
    private Item m_currentWeapon;
    
    //the attack used with the current item
    private Attack m_prevAttack;
    
    //the block used to block incoming attacks
    private Block m_prevBlock;
    
    //all Attacks this entity is capable of performing
    private HashMap<String, Attack> m_attacks;
    
    //all Blocks this entity is capable of doing
    private HashMap<String, Block> m_blocks;
    
    //the combatstatelistener used by this combat manager
    private CombatStateListener m_listener;
    
    //the animprovider used by this combatmanager
    private AnimationProvider m_provider;
    
    //the entity using this combatmanager
    private EntityCreature m_creature;
    
    //the needed channels to play animations
    private int m_channels[];
    
    //how long combat mode is still activated in seconds
    private float m_combatMode = 0;
    
    //if the entity is currently in combat mode
    private boolean m_isInCombatMode = false;
    
    public CombatManager(EntityCreature creature, AnimationProvider provider, CombatStateListener listener, EnviromentObservationSet enviroment) {
        m_attacks = new HashMap<String, Attack>();
        m_blocks = new HashMap<String, Block>();
        m_listener = listener;
        m_enviroment = enviroment;
        m_provider = provider;
        m_creature = creature;
    }

    public void initChannels(AnimationManager animmanager) {
        m_channels = animmanager.getChannels(new String[]{"HEAD", "ARMS", "TORSO", "LEGS"});
    }
    
    public void addAttack(String tag, Attack attack) {
        attack.setChannels(m_channels);
        m_attacks.put(tag, attack);
        
        setWeapon(m_currentWeapon);
    }
    
    public void removeAttack(String tag) {
        m_attacks.remove(tag);
    }
    
    public void addBlock(String tag, Block block) {
        block.setChannels(m_channels);
        m_blocks.put(tag, block);
        
        setWeapon(m_currentWeapon);
    }
    
    public void removeBlock(String tag) {
        m_blocks.remove(tag);
    }
    
    public void setWeapon(Item item) {
        m_currentWeapon = item;
        if(item != null) {
            m_prevAttack = m_attacks.get(item.getAttackTag());
            m_prevBlock = m_blocks.get(item.getBlockTag());
        } else {
            m_prevAttack = m_attacks.get("ATTACK_PUNCH");
            m_prevBlock = m_blocks.get("BLOCK_FIST");
        }
    }
    
    public boolean attack() {
        if(m_prevAttack != null) {
            AnimRequest request = m_prevAttack.execute(m_enviroment, m_provider, m_creature);
            
            if(request == null) return false;
            
            if(m_listener != null) m_listener.handleAnimRequest(request);
            
            m_combatMode = 30.0f;
            return true;
        }
        
        return false;
    }
    
    public boolean block() {
        //if there is no block to be executed, return
        if(m_prevBlock == null) return false;
        
        //attack is more important than block
        if((m_prevAttack == null || !m_prevAttack.isExecuting()) && !m_prevBlock.isBlocking()) {
            //abort the attack to stop combos
            if(m_prevAttack != null) m_prevAttack.abort();
            
            AnimRequest request = m_prevBlock.getBlockAnim(m_provider);
            if(request == null) return false;
            
            if(m_listener != null) m_listener.handleAnimRequest(request);
            
            m_combatMode = 30.0f;
            return true;
        }
        
        return false;
    }
    
    public float attacked(EntityCreature attacker, Item weapon) {
        m_combatMode = 30.0f;
        
        float damage = 1.0f;
        float impact = attacker.getStrength();
        boolean sharp = false;
        
        if(weapon != null) {
            damage = weapon.getHitDamage();
            impact *= weapon.getMass() + 1.0f;
            sharp = weapon.isSharp();
        }
        
        if(m_prevBlock != null && m_prevBlock.isBlocking()) {
            damage *= m_prevBlock.block(impact, sharp, m_creature.getMovementManager());
        } else {
            Vector3f relative = m_creature.getPosition().subtract(attacker.getPosition());
            relative.setY(0);
            relative.normalizeLocal();
            
            Vector3f attackdir = attacker.getMovementManager().getCurrentViewDirection();
            attackdir.normalizeLocal();
            
            Quaternion rot = new Quaternion();
            rot.fromAngleAxis((float)Math.toRadians(-90.0f), Vector3f.UNIT_Y);
            
            Vector3f attackleft = rot.mult(attackdir);
            
            float fronthemi = relative.dot(attackdir);
            float lefthemi = relative.dot(attackleft);
            
            AnimRequest request = new AnimRequest(m_provider.getRandomStumbleAnim(fronthemi, lefthemi), m_channels);
            m_listener.handleAnimRequest(request);
            
            Vector3f knockback = m_creature.getPosition().subtract(attacker.getPosition());
            knockback.normalizeLocal();
            knockback.multLocal(impact);
            
            m_creature.getMovementManager().knock(knockback, 0.4f);
        }
        
        return damage;
    }
    
    public void onUpdate(float dt) {
        if(m_combatMode > 0.0f) {
            m_combatMode -= dt;
            m_combatMode = m_combatMode < 0.0f ? 0.0f : m_combatMode;
        }
        
        if(m_combatMode > 0.0f && !m_isInCombatMode) {
            m_isInCombatMode = true;
            m_listener.setInCombatMode(m_isInCombatMode);
        } else if(m_combatMode == 0.0f && m_isInCombatMode) {
            m_isInCombatMode = false;
            m_listener.setInCombatMode(m_isInCombatMode);
        }
    }
}
