package com.austinv11.collectiveframework.minecraft.hooks;

import com.austinv11.collectiveframework.minecraft.event.RenderBookTextureEvent;
import com.austinv11.collectiveframework.minecraft.event.RenderStringEvent;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import com.austinv11.collectiveframework.minecraft.utils.client.GuiUtils;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientHooks {
	
	private static final ResourceLocation defaultBook =  new ResourceLocation("textures/entity/enchanting_table_book.png");
	public static boolean didClick = false;
	
	public static ResourceLocation getBookTexture(TileEntityEnchantmentTable table) {
		RenderBookTextureEvent event = new RenderBookTextureEvent();
		event.bookTexture = defaultBook;
		event.table = table;
		MinecraftForge.EVENT_BUS.post(event);
		return event.bookTexture;
	}
	
	public static String getStringToRender(String input) {
		if (!Config.disableRenderTextEvents) {
			RenderStringEvent event = new RenderStringEvent();
			event.stringToRender = input;
			MinecraftForge.EVENT_BUS.post(event);
			return event.stringToRender;
		} else
			return input;
	}
	
	public static void click() {
		if (Config.clickOnMainMenuOpen && !didClick) {
			GuiUtils.playButtonSound();
			didClick = true;
		}
	}
}
