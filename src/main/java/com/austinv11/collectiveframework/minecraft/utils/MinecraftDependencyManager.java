package com.austinv11.collectiveframework.minecraft.utils;

import com.austinv11.collectiveframework.dependencies.DependencyManager;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModClassLoader;

import java.io.File;
import java.net.MalformedURLException;

/**
 * A class for managing dependencies in Minecraft
 */
public class MinecraftDependencyManager extends DependencyManager {
	
	/**
	 * Attempts to load the specified library (must be a proper java archive!)
	 * @param file The file representing the library
	 * @throws MalformedURLException
	 */
	public static void loadLibrary(File file) throws MalformedURLException {
		((ModClassLoader) Loader.instance().getModClassLoader()).addFile(file);
	}
}
