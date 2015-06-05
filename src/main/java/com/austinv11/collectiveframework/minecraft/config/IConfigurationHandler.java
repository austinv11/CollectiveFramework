package com.austinv11.collectiveframework.minecraft.config;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * An interface representing a custom config handler, it will get called whenever a config is modified
 */
public interface IConfigurationHandler {
	
	/**
	 * Called when a value is set/changed for a config, same as {@link #setValue(String, String, Object, Object, boolean)} but saveToFile is assumed true
	 * @param configValue The value being modified
	 * @param category The category the value is in
	 * @param value The new value
	 * @param config The config object
	 */
	public void setValue(String configValue, String category, Object value, Object config);
	
	/**
	 * Called when a value is set/changed for a config
	 * @param configValue The value being modified
	 * @param category The category the value is in
	 * @param value The new value
	 * @param config The config object
	 * @param saveToFile Whether to save the new value to the file
	 */
	public void setValue(String configValue, String category, Object value, Object config, boolean saveToFile);
	
	/**
	 * Called when a value is requested
	 * @param configValue The value being modified
	 * @param category The category the value is in
	 * @param config The config object
	 * @return The value
	 */
	public Object getValue(String configValue, String category, Object config);
	
	/**
	 * Called to load/create a config file
	 * @param fileName The file name for the config
	 * @param config The config object
	 * @param hint A map full of field data (feel free to ignore this);
	 *             Outer map has the key = the category
	 *             Inner map has the key = the field name and the value = the actual field 
	 */
	public void loadFile(String fileName, Object config, Map<String, Map<String, Field>> hint);
	
	/**
	 * Called to load/create a config from a string
	 * @param string The string containing all the data
	 * @param config The config object
	 * @param hint A map full of field data (feel free to ignore this);
	 *             Outer map has the key = the category
	 *             Inner map has the key = the field name and the value = the actual field 
	 */
	public void loadFromString(String string, Object config, Map<String, Map<String, Field>> hint);
	
	/**
	 * Called to get the config file
	 * @param fileName The file name for the config
	 * @param config The config object
	 * @return The file representing the config
	 */
	public File getConfigFile(String fileName, Object config);
	
	/**
	 * Returns whether the config contains the specified value
	 * @param configValue The value
	 * @param category The category the value is in
	 * @return True if contained
	 */
	public boolean hasValue(String configValue, String category);
	
	/**
	 * Converts the config into a string
	 * @return The config
	 */
	public String convertToString(Object config);
}
