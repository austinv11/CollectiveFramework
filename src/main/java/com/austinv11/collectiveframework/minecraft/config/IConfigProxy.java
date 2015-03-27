package com.austinv11.collectiveframework.minecraft.config;

/**
 * Use this interface to create custom serializers and deserializers for configs
 */
public interface IConfigProxy {
	
	/**
	 * Checks if the object passed can be serialized by this proxy
	 * @param o The object to check
	 * @return True if this proxy can handle the object
	 */
	public boolean canSerializeObject(Object o);
	
	/**
	 * Checks if the key passed represents a config option type that can be deserialized by this
	 * @param key The key of the config type
	 * @return True if this proxy is capable of deserializing
	 */
	public boolean isKeyUsable(String key);
	
	/**
	 * Returns the key representing the object
	 * @param o The object
	 * @return The key
	 */
	public String getKey(Object o);
	
	/**
	 * Serializes an object into a (single line) string
	 * @param o The object to serialize
	 * @return The serialized object
	 * @throws ConfigException
	 */
	public String serialize(Object o) throws ConfigException;
	
	/**
	 * Deserializes an object from a String
	 * @param key The key representing the object type
	 * @param s The string
	 * @return The deserialized object
	 * @throws ConfigException
	 */
	public Object deserialize(String key, String s) throws ConfigException;
}
