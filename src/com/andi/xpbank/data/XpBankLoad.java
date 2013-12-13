package com.andi.xpbank.data;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.andi.xpbank.XpBank;

public class XpBankLoad {
	
	public void loadPlayerData(Player p) {
		FileConfiguration cfg = XpBank.getInstance().getConfigClass().loadConfig("XpBank");
		String playerName = p.getName();
		
		if(!cfg.contains("players." + playerName)) {
			tryNewSetup(playerName);
			return;
		}

		XpBank xpBank = XpBank.getInstance();
		
		double maxStorage = (cfg.contains("players." + playerName + ".maxstorage") ? cfg.getDouble("players." + playerName + ".maxstorage") : 
			xpBank.getVars().serverMaxStorage);
		double bankAmount = (cfg.contains("players." + playerName + ".bankAmount") ? cfg.getDouble("players." + playerName + ".bankAmount") : 
			0);
		double bankLimit = (cfg.contains("players." + playerName + ".limit") ? cfg.getDouble("players." + playerName + ".limit") : 
			xpBank.getVars().serverBankLimit);
		
		XpBank.getInstance().getVars().setupPlayer(playerName, maxStorage, bankLimit, bankAmount);
	}
	
	private void tryNewSetup(String playerName) {
		Player p = Bukkit.getPlayer(playerName);
		
		if(p == null)
			return;
		
		if(!p.hasPermission("xp.create"))
			return;
		
		FileConfiguration cfg = XpBank.getInstance().getConfigClass().loadConfig("XpBank");
		XpBank xpBank = XpBank.getInstance();
		
		cfg.set("players." + playerName + ".bankAmount", 0);
		cfg.set("players." + playerName + ".maxstorage", xpBank.getVars().serverMaxStorage);
		cfg.set("players." + playerName + ".limit", xpBank.getVars().serverBankLimit);		
		
		XpBank.getInstance().getConfigClass().saveConfig(cfg, "XpBank");
		loadPlayerData(p);
	}

}
