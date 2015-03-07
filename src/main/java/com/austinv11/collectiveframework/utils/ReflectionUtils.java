package com.austinv11.collectiveframework.utils;

import java.lang.annotation.Annotation;
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
}
