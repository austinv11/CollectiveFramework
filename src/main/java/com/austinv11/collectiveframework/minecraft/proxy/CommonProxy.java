package com.austinv11.collectiveframework.minecraft.proxy;

import com.austinv11.collectiveframework.minecraft.event.CommandBroadcastHandler;
import com.austinv11.collectiveframework.minecraft.event.TooltipHandler;
import com.austinv11.collectiveframework.minecraft.utils.MinecraftTranslator;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	
	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new MinecraftTranslator());
		MinecraftForge.EVENT_BUS.register(new TooltipHandler());
		MinecraftForge.EVENT_BUS.register(new CommandBroadcastHandler());
	}
	
	public Side getSide() {
		return Side.SERVER;
	}
}
