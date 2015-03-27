package com.austinv11.collectiveframework.utils;

import com.austinv11.collectiveframework.utils.math.MathUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains a few useful utilities to simplify reflection
 */
public class ReflectionUtils {
	
	/**
	 * Gets a list of declared methods with the given annotation
	 * @param annotation The annotation to search for
	 * @param clazz The class to search in
	 * @return The methods with the annotation
	 */
	public static List<Method> getDeclaredMethodsWithAnnotation(Class<? extends Annotation> annotation, Class clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		List<Method> annotated = new ArrayList<Method>();
		for (Method m : methods)
			if (m.isAnnotationPresent(annotation))
				annotated.add(m);
		return annotated;
	}
	
	/**
	 * Gets a list of declared fields with the given annotation
	 * @param annotation The annotation to search for
	 * @param clazz The class to search in
	 * @return The fields with the annotation
	 */
	public static List<Field> getDeclaredFieldsWithAnnotation(Class<? extends Annotation> annotation, Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		List<Field> annotated = new ArrayList<Field>();
		for (Field f : fields)
			if (f.isAnnotationPresent(annotation))
				annotated.add(f);
		return annotated;
	}
	
	/**
	 * Gets a list of methods with the given annotation
	 * @param annotation The annotation to search for
	 * @param clazz The class to search in
	 * @return The methods with the annotation
	 */
	public static List<Method> getMethodsWithAnnotation(Class<? extends Annotation> annotation, Class clazz) {
		Method[] methods = clazz.getMethods();
		List<Method> annotated = new ArrayList<Method>();
		for (Method m : methods)
			if (m.isAnnotationPresent(annotation))
				annotated.add(m);
		return annotated;
	}
	
	/**
	 * Gets a list of fields with the given annotation
	 * @param annotation The annotation to search for
	 * @param clazz The class to search in
	 * @return The fields with the annotation
	 */
	public static List<Field> getFieldsWithAnnotation(Class<? extends Annotation> annotation, Class clazz) {
		Field[] fields = clazz.getFields();
		List<Field> annotated = new ArrayList<Field>();
		for (Field f : fields)
			if (f.isAnnotationPresent(annotation))
				annotated.add(f);
		return annotated;
	}
	
	/**
	 * Checks if the given method has the required parameters
	 * @param method The method
	 * @param params The parameters (ORDER MATTERS!)
	 * @return True of the parameters match, else false
	 */
	public static boolean paramsMatch(Method method, Class<?>... params) {
		Class<?>[] parameters = method.getParameterTypes();
		if (parameters.length == params.length) {
			for (int i = 0; i < parameters.length; i++)
				if (!parameters[i].getName().equals(params[i].getName()))
					return false;
			return true;
		}
		return false;
	}
	
	/**
	 * Attempts to find either a declared or normal field (in that order)
	 * @param fieldName The field to get
	 * @param clazz The class to search
	 * @return The field, or null if it wasn't found
	 */
	public static Field getDeclaredOrNormalField(String fieldName, Class clazz) {
		for (Field f : clazz.getDeclaredFields())
			if (f.getName().equals(fieldName))
				return f;
		for (Field f1 : clazz.getFields())
			if (f1.getName().equals(fieldName))
				return f1;
		return null;
	}
	
	/**
	 * Gets the jvm signature for an object
	 * @param o The object
	 * @return The signature
	 */
	public static String getSignatureFromObject(Object o) {
		if (o.getClass().isArray())
			return o.getClass().getName().replace(".", "/");
		return getSignatureFromObject(o, false);
	}
	
	private static String getSignatureFromObject(Object o, boolean isRecursive) {
		if (o instanceof Integer)
			return "I";
		if (o instanceof Long)
			return "J";
		if (o instanceof Double)
			return "D";
		if (o instanceof Float)
			return "F";
		if (o instanceof Boolean)
			return "Z";
		if (!(o instanceof String) && o instanceof Character)
			return "C";
		if (o instanceof Byte)
			return "B";
		if (o instanceof Void)
			return "V";
		if (o instanceof Short)
			return "S";
		return "L"+o.getClass().getName().replace(".", "/")+";";
	}
	
	/**
	 * Gets the class for a signature (only handles one signature at a time)
	 * @param signature The signature
	 * @return The class representing the signature inputted
	 * @throws ClassNotFoundException
	 */
	public static Class getTypeFromSignature(String signature) throws ClassNotFoundException {
		if (signature.charAt(0) == '[') {
			signature = signature.replace("[", "");
			if (signature.equals("I"))
				return int[].class;
			if (signature.equals("J"))
				return long[].class;
			if (signature.equals("D"))
				return double[].class;
			if (signature.equals("F"))
				return float[].class;
			if (signature.equals("Z"))
				return boolean[].class;
			if (signature.equals("C"))
				return char[].class;
			if (signature.equals("B"))
				return byte[].class;
			if (signature.equals("S"))
				return short[].class;
			if (signature.contains("L"))
				return Array.newInstance(Class.forName(signature.substring(1, signature.length()).replace("/", ".")), 1).getClass();
		} else {
			if (signature.equals("I"))
				return int.class;
			if (signature.equals("J"))
				return long.class;
			if (signature.equals("D"))
				return double.class;
			if (signature.equals("F"))
				return float.class;
			if (signature.equals("Z"))
				return boolean.class;
			if (signature.equals("C"))
				return char.class;
			if (signature.equals("B"))
				return byte.class;
			if (signature.equals("V"))
				return void.class;
			if (signature.equals("S"))
				return short.class;
			if (signature.contains("L"))
				return Class.forName(signature.substring(1, signature.length()-1).replace("/", "."));
		}
		return null;
	} 
	
	/**
	 * Gets classes for a given name, including primitives
	 * @param name The name
	 * @return The class
	 * @throws ClassNotFoundException
	 */
	public static Class getPrimitiveSafeClassForName(String name) throws ClassNotFoundException {
		if (name.equalsIgnoreCase("int"))
			return Integer.TYPE;
		if (name.equalsIgnoreCase("long"))
			return Long.TYPE;
		if (name.equalsIgnoreCase("double"))
			return Double.TYPE;
		if (name.equalsIgnoreCase("float"))
			return Float.TYPE;
		if (name.equalsIgnoreCase("boolean"))
			return Boolean.TYPE;
		if (name.equalsIgnoreCase("char"))
			return Character.TYPE;
		if (name.equalsIgnoreCase("byte"))
			return Byte.TYPE;
		if (name.equalsIgnoreCase("void"))
			return Void.TYPE;
		if (name.equalsIgnoreCase("short"))
			return Short.TYPE;
		return Class.forName(name);
	}
	
	/**
	 * This method checks if the specified class name is of a primitive
	 * @param name The class name
	 * @return True if the name is of a primitive data type
	 */
	public static boolean isNamePrimitive(String name) {
		return name.equalsIgnoreCase("int") || name.equalsIgnoreCase("long") || name.equalsIgnoreCase("double") ||
				name.equalsIgnoreCase("float") || name.equalsIgnoreCase("boolean") || name.equalsIgnoreCase("char") ||
				name.equalsIgnoreCase("byte") || name.equalsIgnoreCase("void") || name.equalsIgnoreCase("short") ||
				name.equalsIgnoreCase("integer") || name.equalsIgnoreCase("character");
	}
	
	/**
	 * This method checks if the specified class name is of a primitive, includes primitive object names
	 * @param name The class name
	 * @return True if the name is of a primitive data type
	 */
	public static boolean isNamePrimitiveOrPrimitiveObject(String name) {
		return name.equalsIgnoreCase("int") || name.equalsIgnoreCase("long") || name.equalsIgnoreCase("double") ||
				name.equalsIgnoreCase("float") || name.equalsIgnoreCase("boolean") || name.equalsIgnoreCase("char") ||
				name.equalsIgnoreCase("byte") || name.equalsIgnoreCase("void") || name.equalsIgnoreCase("short") ||
				name.equalsIgnoreCase("integer") || name.equalsIgnoreCase("character");
	}
	
	/**
	 * Checks if the given object is a primitive wrapper object
	 * @param o The object
	 * @return True of the object represents a primitive wrapper object
	 */
	public static boolean isPrimitiveObject(Object o) {
		return o instanceof Integer || o instanceof Long || o instanceof Double || o instanceof Float ||
				o instanceof Boolean || o instanceof Character || o instanceof Byte || o instanceof Void ||
				o instanceof Short;
	}
	
	/**
	 * This attempts to coerce a string into a standard java object
	 * @param containingString The string to coerce
	 * @return The object, either a {@link Double}, {@link Integer}, {@link Character}, 
	 * {@link Boolean}, null, or {@link String}
	 */
	public static Object coerceStringToJavaObject(String containingString) {
		if (MathUtils.isStringNumber(containingString)) {
			if (containingString.contains("."))
				return Double.parseDouble(containingString);
			else
				return Integer.parseInt(containingString);
		}
		if (containingString.length() == 1)
			return containingString.charAt(0);
		if (containingString.equals("false") || containingString.equals("true"))
			return Boolean.parseBoolean(containingString);
		if (containingString.equals("void"))
			return null;
		return containingString;
	}
	
	/**
	 * Creates an array from a string
	 * @param containingString The string
	 * @return The array
	 */
	public static Object[] coerceStringToArray(String containingString) {
		containingString = containingString.replace("{", "").replace("}", "").trim();
		String[] objects = containingString.split(",");
		Object[] array = new Object[objects.length];
		for (int i = 0; i < objects.length; i++)
			array[i] = coerceStringToJavaObject(objects[i].trim());
		return array;
	}
	
	/**
	 * Creates an array from a string
	 * @param containingString The string
	 * @param toObject The type for the array
	 * @return The array
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	public static Object[] coerceStringToArray(String containingString, Object toObject) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		containingString = containingString.replace("{", "").replace("}", "").trim();
		String[] objects = containingString.split(",");
		Object[] array = new Object[objects.length];
		for (int i = 0; i < objects.length; i++)
			array[i] = objectFromString(objects[i].trim(), toObject.getClass().newInstance()); //FIXME: Don't use newInstance()
		return array;
	}
	
	/**
	 * Tries to create an object from a string
	 * @param objectString The string representing the object
	 * @param object The object to deserialize the string into
	 * @return The object
	 * @throws IOException
	 */
	public static Object objectFromString(String objectString, Object object) throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		if (object == null)
			if (objectString.contains("[") && objectString.endsWith("]")) {
				return coerceStringToArray(objectString);
			} else {
				return coerceStringToJavaObject(objectString);
			}
		if (object instanceof ISerializable)
			return ((ISerializable) object).deserialize(objectString);
		if (object.getClass().isArray())
			return coerceStringToArray(objectString, object);
		BufferedReader reader = new BufferedReader(new StringReader(objectString));
		String line, block = "", blockField = null, blockType = null;
		boolean isReadingBlock = false;
		while ((line = reader.readLine()) != null) {
			line = line.replace("\t", "");
			if (!isReadingBlock && line.contains("{")) {
				blockType = line.trim().substring(0, line.indexOf(":"));
				blockField = line.trim().substring(line.indexOf(":")+1, line.indexOf("="));
				isReadingBlock = true;
				continue;
			}
			if (isReadingBlock && line.trim().equals("}")) {
				isReadingBlock = false;
				Field field = getDeclaredOrNormalField(blockField, object.getClass());
				field.setAccessible(true);
				field.set(object, objectFromString(objectString, getTypeFromSignature(blockType).newInstance())); //FIXME: Don't use newInstance()
				continue;
			}
			if (isReadingBlock) {
				block = block+line;
				continue;
			}
			if (line.length() > 4) {
				Field field = getDeclaredOrNormalField(line.trim().substring(line.indexOf(":")+1, line.contains("=") ? line.indexOf("=") : line.length()), object.getClass());
				if (field != null) {
					field.setAccessible(true);
					field.set(object, coerceStringToJavaObject(line.substring(line.indexOf("=")+1).trim()));
				}
			}
		}
		return object;
	}
	
	/**
	 * Translates an object into a string readable by {@link #objectFromString}
	 * @param o The object
	 * @return The String
	 */
	public static String objectToString(Object o) {
		return objectToString(o, 0);
	}
	
	/**
	 * Translates an object into a string readable by {@link #objectFromString}
	 * @param o The object
	 * @param tabs The number of tab indents
	 * @return The string
	 */
	public static String objectToString(Object o, int tabs) {
		if (o.getClass().isArray()) {
			String array = "[";
			for (int i = 0; i < Array.getLength(o); i++) {
				array = array+","+objectToString(Array.get(o, i), tabs);
			}
			array = array.replaceFirst(",", "");
			return array+"]";
		}
		if (o instanceof ISerializable)
			return  ((ISerializable) o).serialize();
		if (isPrimitiveObject(o) || o instanceof String)
			return o.toString();
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		Field[] declared = o.getClass().getDeclaredFields();
		for (Field f : declared) {
			f.setAccessible(true);
			try {
				sb.append(StringUtils.repeatString("\t", tabs+1));
				sb.append(getSignatureFromObject(o));
				sb.append(":");
				sb.append(f.getName());
				sb.append("=");
				sb.append(objectToString(f.get(o), tabs+1));
				sb.append("\n");
			} catch (IllegalAccessException e) {
				e.printStackTrace();//This should never be reached
			}
		}
		Field[] normal = o.getClass().getFields();
		for (Field f : normal) {
			f.setAccessible(true);
			try {
				sb.append(StringUtils.repeatString("\t", tabs+1));
				sb.append(getSignatureFromObject(o));
				sb.append(":");
				sb.append(f.getName());
				sb.append("=");
				sb.append(objectToString(f.get(o), tabs+1));
				sb.append("\n");
			} catch (IllegalAccessException e) {
				e.printStackTrace();//This should never be reached
			}
		}
		sb.append(StringUtils.repeatString("\t", tabs));
		sb.append("}");
		return sb.toString();
	}
}
