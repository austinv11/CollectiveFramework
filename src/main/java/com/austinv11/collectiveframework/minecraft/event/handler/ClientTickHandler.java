package com.austinv11.collectiveframework.minecraft.event.handler;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.minecraft.init.Keybindings;
import com.austinv11.collectiveframework.minecraft.network.TimeUpdatePacket;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import com.austinv11.collectiveframework.utils.LogicUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientTickHandler {
	
	private static long startWorldTime = -1;
	private static int totalTimeChange = 0;
	private static int startCloudTicks = -1;
	private static boolean wasDown = false;
	
	private static boolean wasKonga = false;
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (HooksHandler.kongaTime && !wasKonga) {
			HooksHandler.kongaTick = HooksHandler.START_KONGA_TIME+1;
			wasKonga = true;
		} 
		if (HooksHandler.kongaTick > 0) {
			HooksHandler.kongaTick--;
		} else if (HooksHandler.kongaTick == 0 && wasKonga) {
			HooksHandler.kongaTime = false;
			wasKonga = false;
		}
		
		if (Config.enableButtonTimeChanging) {
			if (Minecraft.getMinecraft().world != null) {
				if (LogicUtils.xor(Keybindings.TIME_BACK.isPressed(), Keybindings.TIME_FORWARD.isPressed())) {
					if (startWorldTime == -1)
						startWorldTime = Minecraft.getMinecraft().world.getWorldTime();
					if (startCloudTicks == -1)
						startCloudTicks = Minecraft.getMinecraft().renderGlobal.cloudTickCounter;
					
					boolean isForward;
					isForward = !Keybindings.TIME_BACK.isPressed();
					
					wasDown = true;
					int difference = (isForward ? Config.timeChangeRate : -Config.timeChangeRate);
					totalTimeChange += difference;
					Minecraft.getMinecraft().world.setWorldTime(startWorldTime+totalTimeChange);
					Minecraft.getMinecraft().renderGlobal.cloudTickCounter = startCloudTicks+totalTimeChange;
				}
				
				if (wasDown && LogicUtils.nor(Keybindings.TIME_BACK.isPressed(),
						Keybindings.TIME_FORWARD.isPressed())) {
					CollectiveFramework.NETWORK.sendToServer(new TimeUpdatePacket(startWorldTime, totalTimeChange, Minecraft.getMinecraft().player.getGameProfile()));
					startWorldTime = -1;
					totalTimeChange = 0;
					startCloudTicks = -1;
					wasDown = false;
				}
			}
		}
	}
}
