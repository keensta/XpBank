package com.andi.xpbank.utils;

import org.bukkit.entity.Player;

import com.andi.xpbank.XpBank;

public class MiscMethods {
	
	public static XpBank xpBank = XpBank.getInstance();
	
	public MiscMethods() {
	}
	
	public static String nameTrim(Player p) {
		return (p.getName().length() > 15 ? p.getName().substring(0, 15) : p.getName());
	}
	
	public static boolean bankAmountCheck(String name) {

		double bankLimit = (xpBank.getVars().hasBankLimit(name) ? xpBank.getVars().getBankLimit(name) : xpBank.getVars().serverBankLimit);
		double bankAmount = (xpBank.getVars().hasBankAmount(name) ? xpBank.getVars().getBankAmount(name) : 0);
		
		return (bankAmount >= bankLimit ? false : true);
	}

}
