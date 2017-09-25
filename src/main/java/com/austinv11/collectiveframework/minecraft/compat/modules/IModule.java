package com.austinv11.collectiveframework.minecraft.compat.modules;


import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * An interface for adding explicit mod compatibility
 */
public interface IModule {
	
	/**
	 * Called on the pre-init event
	 * @param event The event
	 */
	public void preInit(FMLPreInitializationEvent event);
	
	/**
	 * Called on the init event
	 * @param event The event
	 */
	public void init(FMLInitializationEvent event);
	
	/**
	 * Called on the post-init event
	 * @param event The event
	 */
	public void postInit(FMLPostInitializationEvent event);
	
	/**
	 * Gets the mods required for this module to activate
	 * @return The modids required 
	 * (not strict, so {@code {"modA", "modB}} will cause the module to activate even if only modA is loaded)
	 */
	public String[] modsCompatible();
}
