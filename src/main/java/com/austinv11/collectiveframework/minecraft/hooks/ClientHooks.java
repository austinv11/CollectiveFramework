package com.austinv11.collectiveframework.minecraft.hooks;

import com.austinv11.collectiveframework.minecraft.event.RenderBookTextureEvent;
import com.austinv11.collectiveframework.minecraft.event.RenderStringEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class ClientHooks {
	
	private static final ResourceLocation defaultBook =  new ResourceLocation("textures/entity/enchanting_table_book.png");
	
	public static ResourceLocation getBookTexture(TileEntityEnchantmentTable table) {
		RenderBookTextureEvent event = new RenderBookTextureEvent();
		event.bookTexture = defaultBook;
		event.table = table;
		MinecraftForge.EVENT_BUS.post(event);
		return event.bookTexture;
	}
	
	public static String getStringToRender(String input) {
		RenderStringEvent event = new RenderStringEvent();
		event.stringToRender = input;
		MinecraftForge.EVENT_BUS.post(event);
		return event.stringToRender;
	}
}
