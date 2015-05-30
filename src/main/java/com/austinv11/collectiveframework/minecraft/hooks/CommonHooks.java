package com.austinv11.collectiveframework.minecraft.hooks;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

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
		
	}
}
