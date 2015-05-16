package com.austinv11.collectiveframework.minecraft.config;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * This is called when CollectiveFramework attempts to reload a config
 * This event is called in two parts, PRE: Before the reload and POST: After the reload
 * NOTE: This is called on the MinecraftForge event bus
 */
public class ConfigReloadEvent extends Event {
	
	/**
	 * The name of the config file
	 */
	public String configName;
	/**
	 * The contents of the config
	 */
	public String config;
	/**
	 * Whether the event is being called to revert server changes
	 */
	public boolean isRevert;
	
	@Cancelable
	public static class Pre extends ConfigReloadEvent {
		
	}
	
	public static class Post extends ConfigReloadEvent {
		
	}
}
