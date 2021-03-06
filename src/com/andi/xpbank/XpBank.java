package com.andi.xpbank;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.andi.xpbank.commands.XpBankCommands;
import com.andi.xpbank.data.Config;
import com.andi.xpbank.data.XpBankLoad;
import com.andi.xpbank.data.XpBankSave;
import com.andi.xpbank.data.XpBankVars;
import com.andi.xpbank.dev.DevMenu;
import com.andi.xpbank.dev.MenuHandler;
import com.andi.xpbank.listeners.XpBankBreak;
import com.andi.xpbank.listeners.XpBankCreate;
import com.andi.xpbank.listeners.XpBankInteract;
import com.andi.xpbank.listeners.XpBankSpawnerExp;

public class XpBank extends JavaPlugin {

	public Logger log = Logger.getLogger("Minecraft");
	
	//Classes
	public static XpBank XpBank;
	private Config config;
	private XpBankVars vars;
	private XpBankLoad xpLoad;
	
	//Listeners
	private XpBankCreate bankCreate;
	private XpBankBreak bankBreak;
	private XpBankInteract bankInteract;
	private XpBankSpawnerExp spawnerExp;
	
	//Command Class
	private XpBankCommands bankCommands;
	
	//DevMenu
	@SuppressWarnings("unused")
	private DevMenu devMenu;
	private MenuHandler menuH;
	
	public void onEnable() {

		XpBank = this;
		config = new Config();
		
		config.checkConfig("XpBank");
		
		vars = new XpBankVars();
		xpLoad = new XpBankLoad();
		
		bankCreate = new XpBankCreate();
		bankBreak = new XpBankBreak();
		bankInteract = new XpBankInteract();
		spawnerExp = new XpBankSpawnerExp();
		
		bankCommands = new XpBankCommands();
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,  new XpBankSave(), 20 * 15, (20 * 60) * 2);

		menuH = new MenuHandler(this);
		devMenu = new DevMenu(menuH);
		
		log.info("[XpBank] Is now enabled.");

	}

	public void onDisable() {

		log.info("[XpBank] Is now disabled.");

	}
	
	public static XpBank getInstance() {
		return XpBank;
	}
	
	public Config getConfigClass() {
		return config;
	}
	
	public XpBankVars getVars() {
		return vars;
	}
	
	public XpBankLoad getXpLoad() {
		return xpLoad;
	}
	
	public XpBankCreate getXpBankCreate() {
		return bankCreate;
	}
	
	public XpBankBreak getXpBankBreak() {
		return bankBreak;
	}
	
	public XpBankInteract getXpBankInteract() {
		return bankInteract;
	}
	
	public XpBankSpawnerExp getSpawnerExp() {
		return spawnerExp;
	}
	
	public XpBankCommands getXpBankCmd() {
		return bankCommands;
	}
	
	public boolean isPlayerLoaded(String name) {
		return vars.isPlayerLoaded(name);
	}
	
	public boolean isPlayerAllowedBank(String name) {
		return Bukkit.getPlayer(name).hasPermission("xp.create");
	}

}
