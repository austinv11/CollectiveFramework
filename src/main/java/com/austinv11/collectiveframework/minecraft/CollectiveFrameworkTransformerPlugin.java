package com.austinv11.collectiveframework.minecraft;

import com.austinv11.collectiveframework.minecraft.asm.CollectiveFrameworkTransformer;
import com.austinv11.collectiveframework.minecraft.config.ConfigRegistry;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.SortingIndex(Integer.MAX_VALUE) //Want asm as late as possible to get srg names
public class CollectiveFrameworkTransformerPlugin implements IFMLLoadingPlugin, IFMLCallHook {
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{CollectiveFrameworkTransformer.class.getName()};
	}
	
	@Override
	public String getModContainerClass() {
		return null;
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
		ConfigRegistry.registerConfig(new Config());
		ConfigRegistry.earlyInit();
		return null;
	}
}
