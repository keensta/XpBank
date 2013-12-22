package com.andi.xpbank.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import com.andi.xpbank.XpBank;
import com.andi.xpbank.utils.ExperienceManager;
import com.andi.xpbank.utils.MiscMethods;

public class XpBankCommands implements Listener, CommandExecutor{
	
	public XpBank xpBank = XpBank.getInstance();
	
	public XpBankCommands() {
		
		xpBank.getCommand("xp").setExecutor(this);
		
	}
	
	/*Commands Info
	 * 2 Types of commands Admin,Modify
	 * 
	 * Admin: 
	 * 	setLevel : Sets the target players level to the given [Argument]. 
	 * 	addLevel : Adds given [Argument] to current players level. 
	 *  removeLevel : Removes given [Argument] from current players level. 
	 * 	setMax : Sets the servers max storage as the given [Argument].
	 * 	setLimit : Sets the servers bank limit as the given [Argument].
	 * 	setClickAmt : Sets the servers default click amount as the given [Argument].
	 * 	setPlyMax : Sets the players max storage as the given [Argument].
	 * 	setPlyLimit : Sets the players bank limit as the given [Argument].
	 * 	setPlyAmt : Sets the players current bank amount as the given [Arugment].
	 *	resetPly : Resets all values of the player BankAmount,MaxStorage,BankLimit.
	 *
	 * Modify:
	 * 	clickAmt : Sets the amount of xp deposited per click.
	 * 	pay : Pays [Player] the xp on which you entered.
	 * 	calc : Works out from your current level how much xp you need to get to [Level].
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
			sender.sendMessage(ChatColor.RED + "[XpBank] Use '/xp help'");
			
			return true;
		} else {
			
			//TODO: Make a better help section this is quickly throw together for release of plugin its a workaround and NEEDS to be changed
			if(args[0].equalsIgnoreCase("help")) {
				
				if(args.length == 1){
					sender.sendMessage(ChatColor.GRAY + "------------------------XpBank-----------------------");
					sender.sendMessage(ChatColor.GREEN + "/xp clickAmt [Amount] " + ChatColor.GRAY + " - " + ChatColor.GOLD 
								+ "Sets amount to add/remove when interacting with bank.");
					sender.sendMessage(ChatColor.GREEN + "/xp calc [Level] " + ChatColor.GRAY + " - " + ChatColor.GOLD 
								+ "Calculates the needed xp to reach [Level].");
					sender.sendMessage(ChatColor.GREEN + "/xp pay [Amount] [Player] " + ChatColor.GRAY + " - " + ChatColor.GOLD 
								+ "Pays [Player] in xp by the [Amount] specified.");
					
					if(p.hasPermission("xp.admin")) {
						sender.sendMessage(ChatColor.GREEN + "/xp setLevel [level] [player]" + ChatColor.GRAY + " - " + ChatColor.GOLD 
								+ " Sets the target players level to the given [level]");
						sender.sendMessage(ChatColor.GREEN + "/xp addLevel [level] [player]" + ChatColor.GRAY + " - " + ChatColor.GOLD 
								+ " Adds given [level] to current players level.");
						sender.sendMessage(ChatColor.GREEN + "/xp removeLevel [level] [player]" + ChatColor.GRAY + " - " + ChatColor.GOLD 
								+ " Removes given [level] from current players level.");
						sender.sendMessage(ChatColor.GREEN + "/xp setLimit [limit]" + ChatColor.GRAY + " - " + ChatColor.GOLD 
								+ " Sets the servers bank limit as the given [limit].");
						sender.sendMessage(ChatColor.GREEN + "/xp setLimit [limit]" + ChatColor.GRAY + " - " + ChatColor.GOLD 
								+ " Sets the servers bank limit as the given [limit].");
						sender.sendMessage(ChatColor.GREEN + "Page 1 of 2. Do '/Xp help 2' for page 2");
					}
					sender.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
				}
				
				if(args.length == 2) {
					int i = Integer.parseInt(args[1]);
					
					if(i == 2) {
						sender.sendMessage(ChatColor.GRAY + "------------------------XpBank-----------------------");
						sender.sendMessage(ChatColor.GREEN + "/xp setPlyMax [max] [player]" + ChatColor.GRAY + " - " + ChatColor.GOLD 
								+ "  Sets the [player] max storage as the given [max].");
						sender.sendMessage(ChatColor.GREEN + "/xp setPlyLimit [limit] [player]" + ChatColor.GRAY + " - " + ChatColor.GOLD 
								+ "  Sets the [player] bank limit as the given [limit].");
						sender.sendMessage(ChatColor.GREEN + "/xp setPlyAmt [amt] [player]" + ChatColor.GRAY + " - " + ChatColor.GOLD 
								+ "  Sets the [player] current bank amount as the given [amt].");
						sender.sendMessage(ChatColor.GREEN + "/xp resetPly [player]" + ChatColor.GRAY + " - " + ChatColor.GOLD 
								+ "  Resets all values of the [player] BankAmount,MaxStorage,BankLimit");
						sender.sendMessage(ChatColor.GREEN + "Page 2 of 2. Do '/Xp help' for page 1");
						sender.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
					}
				}
				
			}
			
			//Modify Commands Begin
			if(args[0].equalsIgnoreCase("clickAmt") && p.hasPermission("xp.quickstore")) {
				
				if(args.length < 2) {
					sender.sendMessage(ChatColor.RED + "Command help /xp clickAmt [Amount]");
					return false;
				}
				
				int i = (int) MiscMethods.getInt(args[1]);
				
				if(i <= 0) {
					sender.sendMessage(ChatColor.RED + "You have to select a positive numder.");
					return false;
				}
				
				xpBank.getVars().addPlayerList(p.getName(), i);				
				p.sendMessage(ChatColor.GREEN + "You have set your click amount to " + i);
				
				return true;
			}
			
			if(args[0].equalsIgnoreCase("calc")) {
				
				if(args.length < 2) {
					sender.sendMessage(ChatColor.RED + "Command help /xp calc [Level]");
					return false;
				}
				
				int i = (int) MiscMethods.getInt(args[1]);
				ExperienceManager em = xpBank.getVars().getExperienceManager(p.getName());
				
				if(i == 0) {
					sender.sendMessage(ChatColor.RED + "Command help /xp calc [Level]");
					return false;
				}
				
				if(em.getLevelForExp(em.getCurrentExp()) >= i) {
					sender.sendMessage(ChatColor.RED + "You're level " + em.getLevelForExp(em.getCurrentExp()) + ". " + i + 
							" is either less than or equal to your current level");
					return false;
				}
							
				p.sendMessage(ChatColor.GREEN + "You need a total of " + (em.getXpForLevel(i) - em.getCurrentExp()) + " to reach your target level.");
				
				return true;
			}
			
			if(args[0].equalsIgnoreCase("pay") && p.hasPermission("xp.pay")) {
				
				if(args.length <= 2) {
					sender.sendMessage(ChatColor.RED + "Command help /xp pay [Amount] [player]");
					return false;
				}

				ExperienceManager emP =  xpBank.getVars().getExperienceManager(p.getName());
				int i = (int) MiscMethods.getInt(args[1]);
				
				if(i == 0) {
					sender.sendMessage(ChatColor.RED + "Command help /xp pay [Amount] [Player]");
					return false;
				}
				
				String tp = args[2];
				
				if(emP.getCurrentExp() < i) {
					sender.sendMessage(ChatColor.RED + "You don't have enough xp!");
					return false;
				}
				
				Player targetPlayer = Bukkit.getPlayer(tp);
				
				if(targetPlayer == null || !(targetPlayer.isOnline()) ) {
					sender.sendMessage(ChatColor.RED + "Targetplayer is either not online or is incorrect.");
					return false;
				}
				
				ExperienceManager emTP =  xpBank.getVars().getExperienceManager(targetPlayer.getName());
				
				emP.setExp(emP.getCurrentExp() - i);
				sender.sendMessage(ChatColor.GREEN + "You have paid " + targetPlayer.getDisplayName() + " " + i + " xp");
				emTP.setExp(emTP.getCurrentExp()+ i);
				targetPlayer.sendMessage(ChatColor.AQUA + "You recieved " + i + " xp from " + sender.getName());
				
				return true;
			}
			//Modify Commands End
			
			//Admin Commands
			if(p.hasPermission("xp.admin")) {
				
				//Level Commands Begin
				if(args[0].equalsIgnoreCase("setLevel")) {
					
					if(args.length <= 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setLevel [level] [player]");
						return false;
					}
					
					int i = (int) MiscMethods.getInt(args[1]);
					
					if(i == 0) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setLevel [level] [player]");
						return false;
					}
					
					String tp = args[2];
					
					Player targetPlayer = Bukkit.getPlayer(tp);
					
					if(targetPlayer == null || !(targetPlayer.isOnline()) ) {
						sender.sendMessage(ChatColor.RED + "Targetplayer is either not online or is incorrect.");
						return false;
					}
					
					targetPlayer.setLevel(i);
					sender.sendMessage(ChatColor.GREEN + "You have set " + targetPlayer.getDisplayName() + " level to " + i);
					targetPlayer.sendMessage(ChatColor.AQUA + "Your level has been set to " + i + " By a Admin");
					
					return true;
				}
				
				if(args[0].equalsIgnoreCase("addLevel")) {
					
					if(args.length <= 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp addLevel [level] [player]");
						return false;
					}
					
					int i = (int) MiscMethods.getInt(args[1]);
					
					if(i == 0) {
						sender.sendMessage(ChatColor.RED + "Command help /xp addLevel [level] [player]");
						return false;
					}
					
					String tp = args[2];
					
					Player targetPlayer = Bukkit.getPlayer(tp);
					
					if(targetPlayer == null || !(targetPlayer.isOnline()) ) {
						sender.sendMessage(ChatColor.RED + "Targetplayer is either not online or is incorrect.");
						return false;
					}
					
					int newL = targetPlayer.getLevel() + i;
					
					targetPlayer.setLevel(newL);
					sender.sendMessage(ChatColor.GREEN + "You have added " + newL + " levels to " + targetPlayer.getDisplayName());
					targetPlayer.sendMessage(ChatColor.AQUA + "You have been given " + i + " levels by a Admin");
					
					return true;
				}
				
				if(args[0].equalsIgnoreCase("removeLevel")) {
					
					if(args.length <= 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp removeLevel [level] [player]");
						return false;
					}
					
					int i = (int) MiscMethods.getInt(args[1]);
					
					if(i == 0) {
						sender.sendMessage(ChatColor.RED + "Command help /xp removeLevel [level] [player]");
						return false;
					}
					
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
					
					int i = (int) MiscMethods.getInt(args[1]);
					
					if(i == 0) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setMax [amount]");
						return false;
					}
					
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
					
					int i = (int) MiscMethods.getInt(args[1]);
					
					if(i == 0) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setLimit [amount]");
						return false;
					}
					
					xpBank.getVars().serverBankLimit = i;
					xpBank.getVars().needSaving = true;
					
					sender.sendMessage(ChatColor.GREEN + "You have set server bank limit to " + i);
					
					return true;
				}
				
				if(args[0].equalsIgnoreCase("setClickAmt")) {
					
					if(args.length < 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setClickAmt [amount]");
						return false;
					}
					
					int i = (int) MiscMethods.getInt(args[1]);
					
					if(i == 0) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setClickAmt [amount]]");
						return false;
					}
					
					xpBank.getVars().serverClickAmt = i;
					xpBank.getVars().needSaving = true;
					
					sender.sendMessage(ChatColor.GREEN + "You have set server default click amount to " + i);
					
					return true;
				}
				//Server Handling Command End
				
				//Player Handling Command Begin
				if(args[0].equalsIgnoreCase("setPlyMax")) {
					
					if(args.length <= 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setPlyMax [amount] [player]");
						return false;
					}
					
					int i = (int) MiscMethods.getInt(args[1]);
					
					if(i == 0) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setPlyMax [amount] [player]");
						return false;
					}
					
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
					
					if(args.length <= 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setPlyLimit [amount] [player]");
						return false;
					}
					
					int i = (int) MiscMethods.getInt(args[1]);
					
					if(i == 0) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setPlyLimit [amount] [player]");
						return false;
					}
					
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
					
					if(args.length <= 2) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setPlyAmt [amount] [player]");
						return false;
					}
					
					int i = (int) MiscMethods.getInt(args[1]);
					
					if(i == 0) {
						sender.sendMessage(ChatColor.RED + "Command help /xp setPlyAmt [amount] [player]");
						return false;
					}
					
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
					
					if(args.length <= 2) {
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
