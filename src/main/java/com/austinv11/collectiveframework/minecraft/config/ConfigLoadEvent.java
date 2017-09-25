package com.austinv11.collectiveframework.minecraft.config;


import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * This is called when CollectiveFramework attempts to load a config.
 * When a config is synced, it is called in two parts, PRE: Before the reload and POST: After the reload.
 * The Init event is posted BEFORE a config is read (if it currently exists)
 * NOTE: This is called on the MinecraftForge event bus
 */
public class ConfigLoadEvent extends Event {
	
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
	public static class Pre extends ConfigLoadEvent {
		
	}
	
	public static class Post extends ConfigLoadEvent {
		
	}
	
	public static class Init extends ConfigLoadEvent {
		
	}
}
