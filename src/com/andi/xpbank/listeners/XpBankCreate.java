package com.andi.xpbank.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.andi.xpbank.XpBank;
import com.andi.xpbank.utils.MiscMethods;

public class XpBankCreate implements Listener{
	
	public XpBank xpBank = XpBank.getInstance();
	
	public XpBankCreate() {
		
		xpBank.getServer().getPluginManager().registerEvents(this, xpBank);
		
	}
	
	
	@EventHandler
	public void onBankCreate(SignChangeEvent ev) {
		
		if(xpBank.getVars().banksDisabled)
			return;
		
		Player p = ev.getPlayer();
		String playerName = MiscMethods.nameTrim(p);
		
		if(!xpBank.isPlayerLoaded(p.getName()))
			xpBank.getXpLoad().loadPlayerData(p);
		
		if(p != null) {
			
			if(ev.getLine(0).replaceAll(" ", "").equalsIgnoreCase("[XpBank]")) {

				if(!xpBank.isPlayerAllowedBank(p.getName())) {
					ev.setLine(0, "Insufficient");
					ev.setLine(1, "Permission");
					return;
				}
				
				if(!MiscMethods.bankAmountCheck(p.getName()) && !p.hasPermission("xp.create.unlimited")) {
					p.sendMessage(ChatColor.RED + "You have reached your limit on banks.");
					ev.setLine(0, "--Limit Reached--");
					return;
				} else {
					ev.setLine(0, "[XpBank]");
					ev.setLine(1, playerName);
					ev.setLine(2, "0");
					ev.setLine(3, "Level:" + " 0");
					p.sendMessage(ChatColor.GREEN + "XpBank sucessfully created.");
					XpBank.getInstance().getVars().addBankAmount(p.getName(), 
							(XpBank.getInstance().getVars().hasBankAmount(p.getName()) ? XpBank.getInstance().getVars().getBankAmount(p.getName()) + 1: 1));
					XpBank.getInstance().getVars().needSaving = true;
					return;
				}
				
			}
			
		}
		
	}

}
