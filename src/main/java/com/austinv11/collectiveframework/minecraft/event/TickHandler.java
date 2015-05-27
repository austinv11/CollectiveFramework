package com.austinv11.collectiveframework.minecraft.event;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.minecraft.init.Keybindings;
import com.austinv11.collectiveframework.minecraft.network.TimeUpdatePacket;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import com.austinv11.collectiveframework.utils.LogicUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

public class TickHandler {
	
	private static long startWorldTime = -1;
	private static int totalTimeChange = 0;
	private static boolean wasDown = false;
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (Config.enableButtonTimeChanging) {
			if (Minecraft.getMinecraft().theWorld != null && LogicUtils.xor(Keybindings.TIME_BACK.getIsKeyPressed(), Keybindings.TIME_FORWARD.getIsKeyPressed())) {
				if (startWorldTime == -1)
					startWorldTime = Minecraft.getMinecraft().theWorld.getWorldTime();
				boolean isForward;
				if (Keybindings.TIME_BACK.getIsKeyPressed())
					isForward = false;
				else
					isForward = true;
				
				wasDown = true;
				int difference = (isForward ? Config.timeChangeRate : -Config.timeChangeRate);
				totalTimeChange += difference;
				Minecraft.getMinecraft().theWorld.setWorldTime(startWorldTime+totalTimeChange);
				Minecraft.getMinecraft().renderGlobal.cloudTickCounter += difference;
				
			}
			if (wasDown && Minecraft.getMinecraft().theWorld != null && LogicUtils.nor(Keybindings.TIME_BACK.getIsKeyPressed(),
					Keybindings.TIME_FORWARD.getIsKeyPressed())) {
				CollectiveFramework.NETWORK.sendToServer(new TimeUpdatePacket(startWorldTime, totalTimeChange));
				startWorldTime = -1;
				totalTimeChange = 0;
				wasDown = false;
			}
		}
	}
}
