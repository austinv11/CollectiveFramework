package com.austinv11.collectiveframework.minecraft.config;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.utils.ArrayUtils;
import com.austinv11.collectiveframework.utils.ReflectionUtils;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * A registry for configs
 */
public class ConfigRegistry {
	
	private static List<ConfigProxy> toBeInitialized = new ArrayList<ConfigProxy>();
	
	public static List<ConfigProxy> configs = new ArrayList<ConfigProxy>();
	private static List<IConfigProxy> proxies = new ArrayList<IConfigProxy>();
	
	static {
		registerConfigProxy(new DefaultProxy());
	}
	
	/**
	 * Registers a config with the registry
	 * @param config The config to register
	 * @throws ConfigException
	 */
	public static void registerConfig(Object config) throws ConfigException {
		if (!config.getClass().isAnnotationPresent(Config.class))
			throw new ConfigException("Config "+config.toString()+" does not contain a Config annotation!");
		Config configAnnotation = config.getClass().getAnnotation(Config.class);
		try {
			ConfigProxy configProxy = new ConfigProxy(configAnnotation, config);
			toBeInitialized.add(configProxy);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigException(e.getMessage());
		}
	}
	
	/**
	 * Registers a config proxy
	 * @param proxy The config proxy
	 */
	public static void registerConfigProxy(IConfigProxy proxy) {
		proxies.add(proxy);
	}
	
	/**
	 * Only meant for internal use
	 */
	public static void init() {
		for (ConfigProxy config : toBeInitialized)
			initialize(config);
		toBeInitialized.clear();
	}
	
	private static void initialize(ConfigProxy configProxy) {
		IConfigurationHandler handler = configProxy.handler;
		ConfigLoadEvent.Init event = new ConfigLoadEvent.Init();
		event.config = handler.convertToString(configProxy.config);
		event.configName = configProxy.fileName;
		event.isRevert = false;
		MinecraftForge.EVENT_BUS.post(event);
		handler.loadFile(configProxy.fileName, configProxy.config, configProxy.fields);
		configs.add(configProxy);
	}
	
	/**
	 * Gets the key for the passed object
	 * @param o The object
	 * @return The key
	 */
	public static String getKey(Object o) {
		for (IConfigProxy proxy : proxies)
			if (proxy.canSerializeObject(o))
				return proxy.getKey(o);
		return "@NULL@";
	}
	
	/**
	 * Serializes the passed object into a string
	 * @param o The object to serialize
	 * @return The serialized string
	 * @throws ConfigException
	 */
	public static String serialize(Object o) throws ConfigException {
		for (IConfigProxy proxy : proxies)
			if (proxy.canSerializeObject(o))
				return proxy.serialize(o);
		return "@NULL@";
	}
	
	/**
	 * Deserializes the passed string with the passed key
	 * @param key The key representing the object type
	 * @param string The serialized string
	 * @return The deserialized object
	 * @throws ConfigException
	 */
	public static Object deserialize(String key, String string) throws ConfigException {
		for (IConfigProxy proxy : proxies)
			if (proxy.isKeyUsable(key))
				return proxy.deserialize(key, string);
		return null;
	}
	
	/**
	 * The default {@link com.austinv11.collectiveframework.minecraft.config.IConfigurationHandler} for configs
	 */
	public static class DefaultConfigurationHandler implements IConfigurationHandler {
		
		private Map<String, Map<String, Field>> current = new HashMap<String, Map<String, Field>>();
		
		private File cachedFile;
		
		@Override
		public void setValue(String configValue, String category, Object value, Object config) {
			setValue(configValue, category, value, config, true);
		}
		
		@Override
		public void setValue(String configValue, String category, Object value, Object config, boolean saveToFile) {
			Map<String, Field> fields = current.containsKey(category) ? current.get(category) : new HashMap<String,Field>();
			Field field = fields.containsKey(configValue) ? fields.get(configValue) : ReflectionUtils.getDeclaredOrNormalField(configValue, config.getClass());
			try {
				field.set(config, value);
			} catch (IllegalAccessException e) {
				e.printStackTrace(); //This should never be reached
			}
			if (saveToFile)
				try {
					writeFile(cachedFile, config);
				} catch (Exception e) {
					e.printStackTrace();
				}
			if (!current.containsKey(category) || !fields.containsKey(configValue)) {
				fields.put(configValue, field);
				current.put(category, fields);
			}
		}
		
		@Override
		public Object getValue(String configValue, String category, Object config) {
			if (hasValue(configValue, category)) {
				if (current.containsKey(category) && current.get(category).containsKey(configValue))
					return current.get(category).get(configValue);
			}
			return null;
		}
		
		@Override
		public void loadFile(String fileName, Object config, Map<String, Map<String, Field>> hint) {
			current = hint;
			cachedFile = new File("./config/"+fileName);
			if (cachedFile.exists())
				try {
					readFile(cachedFile, config);
				} catch (Exception e) {
					e.printStackTrace();
				}
			else {
				try {
					cachedFile.createNewFile();
					writeFile(cachedFile, config);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		@Override
		public void loadFromString(String string, Object config, Map<String, Map<String, Field>> hint) {
			try {
				readFromReader(new BufferedReader(new StringReader(string)), config);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public File getConfigFile(String fileName, Object config) {
			return cachedFile;
		}
		
		@Override
		public boolean hasValue(String configValue, String category) {
			return current.containsKey(category) && current.get(category).containsKey(configValue);
		}
		
		@Override
		public String convertToString(Object config) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			try {
				writeToStream(new PrintStream(stream), config);
				return stream.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		private void writeToStream(PrintStream writer, Object config) throws IllegalAccessException, ConfigException {
			for (String category : current.keySet()) {
				int i = 0;
				for (String field : current.get(category).keySet()) {
					Field f = current.get(category).get(field);
					f.setAccessible(true);
					if (f.isAnnotationPresent(Description.class) && f.getAnnotation(Description.class).clientSideOnly() && 
							CollectiveFramework.proxy.getSide() == Side.SERVER)
						continue;
					if (i == 0)
						writer.println(category+" {");
					String comment = f.isAnnotationPresent(Description.class) ? f.getAnnotation(Description.class).comment() : "None! Tell the mod author to include a comment!";
					writer.println("\t"+comment);
					writer.println("\t"+getKey(f.get(config))+":"+field+"="+serialize(f.get(config)));
					writer.println();
					i++;
					if (i == current.get(category).size())
						writer.println("}");
				}
			}
			writer.flush();
			writer.close();
		}
		
		private void writeFile(File file, Object config) throws IOException, IllegalAccessException, ConfigException {
			PrintStream writer = new PrintStream(file);
			writeToStream(writer, config);
		}
		
		private void readFile(File file, Object config) throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			readFromReader(reader, config);
			try {
				writeFile(file, config);
			} catch (ConfigException e) {
				e.printStackTrace();
			}
		}
		
		private void readFromReader(BufferedReader reader, Object config) throws IOException, IllegalAccessException {
			String line;
			boolean reachedBracket = false;
			int lineCount = 0;
			while ((line = reader.readLine()) != null) {
				if (!reachedBracket && line.contains("{")) {
					reachedBracket = true;
					continue;
				}
				if (reachedBracket && line.equals("}")) {
					reachedBracket = false;
					continue;
				}
				if (reachedBracket) {
					if (lineCount < 1) {
						lineCount++;
					} else if (lineCount == 1) {
						String field = line.substring(line.indexOf(":")+1, line.indexOf("="));
						String key = line.substring(0, line.indexOf(":")).replace("\t", "");
						line = line.substring(line.indexOf("=")+1);
						Field f = ReflectionUtils.getDeclaredOrNormalField(field, config.getClass());
						if (f != null) {
							try {
								f.setAccessible(true);
								f.set(config, deserialize(key, line));
							} catch (ConfigException e) {
								e.printStackTrace();
							}
						}
						lineCount++;
					} else {
						lineCount = 0;
					}
				}
			}
		}
	}
	
	public static class ConfigProxy implements Cloneable {
		
		public Object config;
		public IConfigurationHandler handler;
		public String fileName;
		public boolean doesSync;
		public TreeMap<String, Map<String, Field>> fields = new TreeMap<String, Map<String, Field>>();
		
		public ConfigProxy(Config annotation, Object config) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
			this.config = config;
			this.handler = (IConfigurationHandler) Class.forName(annotation.handler()).newInstance();
			this.fileName = annotation.fileName().equals("@NULL@") ? config.getClass().getSimpleName()+".cfg" : annotation.fileName();
			this.doesSync = annotation.doesSync();
			
			Field[] declared = config.getClass().getDeclaredFields();
			for (Field f : declared) {
				f.setAccessible(true);
				if (ArrayUtils.indexOf(annotation.exclude(), f.getName()) == -1) {
					if (f.isAnnotationPresent(Description.class))
						addToCategory(f.getAnnotation(Description.class).category(), f);
					else
						addToCategory("General", f);
					}
			}
			
			Field[] field = config.getClass().getFields();
			for (Field f : field) {
				f.setAccessible(true);
				if (ArrayUtils.indexOf(annotation.exclude(), f.getName()) == -1)
					if (f.isAnnotationPresent(Description.class))
						addToCategory(f.getAnnotation(Description.class).category(), f);
					else
						addToCategory("General", f);
			}
		}
		
		private void addToCategory(String category, Field f) {
			Map<String, Field> vals;
			if (fields.containsKey(category))
				vals = fields.get(category);
			else
				vals = new TreeMap<String, Field>();
			vals.put(f.getName(), f);
			fields.put(category, vals);
		}
	}
	
	public static void onConfigReload(ConfigLoadEvent.Pre event) {
		if (!event.isCanceled()) {
			if (!event.isRevert) {
				CollectiveFramework.LOGGER.info("Reloading config '"+event.configName+"'");
				ConfigProxy proxy = findConfigProxyForConfigFile(configs, event.configName);
				if (proxy == null) {
					CollectiveFramework.LOGGER.error("There was an error reloading the config!");
					return;
				}
				proxy.handler.loadFromString(event.config, proxy.config, proxy.fields);
			} else {
				CollectiveFramework.LOGGER.info("Reverting config '"+event.configName+"'");
				ConfigProxy proxy = findConfigProxyForConfigFile(configs, event.configName);
				if (proxy == null) {
					CollectiveFramework.LOGGER.error("There was an error reverting the config!");
					return;
				}
				proxy.handler.loadFile(event.config, proxy.config, proxy.fields);
			}
		}
	}
	
	private static ConfigProxy findConfigProxyForConfigFile(List<ConfigProxy> proxies, String filename) {
		for (ConfigProxy proxy : proxies)
			if (proxy.fileName.equals(filename))
				return proxy;
		return null;
	}
}
