package com.austinv11.collectiveframework.minecraft.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.List;

/**
 * A class for aiding in manipulating worlds
 */
public class WorldUtils {
	
	/**
	 * Gets a player with the given username in the provided world
	 * @param playerName The player username
	 * @param world The world
	 * @return The player, or null if the player wasn't found
	 */
	public static EntityPlayer getPlayerForWorld(String playerName, World world) {
		for (EntityPlayer player : (List<EntityPlayer>) world.playerEntities)
			if (player.getCommandSenderName().equals(playerName))
				return player;
		return null;
	}
	
	/**
	 * Gets the world for the provided dimension id
	 * @param id The dimension id
	 * @return The world
	 */
	public static World getWorldFromDimensionId(int id) {
		return MinecraftServer.getServer().worldServerForDimension(id);
	}
}
