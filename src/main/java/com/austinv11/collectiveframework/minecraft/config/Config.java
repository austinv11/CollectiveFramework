package com.austinv11.collectiveframework.minecraft.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When this annotation is present, a config is registered with this class using all found fields
 * The config is automatically registered unless the class implements {@link com.austinv11.collectiveframework.minecraft.asm.IIgnored}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Config {
	
	/**
	 * If preferred, the custom file name
	 * @return The file name (extension included)
	 */
	String fileName() default "@NULL@";
	
	/**
	 * Used to get a list of fields that you want ignored
	 * @return The fields to ignore
	 */
	String[] exclude() default {};
	
	/**
	 * Class for a custom config handler, it must implement {@link com.austinv11.collectiveframework.minecraft.config.IConfigurationHandler}
	 * @return The class name
	 */
	String handler() default "com.austinv11.collectiveframework.minecraft.config.ConfigRegistry$DefaultConfigurationHandler";
	
	/**
	 * Returns whether the config should be loaded early (like as soon as literally possible)
	 * @return Whether to load the config early
	 */
	boolean earlyInit() default false;
}
