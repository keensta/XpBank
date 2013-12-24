package com.andi.xpbank.dev;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.andi.xpbank.XpBank;
import com.andi.xpbank.data.XpBankVars;

public class DevMenu implements Listener{
	
	private MenuHandler menuH;
	private XpBank xpBank = XpBank.getInstance();
	@SuppressWarnings("unused")
	private XpBankVars vars = xpBank.getVars();
	
	String[] allowedUsers = {};
	HashMap<String, String> devMenuOrder = new HashMap<String, String>();

	//Nothing here works unless there is a allowedUser however its currently Impossible to add them other then editting the code itself. 
	//So this is usless and is not used at all...
	public DevMenu (MenuHandler menuH) {
		
		StringBuilder sb = new StringBuilder();
		for(String users : allowedUsers) {
			sb.append(users);
			sb.append(", ");
		}
		
		Logger.getLogger("Minecraft").info("XpBank Dev Menu has been activated!");
		Logger.getLogger("Minecraft").info("AllowedUsers: " + sb.toString());
		
		xpBank.getServer().getPluginManager().registerEvents(this, xpBank);
		this.menuH = menuH;
		
		
	}



	private void setupDevMenu(Player p) {
		LinkedList <MenuOptions> options = new LinkedList <MenuOptions>();
		
		//Sign Modification Row
		String[] loreS = {"This row is for Sign modifying", ""};
		String[] loreS1 = {"Sets the first sign", "you click to your name"};
		String[] loreS2 = {"Effects the first sign you click", ""};
		options.add(menuH.addOption("SignModification", loreS, false, Material.SIGN, (short) 0, 0, 0));
		options.add(menuH.addOption("Set To Owner", loreS1, true, Material.WRITTEN_BOOK, 1, 0));
		options.add(menuH.addOption("MaxSignExp", loreS2, true, Material.DIAMOND, 2, 0));
		options.add(menuH.addOption("ResetSign", loreS2, true, Material.FIRE, 3, 0));
		
		//Player Modification Row 
		String[] loreP = {"This row is for Player modifying", ""};
		options.add(menuH.addOption("PlayerModification", loreP, false, Material.SKULL_ITEM, (short) 0, 0, 1));
		
		menuH.createMenu(p, "XpBank DevMenu", 54, options, true);
	}
	
	@EventHandler
	public void devMenuOpen(PlayerInteractEvent ev) {
		Player p = ev.getPlayer();
		
		
		//Opens DevMenu
		if(p.isSneaking() && p.getItemInHand().getType() == Material.BOOK) {
			for(String users : allowedUsers) {
				if(users.equals(p.getName()))
					setupDevMenu(p); break;
			}
		}
		
	}
	
	@EventHandler
	public void onMenuClickEvent(MenuInteractEvent ev) {
		Player p = ev.getPlayer();
		
		if(ev.getSelectedItemName() == null)
			return;
		
		switch (ev.getSelectedItemName()) {
		case "Set To Owner":
				p.sendMessage(ChatColor.AQUA + "Click a sign to set ownership.");
				devMenuOrder.put(p.getName(), "SetToOwner");
			break;
		
		case "MaxSignExp":
				p.sendMessage(ChatColor.AQUA + "Click a sign to max it's stats.");
				devMenuOrder.put(p.getName(), "MaxSignExp");
			break;
		
		case "ResetSign":
				p.sendMessage(ChatColor.AQUA + "Click a sign to reset it's stats.");
				devMenuOrder.put(p.getName(), "ResetSign");
			break;
		
		case "CreateSign":
				p.sendMessage(ChatColor.AQUA + "Click agains't any block to create a sign");
				devMenuOrder.put(p.getName(), "CreateSign");
			break;
		}
		
		p.closeInventory();
	}
	

}
