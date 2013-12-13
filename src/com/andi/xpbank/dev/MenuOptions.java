package com.andi.xpbank.dev;

import org.bukkit.Material;

//Credit to H2NCH2COOH base code made by him. With some slight changes by myself.
public class MenuOptions {
	
	private String name;
	private String[] lore;
	private boolean selectable;
	private Material material;
	private short data;
	private int posX;
	private int posY;
	
	public MenuOptions(String name, String[] lore, boolean selectable, Material material, short data, int posX, int posY) {
		this.name = name;
		this.lore = lore;
		this.selectable = selectable;
		this.material = material;
		this.data = data;
		this.posX = (posX < 9 ? posX : 8);
		this.posY = (posY < 6 ? posY : 5);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String[] getLore() {
		return this.lore;
	}
	
	public boolean isSelectable() {
		return this.selectable;
	}
	
	public Material getMaterial() {
		return this.material;
	}
	
	public short getData() {
		return this.data;
	}
	
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}

}
