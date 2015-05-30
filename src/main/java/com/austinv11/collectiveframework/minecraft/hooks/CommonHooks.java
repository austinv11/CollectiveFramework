package com.austinv11.collectiveframework.minecraft.hooks;

import com.austinv11.collectiveframework.minecraft.event.FindMatchingRecipeEvent;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class CommonHooks {
	
	public static ItemStack getResult(InventoryCrafting craftingInventory, World world) {
		ItemStack result = null;
		//Vanilla behavior:
		for (int j = 0; j < CraftingManager.getInstance().recipes.size(); ++j) {
			IRecipe irecipe = (IRecipe)CraftingManager.getInstance().recipes.get(j);
			
			if (irecipe.matches(craftingInventory, world)) {
				result = irecipe.getCraftingResult(craftingInventory);
				break;
			}
		}
		
		FindMatchingRecipeEvent event = new FindMatchingRecipeEvent();
		event.craftingInventory = craftingInventory;
		event.world = world;
		event.result = result;
		MinecraftForge.EVENT_BUS.post(event);
		return event.result;
	}
}
