package com.austinv11.collectiveframework.minecraft.asm;

import com.austinv11.collectiveframework.minecraft.asm.Transformer;
import com.austinv11.collectiveframework.minecraft.config.ConfigException;
import com.austinv11.collectiveframework.minecraft.config.ConfigRegistry;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.SortingIndex(Integer.MAX_VALUE) //Want asm as late as possible to get srg names
public class CollectiveFrameworkTransformerPlugin implements IFMLLoadingPlugin {
	
	public CollectiveFrameworkTransformerPlugin() {
		try {
			ConfigRegistry.registerConfig(new Config());
		} catch (ConfigException e) {
			e.printStackTrace();
		}
		ConfigRegistry.earlyInit();
	}
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{Transformer.class.getName()};
	}
	
	@Override
	public String getModContainerClass() {
		return null;
	}
	
	@Override
	public String getSetupClass() {
		return null;
	}
	
	@Override
	public void injectData(Map<String,Object> data) {
		
	}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
