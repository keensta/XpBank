package com.andi.xpbank.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.andi.xpbank.XpBank;

public class Config {

	public Config() {
		
	}
	
	public void checkConfig(String name) {
		final File dataDirectory = XpBank.getInstance().getDataFolder();
		if (dataDirectory.exists() == false)
			dataDirectory.mkdirs();

		final File f = new File(XpBank.getInstance().getDataFolder(), name+".yml");
		if (f.canRead())
			return;

		XpBank.getInstance().log.info("[XpBank] Writing default " + name+".yml");
		final InputStream in = XpBank.getInstance().getResource(name+".yml");

		if (in == null) {
			XpBank.getInstance().log.severe("[XpBank] Could not find " + name+".yml" + " resource");
			return;
		} else {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
				final byte[] buf = new byte[512];
				int len;
				while ((len = in.read(buf)) > 0) {
					fos.write(buf, 0, len);
				}
			} catch (final IOException iox) {
				XpBank.getInstance().log.severe("[XpBank] Could not export " + name+".yml");
				return;
			} finally {
				if (fos != null)
					try {
						fos.close();
					} catch (final IOException iox) {
					}
				if (in != null)
					try {
						in.close();
					} catch (final IOException iox) {
					}
			}
			return;
		}
	}
	
	public FileConfiguration loadConfig(String fileName) {
		return YamlConfiguration.loadConfiguration(new File(XpBank.getInstance().getDataFolder(), fileName+".yml"));
	}

	public void saveConfig(FileConfiguration cfg, String fileName) {
		try {
			cfg.save(new File(XpBank.getInstance().getDataFolder(), fileName+".yml"));
		} catch (IOException e) {
			Logger.getLogger("Minecraft").severe("[XpBank] Failed to save " + fileName +".yml!");
			e.printStackTrace();
		}
	}

}
