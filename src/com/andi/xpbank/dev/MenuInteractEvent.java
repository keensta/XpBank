package com.andi.xpbank.dev;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

//Credit to H2NCH2COOH for MenuEvent Idea
public class MenuInteractEvent extends PlayerEvent{
	
	public static HandlerList handlers = new HandlerList();
	public MenuInteractEventResult result;
	public String selected;
	public ItemStack item;
	public String title;
	public Inventory inv;
	public InventoryView view;
	public boolean close;
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public MenuInteractEvent(Player player, MenuInteractEventResult result, String selected, ItemStack item, String title, Inventory inv, InventoryView view) {
		super(player);
		
		this.result = result;
		this.selected = selected;
		this.item = item;
		this.title = title;
		this.inv = inv;
		this.view = view;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public MenuInteractEventResult getResult() {
		return this.result;
	}
	
	public String getSelectedItemName() {
		return this.selected;
	}
	
	public ItemStack getSelectedItem(){
		return this.item;
	}
	
	public Inventory getInventory() {
		return this.inv;
	}
	
	public String getMenuTitle() {
		return this.title;
	}
	
	public InventoryView getInventoryView() {
		return this.view;
	}
	
	public void closeMenu(boolean close) {
		this.close = close;
	}
	
	public boolean getClose() {
		return close;
	}

}
