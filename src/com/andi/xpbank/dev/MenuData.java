package com.andi.xpbank.dev;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

//Credit to H2NCH2COOH base code made by him. With some slight changes by myself.
public class MenuData {

	private Player player;
	private String title;
	private int size;
	private List<MenuOptions> menuoptions;
	private Inventory inventory;
	private boolean canClose;
	private HashMap<ItemStack, Boolean> selectable = new HashMap<ItemStack, Boolean>();
	
	public MenuData(Player player, String title, int size, List<MenuOptions> menuoptions, boolean canclose) {
		this.player = player;
		this.title = title;
		this.size = size;
		this.menuoptions = menuoptions;
		this.canClose = canclose;

		this.inventory = Bukkit.getServer().createInventory(null, this.size, this.title);
		
		if(menuoptions == null) {
			return;
		}
		
		for(MenuOptions o : this.menuoptions) {
			ItemStack item = new ItemStack(o.getMaterial(), 1, o.getData() == 1000 ? 0 : o.getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(o.getName());
			if(o.getLore() != null) {
				List<String> lore = new LinkedList<String>();
				for(String s : o.getLore()) {
					lore.add(s);
				}
				meta.setLore(lore);
			}
			item.setItemMeta(meta);
			this.selectable.put(item, o.isSelectable());
			this.inventory.setItem(o.getPosX() + 9 * o.getPosY(), item);
		}
	}
	
	public InventoryView show() {
		return this.player.openInventory(this.inventory);
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public List<MenuOptions> getMenuOptions() {
		return this.menuoptions;
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public boolean isSelectable(ItemStack item) {
		if((item == null) || (!this.selectable.containsKey(item))) {
			return false;
		}
		return this.selectable.get(item);
	}
	
	public boolean canClose() {
		return this.canClose;
	}
}
