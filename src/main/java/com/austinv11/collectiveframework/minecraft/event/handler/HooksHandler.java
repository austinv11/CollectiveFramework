package com.austinv11.collectiveframework.minecraft.event.handler;

import com.austinv11.collectiveframework.minecraft.event.RenderStringEvent;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import com.austinv11.collectiveframework.minecraft.utils.Colors;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HooksHandler {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderString(RenderStringEvent event) {
		if (Config.applyColorPatch)
			event.stringToRender = Colors.replaceAlternateColorChar(event.stringToRender);
	}
}
