package com.andi.xpbank.dev;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryView;

import com.andi.xpbank.XpBank;


public class MenuHandler implements Listener{
		
	public XpBank plugin = XpBank.getInstance();
	public HashMap<InventoryView, MenuData> currentMenus = new HashMap<InventoryView, MenuData>();
	
	public MenuHandler(XpBank plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void createMenu(Player player, String title, int size, List<MenuOptions> menuoptions, boolean canClose) {
		MenuData m = new MenuData(player, title, size, menuoptions, canClose);
		currentMenus.put(m.show(), m);
	}
	
	public MenuOptions addOption(String name, boolean selectable, Material material, int posX, int posY) {
		return new MenuOptions(name, null, selectable, material, (short) 1000, posX, posY);
	}
	
	public MenuOptions addOption(String name,String[] lore, boolean selectable, Material material, int posX, int posY) {
		return new MenuOptions(name, lore, selectable, material, (short) 1000, posX, posY);
	}
	
	public MenuOptions addOption(String name,String[] lore, boolean selectable, Material material, Short data, int posX, int posY) {
		return new MenuOptions(name, lore, selectable, material, data, posX, posY);
	}
	
	public MenuOptions addOption(String name, boolean selectable, Material material, Short data, int posX, int posY) {
		return new MenuOptions(name, null, selectable, material, data, posX, posY);
	}
	
	@EventHandler
	public void inventoryClickEvent(InventoryClickEvent ev) {
		
		if(ev.getCurrentItem() == null)
			return;
		
		if(currentMenus.containsKey(ev.getView())) {
			ev.setCancelled(true);
			
			MenuData m = (MenuData) currentMenus.get(ev.getView());
			if(m.isSelectable(ev.getCurrentItem())) {
				MenuInteractEvent menuIEvent = new  MenuInteractEvent((Player)ev.getWhoClicked(), MenuInteractEventResult.SELECTED, 
						ev.getCurrentItem().getItemMeta().getDisplayName(), ev.getCurrentItem(), ev.getInventory().getTitle(), ev.getInventory(), ev.getView());
				this.plugin.getServer().getPluginManager().callEvent(menuIEvent);
				//currentMenus.remove(ev.getView());
			
				/*List<HumanEntity> viewers = ev.getViewers();
				
				for(HumanEntity e : viewers) {
					e.closeInventory();
				}
				*/
				
			}
		}
		
	}
	
	@EventHandler
	public void inventoryCloseEvent(InventoryCloseEvent ev) {
		if(currentMenus.containsKey(ev.getView())) {
			final MenuData m = (MenuData)currentMenus.get(ev.getView());
			
			if(!(m.canClose())) {
				currentMenus.remove(m.show());
				
				this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, 
						new Runnable() {
					
					public void run() {
						createMenu(m.getPlayer(), m.getTitle(), m.getSize(), m.getMenuOptions(), m.canClose());
					}
					
				}
						, 20);
				
			} else {
				MenuInteractEvent menuIevent = new MenuInteractEvent((Player)ev.getPlayer(), MenuInteractEventResult.CLOSED, 
						null, null, ev.getInventory().getTitle(), ev.getInventory(), ev.getView());
				this.plugin.getServer().getPluginManager().callEvent(menuIevent);
				currentMenus.remove(ev.getView());
			}
		}
	}
	
	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent ev) {
		boolean f = false;
		for(InventoryView key : currentMenus.keySet()) {
			MenuData m = (MenuData)currentMenus.get(key);
			if(m.getPlayer() == ev.getPlayer()) {
				currentMenus.remove(key);
				f = true;
			}
		}
		
		if(f) {
			MenuInteractEvent menuIevent = new MenuInteractEvent(ev.getPlayer(), MenuInteractEventResult.QUITCLOSED, 
					null, null, null, null, null);
			plugin.getServer().getPluginManager().callEvent(menuIevent);
		}
	}

}
