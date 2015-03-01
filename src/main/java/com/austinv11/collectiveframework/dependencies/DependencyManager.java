package com.austinv11.collectiveframework.dependencies;

import com.austinv11.collectiveframework.dependencies.download.IDownloadProvider;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModClassLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for managing dependencies
 */
public class DependencyManager {
	
	private static final List<IDownloadProvider> providers = new ArrayList<IDownloadProvider>();
	
	/**
	 * Register a download provider, note: the earlier the registration, the higher priority it has
	 * @param provider The download provider
	 */
	public static void registerDownloadProvider(IDownloadProvider provider) {
		providers.add(provider);
	}
	
	/**
	 * Attempts to load the specified library (must be a proper java archive!)
	 * @param file The file representing the library
	 * @throws MalformedURLException
	 */
	public static void loadLibrary(File file) throws MalformedURLException {
		((ModClassLoader)Loader.instance().getModClassLoader()).addFile(file);
	}
}
