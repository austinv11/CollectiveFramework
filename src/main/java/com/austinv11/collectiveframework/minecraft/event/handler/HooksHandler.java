package com.austinv11.collectiveframework.minecraft.event.handler;

import com.austinv11.collectiveframework.minecraft.event.RenderStringEvent;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import com.austinv11.collectiveframework.minecraft.utils.Colors;
import com.austinv11.collectiveframework.utils.StringUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HooksHandler {
	
	public static volatile boolean kongaTime = false; //:D
	public static final int START_KONGA_TIME = 20;
	public static int kongaTick = 0;
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderString(RenderStringEvent event) {
		if (Config.applyColorPatch)
			event.stringToRender = Colors.replaceAlternateColorChar(event.stringToRender);
		if (Config.enableCloudToButt) {
			String string = event.stringToRender;
			if (string.toLowerCase().contains("cloud")) {
				string = StringUtils.replaceAllPreservingCase(string, "the cloud", "my butt");
				string = StringUtils.replaceAllPreservingCase(string, "cloud", "butt");
				event.stringToRender = string;
			}
		}
		if (event.stringToRender.toLowerCase().contains("konga")) {
			kongaTime = true;
			kongaTick = START_KONGA_TIME;
		}
	}
}
