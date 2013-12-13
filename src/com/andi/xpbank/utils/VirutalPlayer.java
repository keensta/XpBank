package com.andi.xpbank.utils;

public class VirutalPlayer {
	/*
	Player keepInformed; // / who to send the messages to
	boolean online = true;
	double health = 20;
	boolean isop = true;
	static boolean enableMessages = true;
	boolean showMessages = true;
	boolean showTeleports = true;
	GameMode gamemode = GameMode.SURVIVAL;

	Location loc;

	public VirutalPlayer(CraftServer cserver, MinecraftServer mcserver, World world, GameProfile gp, PlayerInteractManager iiw) {
		super(cserver, new EntityPlayer(mcserver, (WorldServer) world, gp, iiw));
		this.loc = this.getLocation();
	}

	public VirutalPlayer(CraftServer cserver, EntityPlayer ep) {
		super(cserver, ep);
		this.loc = this.getLocation();
	}

	@Override
	public void updateInventory() {
		// / Do nothing
	}

	@Override
	public void setGameMode(GameMode gamemode) {
		try {
			super.setGameMode(gamemode);
		} catch (Exception e) {
		}
		this.gamemode = gamemode;
	}

	@Override
	public GameMode getGameMode() {
		return gamemode;
	}

	@Override
	public void setHealth(double h) {
		if (h < 0)
			h = 0;
		this.health = h;
		try {
			super.setHealth(h);
		} catch (Exception e) {
		}
		try {
			this.getHandle().setHealth((float) h);
		} catch (Exception e) {
		}
	}

	@Override
	public boolean isDead() {
		return super.isDead() || health <= 0;
	}

	@Override
	public void sendMessage(String s) {
		
	}

	public void moveTo(Location loc) {
		entity.move(loc.getX(), loc.getY(), loc.getZ());
	}

	public boolean teleport(Location l, boolean respawn) {
		return false;
	}

	@Override
	public boolean teleport(Location l) {
		return false;
	}

	public void respawn(Location loc) {
		this.health = 20;
		boolean changedWorlds = !this.loc.getWorld().getName().equals(loc.getWorld().getName());
		CraftServer cserver = (CraftServer) Bukkit.getServer();
		PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(this, loc, false);
		cserver.getPluginManager().callEvent(respawnEvent);
		if (changedWorlds) {
			PlayerChangedWorldEvent pcwe = new PlayerChangedWorldEvent(this, loc.getWorld());
			cserver.getPluginManager().callEvent(pcwe);
		}
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean b) {
		online = b;
	}

	@Override
	public boolean isOp() {
		return isop;
	}

	@Override
	public void setOp(boolean b) {
		isop = b;
	}

	@Override
	public String toString() {
		String world = "&5" + this.loc.getWorld().getName() + ",";
		return getName() + "&e h=&2" + getHealth() + "&e o=&5" + isOnline() + "&e d=&7" + isDead() + "&e loc=&4" + world + "&4" + " gm=" + getGameMode();
	}

	@Override
	public void setScoreboard(Scoreboard scoreboard) {
		Object s = null;
		if (scoreboard != null) {
			if (Bukkit.getScoreboardManager().getMainScoreboard() != null && scoreboard.equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
				s = "BukkitMainScoreboard";
			} else if (scoreboard.getObjective(DisplaySlot.SIDEBAR) != null) {
				s = scoreboard.getObjective(DisplaySlot.SIDEBAR).getName();
			} else if (scoreboard.getObjective(DisplaySlot.PLAYER_LIST) != null) {
				s = scoreboard.getObjective(DisplaySlot.PLAYER_LIST).getName();
			}
		}
		
	}

	public void setLocation(Location l) {
		loc = l;
	}

	public Player getInformed() {
		return keepInformed;
	}

	public static void setGlobalMessages(boolean enable) {
		VirutalPlayer.enableMessages = enable;
	}*/
}