package com.austinv11.collectiveframework.minecraft.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows for a class to act as a config, simplifies config creation.
 * Both static and declared fields represent config key-value pairs (unless excluded)
 * Register an instance of the config with {@link ConfigRegistry#registerConfig(Object)}
 * The config could be initialized at any three init stages-depending on when you register your config
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Config {
	
	/**
	 * If preferred, the custom file name, otherwise it uses the class name
	 * @return The file name (include the extension)
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
	 * Whether configs should be synced between server to client.
	 * Note: You could configure this by subscribing to the {@link ConfigLoadEvent}, 
	 * cancelling {@link ConfigLoadEvent.Pre} will prevent syncing 
	 * @return True to enable syncing
	 */
	boolean doesSync() default true;
}
