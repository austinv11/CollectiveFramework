package com.austinv11.collectiveframework.minecraft.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions(value = {"com.austinv11.collectiveframework.minecraft.asm", "com.austinv11.collectiveframework.minecraft.client.gui"})
@IFMLLoadingPlugin.SortingIndex(Integer.MAX_VALUE) //Want asm as late as possible to get srg names
public class CollectiveFrameworkTransformerPlugin implements IFMLLoadingPlugin {
	
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
