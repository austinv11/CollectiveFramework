package com.austinv11.collectiveframework.minecraft.proxy;

import com.austinv11.collectiveframework.minecraft.client.gui.KeyOverlay;
import com.austinv11.collectiveframework.minecraft.event.KeyHandler;
import com.austinv11.collectiveframework.minecraft.utils.IconManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerEvents() {
		super.registerEvents();
		MinecraftForge.EVENT_BUS.register(new IconManager());
		MinecraftForge.EVENT_BUS.register(new KeyOverlay());
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}
	
	@Override
	public Side getSide() {
		return Side.CLIENT;
	}
}
