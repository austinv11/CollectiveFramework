package com.austinv11.collectiveframework.minecraft.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.world.WorldServer;

/**
 * This is just a simple spoof EntityPlayerMP implementation
 */
public class GhostEntityPlayerMP extends EntityPlayerMP {
	
	private String name;
	
	public GhostEntityPlayerMP(ICommandSender sender) {
		super(MinecraftServer.getServer(), (WorldServer) sender.getEntityWorld(), 
				new GameProfile(null, sender.getCommandSenderName()), new ItemInWorldManager(sender.getEntityWorld()));
		name = sender.getCommandSenderName();
		this.posX = sender.getPlayerCoordinates().posX;
		this.posY = sender.getPlayerCoordinates().posY;
		this.posZ = sender.getPlayerCoordinates().posZ;
	}
	
	@Override
	public String getDisplayName() {
		return name;
	} 
	
	@Override
	public String getCommandSenderName() {
		return name;
	}
	
	public static EntityPlayerMP getPlayerForSender(ICommandSender sender) {
		if (sender instanceof EntityPlayerMP)
			return (EntityPlayerMP) sender;
		EntityPlayerMP player = (EntityPlayerMP) WorldUtils.getPlayerForWorld(sender.getCommandSenderName(), sender.getEntityWorld());
		if (player != null)
			return player;
		return new GhostEntityPlayerMP(sender);
	}
}
