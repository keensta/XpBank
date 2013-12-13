package com.andi.xpbank.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.andi.xpbank.XpBank;
import com.andi.xpbank.utils.ExperienceManager;

public class XpBankVars {
	
	private HashMap<String, Double> maxStorage = new HashMap<String, Double>();
	private HashMap<String, Double> bankLimit = new HashMap<String, Double>();
	private HashMap<String, Double> bankAmount = new HashMap<String, Double>();
	private HashMap<String, ExperienceManager> expManager = new HashMap<String, ExperienceManager>();
	
	private HashMap<String, Integer> playerList = new HashMap<String, Integer>();
	
	private List<String> loadedPlayers = new ArrayList<String>();
	//TODO: Add a server wide default click amount & a spawner exp stop.
	public double serverMaxStorage;
	public double serverBankLimit;
	
	public boolean needSaving = false;
	
	public boolean banksDisabled = false;
	
	public XpBankVars() {
		setupDefaults();
	}
	
	public void setupPlayer(String name, double d, double d2, double d3) {
		if(d != 0)
			maxStorage.put(name, d);
		bankLimit.put(name, d2);
		bankAmount.put(name, d3);
		expManager.put(name, new ExperienceManager(Bukkit.getPlayer(name)));
		
		loadedPlayers.add(name);
	}
	
	public void setupDefaults() {
		FileConfiguration cfg = XpBank.getInstance().getConfigClass().loadConfig("XpBank");
		
		double maxStorage = cfg.getDouble("settings." + "maxstorage." + "default");
		double bankAmount = (cfg.getInt("settings." + "limit." + "default") == 0 ? Integer.MAX_VALUE : cfg.getInt("settings." + "limit." + "default"));
		boolean bankDisable = cfg.getBoolean("settings." + "disableBanks");
		
		setDefaults(maxStorage, bankAmount, bankDisable);
	}
	
	public void setDefaults(double d, double d2, boolean b) {
		serverMaxStorage = d;
		serverBankLimit = d2;
		banksDisabled = b;
	}
	
	public void removePlayer(String name) {
		maxStorage.remove(name);
		bankLimit.remove(name);
		bankAmount.remove(name);
	}
	
	public void purgeCache() {
		maxStorage.clear();
		bankLimit.clear();
		bankAmount.clear();
	}
	
	public boolean hasMaxStorage(String name) {
		return (maxStorage.containsKey(name) ? true : false);
	}
	
	public void addMaxStorage(String name, double d) {
		maxStorage.put(name, d);
	}
	
	public Double getMaxStorage(String name) {
		return maxStorage.get(name);
	}
	
	public Set<String> getMaxStorageSet() {
		return maxStorage.keySet();
	}
	
	public boolean hasBankLimit(String name) {
		return (bankLimit.containsKey(name) ? true : false);
	}
	
	public void addBankLimit(String name, double d) {
		bankLimit.put(name, d);
	}
	
	public Double getBankLimit(String name) {
		return bankLimit.get(name);
	}
	
	public Set<String> getBankLimitSet() {
		return bankLimit.keySet();
	}
	
	public boolean hasBankAmount(String name) {
		return (bankAmount.containsKey(name) ? true : false);
	}
	
	public void addBankAmount(String name, double d) {
		bankAmount.put(name, d);
	}
	
	public Double getBankAmount(String name) {
		return bankAmount.get(name);
	}
	
	public Set<String> getBankAmountSet() {
		return bankAmount.keySet();
	}
	
	public boolean hasExperianceManager(String name) {
		return (expManager.containsKey(name) ? true : false);
	}
	
	public ExperienceManager getExperienceManager(String name) {
		
		if(!expManager.containsKey(name)) {
			expManager.put(name, new ExperienceManager(Bukkit.getPlayer(name)));
			return expManager.get(name);
		}
		
		return expManager.get(name);
	}
	
	public boolean hasPlayerList(String name) {
		return (playerList.containsKey(name) ? true : false);
	}
	
	public void addPlayerList(String name, int i) {
		playerList.put(name, i);
	}
	
	public Integer getPlayerList(String name) {
		return playerList.get(name);
	}
	
	public boolean isPlayerLoaded(String name) {
		return (loadedPlayers.contains(name) ? true : false);
	}
	
	public void addPlayerLoaded(String name) {
		loadedPlayers.add(name);
	}

	public List<String> getLoadedPlayers() {
		return loadedPlayers;
	}
}
