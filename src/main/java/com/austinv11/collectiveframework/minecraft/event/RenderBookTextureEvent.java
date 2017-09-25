package com.austinv11.collectiveframework.minecraft.event;

import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This event is posted when the book of an enchanting table is being rendered.
 * This is fired on the MinecraftForge event bus
 */
@SideOnly(Side.CLIENT)
public class RenderBookTextureEvent extends Event {
	
	/**
	 * The texture to use
	 */
	public ResourceLocation bookTexture;
	/**
	 * The enchantment table
	 */
	public TileEntityEnchantmentTable table;
}
