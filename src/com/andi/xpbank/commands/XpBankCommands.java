package com.andi.xpbank.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import com.andi.xpbank.XpBank;

public class XpBankCommands implements Listener, CommandExecutor{
	
	public XpBank xpBank = XpBank.getInstance();
	
	public XpBankCommands() {
		
		xpBank.getCommand("xp").setExecutor(this);
		
	}
	
	/*Commands Info
	 * 2 Types of commands Admin,Modify
	 * 
	 * Admin: 
	 * 	setLevel : Sets the target players level to the given [Argument]. - Done
	 * 	addLevel : Adds given [Argument] to current players level. - Done
	 *  removeLevel : Removes given [Argument] from current players level. - Done
	 * 	setMax : Sets the servers max storage as the given [Argument]. - Done
	 * 	setLimit : Sets the servers bank limit as the given [Argument]. - Done
	 * 	setPlyMax : Sets the players max storage as the given [Argument].
	 * 	setPlyLimit : Sets the players bank limit as the given [Argument].
	 * 	setPlyAmt : Sets the players current bank amount as the given [Arugment].
	 *	resetPly : Resets all values of the player BankAmount,MaxStorage,BankLimit.
	 *
	 * Modify:
	 * 	clickAmt : Sets the amount of xp deposited per click.
	 * 	
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = null;
		boolean isConsole = false;
		
	
		if(sender instanceof Player)
			p = (Player) sender;
		else {
			isConsole = true;
		}
		
		if(isConsole) {
			sender.sendMessage(ChatColor.RED + "Console Commands are currently unavailable in XpBankRework (Beta)");
			return true;
		}
		
		if(args.length <= 0) {
			sender.sendMessage(ChatColor.RED + "Sorry but help is currently short of help with XpBankRework (Beta) it will be fully up soon");
			sender.sendMessage(ChatColor.GREEN + "/xp clickAmt [Amount] - Sets amount to add/remove when interacting with bank.");
			return true;
		} else {
			
			//Modify Commands Begin
			if(args[0].equalsIgnoreCase("clickAmt") && p.hasPermission("xp.quickstore")) {
				if(args.length < 2) {
					sender.sendMessage(ChatColor.RED + "Command help /xp clickAmt [Amount]");
					return false;
				}
				
				int i = (int) Integer.parseInt(args[1]);
				
				xpBank.getVars().addPlayerList(p.getName(), i);				
				p.sendMessage(ChatColor.GREEN + "You have set your click amount to " + i);
				
				return true;
			}
			//Modify Commands End
			
			//Admin Commands
			if(p.hasPermission("xpbank.admin")) {
				
				//Level Commands Begin
				if(args[0].equalsIgnoreCase("setLevel")) {
					
					if(args.length < 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setLevel [level] [player]");
						return false;
					}
					
					int i = (int) Integer.parseInt(args[1]);
					String tp = args[2];
					
					Player targetPlayer = Bukkit.getPlayer(tp);
					
					if(targetPlayer == null || !(targetPlayer.isOnline()) ) {
						sender.sendMessage(ChatColor.RED + "Targetplayer is either not online or is incorrect.");
						return false;
					}
					
					targetPlayer.setLevel(i);
					sender.sendMessage(ChatColor.GREEN + "You have set " + targetPlayer.getDisplayName() + " level to " + i);
					targetPlayer.sendMessage(ChatColor.AQUA + "You level has been set to " + i + " By a Admin");
					
					return true;
				}
				
				if(args[0].equalsIgnoreCase("addLevel")) {
					
					if(args.length < 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp addLevel [level] [player]");
						return false;
					}
					
					int i = (int) Integer.parseInt(args[1]);
					String tp = args[2];
					
					Player targetPlayer = Bukkit.getPlayer(tp);
					
					if(targetPlayer == null || !(targetPlayer.isOnline()) ) {
						sender.sendMessage(ChatColor.RED + "Targetplayer is either not online or is incorrect.");
						return false;
					}
					
					int newL = targetPlayer.getLevel() + i;
					
					targetPlayer.setLevel(newL);
					sender.sendMessage(ChatColor.GREEN + "You have added " + newL + " levels to " + targetPlayer.getDisplayName());
					targetPlayer.sendMessage(ChatColor.AQUA + "You have been give " + i + " levels by a Admin");
					
					return true;
				}
				
				if(args[0].equalsIgnoreCase("removeLevel")) {
					
					if(args.length < 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp removeLevel [level] [player]");
						return false;
					}
					
					int i = (int) Integer.parseInt(args[1]);
					String tp = args[2];
					
					Player targetPlayer = Bukkit.getPlayer(tp);
					
					if(targetPlayer == null || !(targetPlayer.isOnline()) ) {
						sender.sendMessage(ChatColor.RED + "Targetplayer is either not online or is incorrect.");
						return false;
					}
					
					int newL = targetPlayer.getLevel() - i;
					
					targetPlayer.setLevel(newL);
					sender.sendMessage(ChatColor.GREEN + "You have taken " + newL + " levels from " + targetPlayer.getDisplayName());
					targetPlayer.sendMessage(ChatColor.AQUA + "You have had " + i + " levels taken by a Admin");
					
					return true;
				}
				//Level Commands End
				
				//Server Handling Command Begin
				if(args[0].equalsIgnoreCase("setMax")) {
					
					if(args.length < 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setMax [amount]");
						return false;
					}
					
					int i = (int) Integer.parseInt(args[1]);
					
					xpBank.getVars().serverMaxStorage = i;
					xpBank.getVars().needSaving = true;
					
					sender.sendMessage(ChatColor.GREEN + "You have set server max storage to " + i);
					
					return true;
				}
				
				if(args[0].equalsIgnoreCase("setLimit")) {
					
					if(args.length < 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setLimit [amount]");
						return false;
					}
					
					int i = (int) Integer.parseInt(args[1]);
					
					xpBank.getVars().serverBankLimit = i;
					xpBank.getVars().needSaving = true;
					
					sender.sendMessage(ChatColor.GREEN + "You have set server bank limit to " + i);
					
					return true;
				}
				//Server Handling Command End
				
				//Player Handling Command Begin
				if(args[0].equalsIgnoreCase("setPlyMax")) {
					
					if(args.length < 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setPlyMax [amount] [player]");
						return false;
					}
					
					int i = (int) Integer.parseInt(args[1]);
					String tp = args[2];
					
					Player targetPlayer = Bukkit.getPlayer(tp);
					
					if(targetPlayer == null || !(targetPlayer.isOnline()) ) {
						sender.sendMessage(ChatColor.RED + "Targetplayer is either not online or is incorrect.");
						return false;
					}
					
					if(!(xpBank.getVars().isPlayerLoaded(targetPlayer.getName())))
						xpBank.getXpLoad().loadPlayerData(targetPlayer);
					
					xpBank.getVars().addMaxStorage(targetPlayer.getName(), (double) i);
					xpBank.getVars().needSaving = true;
					
					sender.sendMessage(ChatColor.GREEN + "You have set " + targetPlayer.getName() + " max storage to " + i);
					
					return true;
				}
				
				if(args[0].equalsIgnoreCase("setPlyLimit")) {
					
					if(args.length < 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setPlyLimit [amount] [player]");
						return false;
					}
					
					int i = (int) Integer.parseInt(args[1]);
					String tp = args[2];
					
					Player targetPlayer = Bukkit.getPlayer(tp);
					
					if(targetPlayer == null || !(targetPlayer.isOnline()) ) {
						sender.sendMessage(ChatColor.RED + "Targetplayer is either not online or is incorrect.");
						return false;
					}
					
					if(!(xpBank.getVars().isPlayerLoaded(targetPlayer.getName())))
						xpBank.getXpLoad().loadPlayerData(targetPlayer);
					
					xpBank.getVars().addBankLimit(targetPlayer.getName(), (double) i);
					xpBank.getVars().needSaving = true;
					
					sender.sendMessage(ChatColor.GREEN + "You have set " + targetPlayer.getName() + " bank limit to " + i);
					
					return true;
				}
				
				if(args[0].equalsIgnoreCase("setPlyAmt")) {
					
					if(args.length < 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setPlyAmt [amount] [player]");
						return false;
					}
					
					int i = (int) Integer.parseInt(args[1]);
					String tp = args[2];
					
					Player targetPlayer = Bukkit.getPlayer(tp);
					
					if(targetPlayer == null || !(targetPlayer.isOnline()) ) {
						sender.sendMessage(ChatColor.RED + "Targetplayer is either not online or is incorrect.");
						return false;
					}
					
					if(!(xpBank.getVars().isPlayerLoaded(targetPlayer.getName())))
						xpBank.getXpLoad().loadPlayerData(targetPlayer);
					
					xpBank.getVars().addBankAmount(targetPlayer.getName(), (double) i);
					xpBank.getVars().needSaving = true;
					
					sender.sendMessage(ChatColor.GREEN + "You have set " + targetPlayer.getName() + " bank amount to " + i);
					
					return true;
				}
				
				if(args[0].equalsIgnoreCase("resetPly")) {
					
					if(args.length < 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp resetPly [player]");
						return false;
					}
					String tp = args[1];
					
					Player targetPlayer = Bukkit.getPlayer(tp);
					
					if(targetPlayer == null || !(targetPlayer.isOnline()) ) {
						sender.sendMessage(ChatColor.RED + "Targetplayer is either not online or is incorrect.");
						return false;
					}
					
					if(!(xpBank.getVars().isPlayerLoaded(targetPlayer.getName())))
						xpBank.getXpLoad().loadPlayerData(targetPlayer);
					
					xpBank.getVars().addBankAmount(targetPlayer.getName(), 0.0);
					xpBank.getVars().addBankLimit(targetPlayer.getName(), xpBank.getVars().serverBankLimit);
					xpBank.getVars().addMaxStorage(targetPlayer.getName(), xpBank.getVars().serverMaxStorage);
					xpBank.getVars().needSaving = true;
					
					sender.sendMessage(ChatColor.GREEN + "You have reset " + targetPlayer.getName() + " xpbank account to default");
					
					return true;
				}
				//Player Handling Commands End
			}
			//Admin Commands End
		}
		
		
		return false;
	} 

}
