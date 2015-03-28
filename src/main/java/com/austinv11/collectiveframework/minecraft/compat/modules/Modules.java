package com.austinv11.collectiveframework.minecraft.compat.modules;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLStateEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * A registry for {@link IModule}
 */
public class Modules {
	
	private static List<IModule> modules = new ArrayList<IModule>();
	
	/**
	 * Registers a module
	 * @param module The module to register
	 */
	public static void registerModCompat(IModule module) {
		modules.add(module);
	}
	
	private static boolean canModuleLoad(IModule module) {
		for (String s : module.modsCompatible())
			if (Loader.isModLoaded(s))
				return true;
		return false;
	}
	
	private static void cleanModules() {
		List<IModule> uncleanedModules = new ArrayList<IModule>(modules);
		modules.clear();
		for (IModule m : uncleanedModules)
			if (canModuleLoad(m))
				modules.add(m);
	}
	
	/**
	 * Intended for internal use only
	 */
	public static void init() {
		cleanModules();
	}
	
	/**
	 * Propagates an FMLStateEvent to all modules
	 * @param event The event
	 */
	public static void propagate(FMLStateEvent event) {
		for (IModule m : modules)
			if (event instanceof FMLPreInitializationEvent)
				m.preInit((FMLPreInitializationEvent) event);
			else if (event instanceof FMLInitializationEvent)
				m.init((FMLInitializationEvent) event);
			else
				m.postInit((FMLPostInitializationEvent) event);
	}
}
