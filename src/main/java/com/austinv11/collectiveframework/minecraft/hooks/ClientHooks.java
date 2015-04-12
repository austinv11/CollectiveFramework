package com.austinv11.collectiveframework.minecraft.hooks;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ClientHooks {
	
	private static ResourceLocation defaultBook =  new ResourceLocation("textures/entity/enchanting_table_book.png");
	private static ResourceLocation infuserBook = new ResourceLocation("dartcraft2:textures/blocks/enchanting_table_book.png");
	
	public static ResourceLocation getBookTexture(TileEntityEnchantmentTable table) {
		if (Loader.isModLoaded("DartCraft2") && table.blockMetadata == Integer.MIN_VALUE)
			return infuserBook;
		return defaultBook;
	}
}
