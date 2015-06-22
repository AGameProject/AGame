/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.combat;

import de.agame.Items.Item;
import de.agame.entitys.animation.AnimRequest;
import de.agame.entitys.animation.AnimationManager;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.sets.EnviromentObservationSet;
import java.util.HashMap;

/**
 *
 * @author Fredie
 */
public class CombatManager {
    
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
    
    //the needed channels to play animations
    private int m_channels[];
    
    public CombatManager(AnimationProvider provider, CombatStateListener listener, EnviromentObservationSet enviroment) {
        m_attacks = new HashMap<String, Attack>();
        m_blocks = new HashMap<String, Block>();
        m_listener = listener;
        m_enviroment = enviroment;
        m_provider = provider;
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
        m_blocks.put(tag, block);
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
            m_prevAttack = m_attacks.get("PUNCH");
            m_prevBlock = null;
        }
    }
    
    public boolean attack() {
        if(m_prevAttack != null) {
            AnimRequest request = m_prevAttack.execute(m_enviroment, m_provider);
            
            if(request == null) return false;
            
            if(m_listener != null) m_listener.handleAnimRequest(request);
            
            return true;
        }
        
        return false;
    }
}
