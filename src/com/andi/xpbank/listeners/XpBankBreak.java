package com.andi.xpbank.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.Attachable;

import com.andi.xpbank.XpBank;
import com.andi.xpbank.utils.ExperienceManager;
import com.andi.xpbank.utils.MiscMethods;

public class XpBankBreak implements Listener{
	
	private static BlockFace[] AllFaces = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP};
	
	public XpBank xpBank = XpBank.getInstance();
	
	public XpBankBreak() {
		
		xpBank.getServer().getPluginManager().registerEvents(this, xpBank);
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBankBreak(BlockBreakEvent ev) {
		
		if(ev.isCancelled())
			return;
		
		Player p = ev.getPlayer();
		String playerName = MiscMethods.nameTrim(p);
		Block b = getSign(ev.getBlock());
		BlockFace bf = getFace(ev.getBlock());
		Sign sign = null;
		
		if(b != null)
			sign = (Sign)b.getState();

		if(sign == null)
			return;
		
		if(!xpBank.isPlayerLoaded(p.getName()))
			xpBank.getXpLoad().loadPlayerData(p);
		
		ExperienceManager em = new ExperienceManager(p);
		
		if(sign.getLine(0).replaceAll(" ", "").equalsIgnoreCase("[XpBank]")) {
			
			String owner = sign.getLine(1);
			if(playerName.equals(owner) || p.hasPermission("xp.breakany")) {
				
				
				if(bf != null) {
					BlockFace signFace = ((Attachable)sign.getData()).getAttachedFace().getOppositeFace();
					if(!signFace.equals(bf))
						return;
				}
				
				double bankAmount = (xpBank.getVars().hasBankAmount(p.getName()) ? xpBank.getVars().getBankAmount(p.getName()) : 0);
				double newBankAmount = bankAmount - 1;
				boolean amountCheck = checkamount(bankAmount);
				
				if(amountCheck) {
					double d = Double.parseDouble(sign.getLine(2));
					int xp = (int) (d + em.getCurrentExp());
					
					em.setExp(xp);
					p.sendMessage(ChatColor.GREEN + "You broke an XpBank owned by " + owner);
					
					if(playerName.equals(owner)) {
						Player ownerP = Bukkit.getPlayer(playerName);
						
						if(ownerP != null) {
							xpBank.getVars().addBankAmount(ownerP.getName(), 0);
							xpBank.getVars().addPlayerLoaded(ownerP.getName());
						}
						
					}
					
					xpBank.getVars().needSaving = true;
					return;
				} else {
					double d = Double.parseDouble(sign.getLine(2));
					int xp = (int) (d + em.getCurrentExp());
					
					em.setExp(xp);
					p.sendMessage(ChatColor.GREEN + "You broke an XpBank owned by " + owner);
					
					if(playerName.equals(owner)) {
						Player ownerP = Bukkit.getPlayer(playerName);
						
						if(ownerP != null) {
							xpBank.getVars().addBankAmount(ownerP.getName(), newBankAmount);
							xpBank.getVars().addPlayerLoaded(ownerP.getName());
						}
						
					}
					
					xpBank.getVars().needSaving = true;
					return;
				}
				
			} else {
				ev.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You may not break a XpBank owned by " + sign.getLine(1));
				return;
			}
			
			
		}
		
	}

	private Block getSign(Block block) {
		if(block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST) {
			return block;
		} else {
			for(BlockFace faces : AllFaces) {
				Block checkB = block.getRelative(faces);
				if(checkB.getType() == Material.WALL_SIGN || checkB.getType() == Material.SIGN || checkB.getType() == Material.SIGN_POST) {
					return checkB;
				}
			}
			return null;
		}
	}
	
	private BlockFace getFace(Block block) {
		if(block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST) {
			return null;
		} else {
			for(BlockFace face : AllFaces) {
				Block checkB = block.getRelative(face);
				if(checkB.getType() == Material.WALL_SIGN || checkB.getType() == Material.SIGN || checkB.getType() == Material.SIGN_POST) {
					return face;
				}
			}
			return null;
		}
	}
	
	private boolean checkamount(double amount) {
		if(amount <= 0.0)
			return true;
		
		return false;
	}

}
