package com.austinv11.collectiveframework.minecraft.proxy;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.minecraft.config.ConfigRegistry;
import com.austinv11.collectiveframework.minecraft.event.handler.CommandBroadcastHandler;
import com.austinv11.collectiveframework.minecraft.event.handler.HooksHandler;
import com.austinv11.collectiveframework.minecraft.event.handler.TooltipHandler;
import com.austinv11.collectiveframework.minecraft.utils.MinecraftTranslator;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	
	public void prepareClient() {}
	
	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new MinecraftTranslator());
		MinecraftForge.EVENT_BUS.register(new TooltipHandler());
		MinecraftForge.EVENT_BUS.register(new CommandBroadcastHandler());
		FMLCommonHandler.instance().bus().register(CollectiveFramework.instance);
		MinecraftForge.EVENT_BUS.register(new ConfigRegistry());
		MinecraftForge.EVENT_BUS.register(new HooksHandler());
	}
	
	public Side getSide() {
		return Side.SERVER;
	}
}
