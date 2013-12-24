package com.andi.xpbank.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;

import com.andi.xpbank.XpBank;

public class XpBankSpawnerExp implements Listener{ 
	
	
	public XpBank xpBank = XpBank.getInstance();
	private ArrayList<UUID> spawnerSpawned = new ArrayList<UUID>();
	
	public XpBankSpawnerExp() {
		
		xpBank.getServer().getPluginManager().registerEvents(this, xpBank);
		
	}
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent ev) {
		
		if(xpBank.getVars().spawnerExp)
			return;
		
		if(ev.isCancelled())
			return;
		
		if(ev.getSpawnReason() == SpawnReason.SPAWNER) {
			spawnerSpawned.add(ev.getEntity().getUniqueId());
		}
		
	}
	
	@EventHandler
	public void onMobDeath(EntityDeathEvent ev) {
		
		if(xpBank.getVars().spawnerExp)
			return;
		
		if(spawnerSpawned.contains(ev.getEntity().getUniqueId())) {
			ev.setDroppedExp(0);
		}
		
	}

}
