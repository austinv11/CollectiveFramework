package com.austinv11.collectiveframework.minecraft.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This event is posted when the Minecraft FontRenderer is attempting to render a String
 * This is fired on the MinecraftForge event bus
 */
@SideOnly(Side.CLIENT)
public class RenderStringEvent extends Event {
	
	/**
	 * The string to render
	 */
	public String stringToRender;
}
