package com.andi.xpbank.data;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

import com.andi.xpbank.XpBank;

@SuppressWarnings("unused")
public class XpBankSave implements Runnable{

	@Override
	public void run() {
		
		FileConfiguration cfg = XpBank.getInstance().getConfigClass().loadConfig("XpBank");
		XpBankVars vars = XpBank.getInstance().getVars();
		
		
		if(vars.needSaving = false)
			return;
		
		cfg.set("settings." + "maxstorage." + "default", vars.serverMaxStorage);
		cfg.set("settings." + "limit." + "default", (vars.serverBankLimit == Integer.MAX_VALUE ? 0 : vars.serverBankLimit));
		cfg.set("settings." + "clickamt." + "default", vars.serverClickAmt);
		
		for(String player : vars.getLoadedPlayers()) {
			
			if(vars.hasMaxStorage(player)) {
				if(vars.getMaxStorage(player) == vars.serverMaxStorage)
					continue;
				
				cfg.set("players." + player + ".maxstorage", vars.getMaxStorage(player));
			}
			
			if(vars.hasBankAmount(player)) {
				cfg.set("players." + player + ".bankAmount", vars.getBankAmount(player));
			}
			
			if(vars.hasBankLimit(player)) {
				if(vars.getBankLimit(player) == vars.serverBankLimit)
					continue;
				
				cfg.set("players." + player + ".limit", vars.getBankLimit(player));
			}
			
			XpBank.getInstance().getConfigClass().saveConfig(cfg, "XpBank");
		}
		
	}

}
