package com.austinv11.collectiveframework.minecraft.event.handler;

import com.austinv11.collectiveframework.minecraft.reference.Config;
import com.austinv11.collectiveframework.minecraft.utils.GhostEntityPlayerMP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommandBroadcastHandler {
	
	@SubscribeEvent
	public void onCommand(CommandEvent event) {
		if (!event.isCanceled() && Config.commandBroadcastRelay) {
			if (event.getCommand() instanceof CommandBroadcast) {
				ITextComponent component = null;
				try {
					component = CommandBase.getChatComponentFromNthArg(event.getSender(), event.getParameters(),
							0, true);
				} catch (CommandException e) {
					e.printStackTrace();
					return;
				}
				MinecraftForge.EVENT_BUS.post(new ServerChatEvent(
						GhostEntityPlayerMP.getPlayerForSender(event.getSender()),
						event.getParameters()[0], new TextComponentTranslation("chat.type.announcement",
						event.getSender().getName(), component)));
			}
		}
	}
}
