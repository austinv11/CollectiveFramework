package com.austinv11.collectiveframework.minecraft.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.ResourceLocation;

/**
 * This event is posted when the book of an enchanting table is being rendered.
 * This is fired on the MinecraftForge event bus
 */
@SideOnly(Side.CLIENT)
public class RetrieveBookTextureEvent extends Event {
	
	/**
	 * The texture to use
	 */
	public ResourceLocation bookTexture;
	/**
	 * The enchantment table
	 */
	public TileEntityEnchantmentTable table;
}
