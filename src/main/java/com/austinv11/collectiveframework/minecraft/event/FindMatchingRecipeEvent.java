package com.austinv11.collectiveframework.minecraft.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * This event is posted when a crafting recipe is refreshed.
 * Note: This is called BEFORE any item is crafted
 * This is fired on the MinecraftForge event bus
 */
public class FindMatchingRecipeEvent extends Event {
	
	/**
	 * The crafting inventory
	 */
	public InventoryCrafting craftingInventory;
	/**
	 * The world the crafting is taking place in
	 */
	public World world;
	/**
	 * The result of the crafting recipe
	 */
	public ItemStack result;
}
