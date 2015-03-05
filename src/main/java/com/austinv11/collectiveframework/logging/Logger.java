package com.austinv11.collectiveframework.logging;

import com.austinv11.collectiveframework.reference.Reference;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

/**
 * A simple class for simplified logging
 */
public class Logger {
	
	private static void log(Level level, Object object){
		FMLLog.log(Reference.MOD_NAME, level, String.valueOf(object));
	}
	
	/**
	 * Logs a message at the ALL level
	 * @param object The object to log
	 */
	public static void all (Object object){
		log(Level.ALL, object);
	}
	
	/**
	 * Logs a message at the DEBUG level
	 * @param object The object to log
	 */
	public static void debug(Object object){
		log(Level.DEBUG, object);
	}
	
	/**
	 * Logs a message at the ERROR level
	 * @param object The object to log
	 */
	public static void error(Object object){
		log(Level.ERROR, object);
	}
	
	/**
	 * Logs a message at the FATAL level
	 * @param object The object to log
	 */
	public static void fatal(Object object){
		log(Level.FATAL, object);
	}
	
	/**
	 * Logs a message at the INFO level
	 * @param object The object to log
	 */
	public static void info(Object object){
		log(Level.INFO, object);
	}
	
	/**
	 * Logs a message at the OFF level
	 * @param object The object to log
	 */
	public static void off(Object object){
		log(Level.OFF, object);
	}
	
	/**
	 * Logs a message at the TRACE level
	 * @param object The object to log
	 */
	public static void trace(Object object){
		log(Level.TRACE, object);
	}
	
	/**
	 * Logs a message at the WARN level
	 * @param object The object to log
	 */
	public static void warn(Object object){
		log(Level.WARN, object);
	}
}
