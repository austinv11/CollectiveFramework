package com.austinv11.collectiveframework.minecraft.proxy;

import com.austinv11.collectiveframework.minecraft.utils.MinecraftTranslator;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	
	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new MinecraftTranslator());
	}
}
