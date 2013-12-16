package com.andi.xpbank.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.andi.xpbank.XpBank;
import com.andi.xpbank.utils.ExperienceManager;
import com.andi.xpbank.utils.MiscMethods;

public class XpBankInteract implements Listener{
	
	public XpBank xpBank = XpBank.getInstance();
	
	public XpBankInteract() {
		
		xpBank.getServer().getPluginManager().registerEvents(this, xpBank);
		
	}
	
	@SuppressWarnings("incomplete-switch")
	@EventHandler
	public void onBankInteract(PlayerInteractEvent ev) {
		
		if(ev.isCancelled())
			return;
		
		if(ev.getClickedBlock() == null)
			return;
		
		Block b = ev.getClickedBlock();
		
		if(b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST) {
			
			Sign sign = (Sign)b.getState();
			
			if(!sign.getLine(0).replaceAll(" ", "").equalsIgnoreCase("[XpBank]"))
				return;
			
			Player p = ev.getPlayer();
			String playerName = MiscMethods.nameTrim(p);
			String owner = sign.getLine(1);
			
			if(playerName.equals(owner)) {
				
				if(!p.hasPermission("xp.create"))
					return;
				
				if(!xpBank.isPlayerLoaded(p.getName()))
					xpBank.getXpLoad().loadPlayerData(p);
				
				boolean insufficient = false;
				double maxStorage = (xpBank.getVars().hasMaxStorage(p.getName()) ? xpBank.getVars().getMaxStorage(p.getName()) : xpBank.getVars().serverMaxStorage);
				ExperienceManager em = xpBank.getVars().getExperienceManager(p.getName());
				double value = Double.parseDouble(sign.getLine(2));
				int amount = (xpBank.getVars().hasPlayerList(p.getName()) ? xpBank.getVars().getPlayerList(p.getName()) : xpBank.getVars().serverClickAmt);
				
				switch (ev.getAction()) {
				case RIGHT_CLICK_BLOCK:
					
					if(amount <= value || value - amount >= 0) {
						value = value - amount;
						em.setExp(em.getCurrentExp() + amount);
						updateSign(sign, value, em);
					} else {
						insufficient = true;
					}
					
					break;

				case LEFT_CLICK_BLOCK:
					
					if(amount < 0) {
						p.sendMessage(ChatColor.RED + "You can not store a negative amount.");
						return;
					}
					
					if(value >= maxStorage || value + amount >= maxStorage) {
						p.sendMessage(ChatColor.RED + "This bank is full!");
						return;
					}
					
					if(em.getCurrentExp() >= amount) {
						value = value + amount;
						em.setExp(em.getCurrentExp() - amount);
						updateSign(sign, value, em);
					} else {
						insufficient = true;
					}
					
					break;
				}
				
				if(insufficient) {
					p.sendMessage(ChatColor.RED + "Insufficient funds!");
					if(amount > 1) {
						p.sendMessage(ChatColor.RED + "Try setting a lower amount.");
					}
				}
				
			} else {
				p.sendMessage(ChatColor.RED + "This is not your sign.");
			}
			
		}
		
	}
	
	public void updateSign(Sign sign, double value, ExperienceManager em) {
		sign.setLine(2, Double.toString(value));
		sign.setLine(3, "Level: " + em.getLevelForExp((int) value));
		sign.update(true);
	}

}
