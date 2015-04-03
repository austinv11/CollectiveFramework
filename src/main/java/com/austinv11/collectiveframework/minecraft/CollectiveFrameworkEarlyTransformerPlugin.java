package com.austinv11.collectiveframework.minecraft;

import com.austinv11.collectiveframework.minecraft.asm.DummyContainer;
import com.austinv11.collectiveframework.minecraft.asm.EarlyTransformer;
import com.austinv11.collectiveframework.minecraft.utils.download.ModpackProvider;
import cpw.mods.fml.relauncher.CoreModManager;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE) //Want mods installed as early as possible
public class CollectiveFrameworkEarlyTransformerPlugin implements IFMLLoadingPlugin, IFMLCallHook {
	
	public CollectiveFrameworkEarlyTransformerPlugin() {
		try {
			injectNewTransformer();
		} catch (Exception e) {
			CollectiveFramework.LOGGER.fatal("There was a problem injecting the CollectiveFramework ASM Transformer!");
			e.printStackTrace();
		}
	}
	
	private void injectNewTransformer() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Class e = Class.forName("cpw.mods.fml.relauncher.CoreModManager$FMLPluginWrapper");
		Constructor wrapperConstructor = e.getConstructor(new Class[]{String.class, IFMLLoadingPlugin.class, File.class, Integer.TYPE, String[].class});
		Field loadPlugins = CoreModManager.class.getDeclaredField("loadPlugins");
		wrapperConstructor.setAccessible(true);
		loadPlugins.setAccessible(true);
		((List)loadPlugins.get((Object)null)).add(wrapperConstructor.newInstance(new Object[]{"CollectiveFrameworkPlugin", new CollectiveFrameworkTransformerPlugin(), null, Integer.valueOf(Integer.MAX_VALUE), new String[0]}));
	}
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{EarlyTransformer.class.getName()};
	}
	
	@Override
	public String getModContainerClass() {
		return DummyContainer.class.getName();
	}
	
	@Override
	public String getSetupClass() {
		return this.getClass().getName();
	}
	
	@Override
	public void injectData(Map<String,Object> data) {
		
	}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
	
	@Override
	public Void call() throws Exception {
		ModpackProvider provider = new ModpackProvider();
		File xml = new File("modpack.xml");
		if (xml.exists()) {
			CollectiveFramework.LOGGER.info("modpack.xml found! Installing modpack...");
			CollectiveFramework.LOGGER.warn("Do NOT stop the client if it hangs!");
			provider.installMods(provider.parseModpackXML(xml), provider.doOverwrite(xml));
			CollectiveFramework.LOGGER.info("Modpack installed! Restarting Minecraft is recommended (though not required)");
		}
		return null;
	}
}
