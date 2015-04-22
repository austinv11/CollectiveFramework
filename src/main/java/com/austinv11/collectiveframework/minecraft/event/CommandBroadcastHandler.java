package com.austinv11.collectiveframework.minecraft.event;

import com.austinv11.collectiveframework.minecraft.reference.Config;
import com.austinv11.collectiveframework.minecraft.utils.GhostEntityPlayerMP;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.command.CommandBase;
import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;

public class CommandBroadcastHandler {
	
	@SubscribeEvent
	public void onCommand(CommandEvent event) {
		if (!event.isCanceled() && Config.commandBoradcastRelay) {
			if (event.command instanceof CommandBroadcast) {
				IChatComponent component = CommandBase.func_147176_a(event.sender, event.parameters, 0, true);
				MinecraftForge.EVENT_BUS.post(new ServerChatEvent(GhostEntityPlayerMP.getPlayerForSender(event.sender), 
						event.parameters[0], new ChatComponentTranslation("chat.type.announcement", new Object[] {event.sender.getCommandSenderName(), component})));
			}
		}
	}
}
