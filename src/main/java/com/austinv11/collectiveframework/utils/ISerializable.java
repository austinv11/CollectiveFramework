package com.austinv11.collectiveframework.utils;

/**
 * Implement this interface if you want your object to be serializable
 */
public interface ISerializable {
	
	/**
	 * Creates an object from a string
	 * @param serializedString The String containing the object
	 * @return The deserialized object
	 */
	public Object deserialize(String serializedString);
	
	/**
	 * Creates a string capable of being deserialized from this object
	 * @return The serialized string
	 */
	public String serialize();
}
