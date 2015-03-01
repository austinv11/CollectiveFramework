package com.austinv11.collectiveframework.utils;

import com.austinv11.collectiveframework.reference.Config;
import com.austinv11.collectiveframework.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {
	
	public static Configuration config;
	
	public static void init(File configFile){
		if (config == null) {
			config = new Configuration(configFile);
		}
		loadConfiguration();
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration(){
		try{//Load & read properties
			config.load();
			Config.translateItems = config.get("translation", "translateItems", false, "Setting this to true will attempt to translate item names which are not localized").getBoolean(false);
			Config.translateChat = config.get("translate", "translateChat", false, "Setting this to true will attempt to translate chat messages").getBoolean(false);
		}catch (Exception e){//Log exception
			Logger.warn("Config exception!");
			Logger.warn(e.getStackTrace());
		}finally {//Save
			if (config.hasChanged()) {
				config.save();
			}
		}
	}
}
