package com.austinv11.collectiveframework.minecraft.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.world.WorldServer;

/**
 * This is just a simple spoof EntityPlayerMP implementation
 */
public class GhostEntityPlayerMP extends EntityPlayerMP {
	
	private String name;
	
	public GhostEntityPlayerMP(ICommandSender sender) {
		super(sender.getServer(), (WorldServer) sender.getEntityWorld(),
				new GameProfile(null, sender.getName()),
				new PlayerInteractionManager(sender.getEntityWorld()));
		name = sender.getName();
		this.posX = sender.getPosition().getX();
		this.posY = sender.getPosition().getY();
		this.posZ = sender.getPosition().getZ();
	}
	
	@Override
	public String getDisplayNameString() {
		return name;
	}
	
	public static EntityPlayerMP getPlayerForSender(ICommandSender sender) {
		if (sender instanceof EntityPlayerMP)
			return (EntityPlayerMP) sender;
		EntityPlayerMP player = (EntityPlayerMP) WorldUtils.getPlayerForWorld(sender.getName(), sender.getEntityWorld());
		if (player != null)
			return player;
		return new GhostEntityPlayerMP(sender);
	}
}
