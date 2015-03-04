package com.austinv11.collectiveframework;

import com.austinv11.collectiveframework.dependencies.download.ModpackProvider;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class CollectiveFrameworkPlugin implements IFMLLoadingPlugin, IFMLCallHook {
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}
	
	@Override
	public String getModContainerClass() {
		return null;
	}
	
	@Override
	public String getSetupClass() {
		return "com.austinv11.collectiveframework.CollectiveFrameworkPlugin";
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
			provider.installMods(provider.parseModpackXML(xml), provider.doOverwrite(xml));
		}
		return null;
	}
}
