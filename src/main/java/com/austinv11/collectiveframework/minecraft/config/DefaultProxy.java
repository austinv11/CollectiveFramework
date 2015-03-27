package com.austinv11.collectiveframework.minecraft.config;

import com.austinv11.collectiveframework.utils.ArrayUtils;
import com.austinv11.collectiveframework.utils.ReflectionUtils;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * A default config proxy for primitive (also includes strings) and array types
 */
public class DefaultProxy implements IConfigProxy {
	
	@Override
	public boolean canSerializeObject(Object o) {
		return o.getClass().isArray() || ReflectionUtils.isPrimitiveObject(o) || o instanceof String;
	}
	
	@Override
	public boolean isKeyUsable(String key) {
		return key.contains("Array") || ReflectionUtils.isNamePrimitiveOrPrimitiveObject(key) || key.contains("String");
	}
	
	@Override
	public String getKey(Object o) {
		if (o.getClass().isArray()) {
			int length = Array.getLength(o);
			String[] keys = new String[length];
			for (int i = 0; i < length; i++)
				keys[i] = ConfigRegistry.getKey(Array.get(o, i));
			String[] noRepeats = ArrayUtils.removeRepeats(keys);
			String key = "";
			for (String s : noRepeats)
				key = key+s+" ";
			key = key+"Array";
			return key;
		} else {
			return o.getClass().getSimpleName();
		}
	}
	
	@Override
	public String serialize(Object o) throws ConfigException {
		if (!canSerializeObject(o))
			throw new ConfigException("Cannot serialize object "+o.toString());
		if (o.getClass().isArray()) {
			int length = Array.getLength(o);
			String[] serialized = new String[length];
			for (int i = 0; i < length; i++)
				serialized[i] = ConfigRegistry.serialize(Array.get(o, i));
			String string = "[";
			for (String s : serialized)
				string = string+","+s;
			string = string.replaceFirst(",", "")+"]";
			return string;
		} else {
			return o.toString();
		}
	}
	
	@Override
	public Object deserialize(String key, String s) throws ConfigException {
		if (!isKeyUsable(key))
			throw new ConfigException("String "+s+" is invalid for this proxy");
		if (key.contains("Array")) {
			s = s.replaceFirst("\\[", "");
			s = s.substring(0, s.lastIndexOf("]"));
			String[] toSerialize = s.split(",");
			String[] trimmedToSerialize = ArrayUtils.trimAll(toSerialize);
			Object[] deserialized = new Object[trimmedToSerialize.length];
			for (int i = 0; i < trimmedToSerialize.length; i++)
				deserialized[i] = ConfigRegistry.deserialize(key.replace(" Array", ""), trimmedToSerialize[i]);
			if (key.contains("Integer"))
				return ArrayUtils.convertObjectArrayToInt(deserialized);
			if (key.contains("Long"))
				return ArrayUtils.convertObjectArrayToLong(deserialized);
			if (key.contains("Double"))
				return ArrayUtils.convertObjectArrayToDouble(deserialized);
			if (key.contains("Float"))
				return ArrayUtils.convertObjectArrayToFloat(deserialized);
			if (key.contains("Boolean"))
				return ArrayUtils.convertObjectArrayToBoolean(deserialized);
			if (key.contains("Character"))
				return ArrayUtils.convertObjectArrayToChar(deserialized);
			if (key.contains("Byte"))
				return ArrayUtils.convertObjectArrayToByte(deserialized);
			if (key.contains("Void"))
				return null;
			if (key.contains("Short"))
				return ArrayUtils.convertObjectArrayToShort(deserialized);
			if (key.contains("String"))
				return Arrays.copyOf(deserialized, deserialized.length, String[].class);
			return deserialized;
		} else {
			if (key.equalsIgnoreCase("integer"))
				return Integer.parseInt(s);
			if (key.equalsIgnoreCase("long"))
				return Long.parseLong(s);
			if (key.equalsIgnoreCase("double"))
				return Double.parseDouble(s);
			if (key.equalsIgnoreCase("float"))
				return Float.parseFloat(s);
			if (key.equalsIgnoreCase("boolean"))
				return Boolean.parseBoolean(s);
			if (key.equalsIgnoreCase("character"))
				return s.toCharArray()[0];
			if (key.equalsIgnoreCase("byte"))
				return Byte.parseByte(s);
			if (key.equalsIgnoreCase("void"))
				return null;
			if (key.equalsIgnoreCase("short"))
				return Short.parseShort(s);
			else
				return s;
		}
	}
}
