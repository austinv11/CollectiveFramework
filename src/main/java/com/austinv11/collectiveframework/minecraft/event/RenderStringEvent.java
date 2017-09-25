package com.austinv11.collectiveframework.minecraft.event;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
