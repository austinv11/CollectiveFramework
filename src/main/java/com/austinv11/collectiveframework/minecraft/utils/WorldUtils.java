package com.austinv11.collectiveframework.minecraft.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

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
		return world.getPlayerEntityByName(playerName);
	}
	
	/**
	 * Gets the world for the provided dimension id
	 * @param id The dimension id
	 * @return The world
	 */
	public static World getWorldFromDimensionId(int id) {
		return DimensionManager.getWorld(id);
	}
	
	/**
	 * Gets the nearest entity within a range of a location
	 * @param location The location to search from
	 * @param maxRange The max range to find entities from
	 * @return The nearest entity or null if none were found
	 */
	public static Entity getNearestEntityToLocation(Location location, double maxRange) {
		World world = location.getWorld();
		double x = location.getX(), y = location.getY(), z = location.getZ();
		Vec3d desired = new Vec3d(x, y, z);
		double lowestDistance = maxRange;
		Entity closest = null;
		for (Entity ent : world.loadedEntityList) {
			Vec3d toCompare = new Vec3d(ent.posX, ent.posY, ent.posZ);
			double distance = desired.distanceTo(toCompare);
			if (distance <= maxRange && distance < lowestDistance) {
				lowestDistance = distance;
				closest = ent;
			}
		}
		return closest;
	}
	
	/**
	 * Gets the nearest entity within the recommended max range of a location
	 * @param location The location to search from
	 * @return The nearest entity or null if none were found
	 */
	public static Entity getNearestEntityToLocation(Location location) {
		return getNearestEntityToLocation(location, Double.MAX_VALUE);
	}
	
	/**
	 * Spawns an item in the world at the specified location
	 * @param location The location for the item to be spawned
	 * @param stack The item to spawn
	 * @return The entity representing the item
	 */
	public static EntityItem spawnItemInWorld(Location location, ItemStack stack) {
		EntityItem item = new EntityItem(location.getWorld(), location.getX(), location.getY(), location.getZ(), stack);
		location.getWorld().spawnEntity(item);
		return item;
	}
	
	/**
	 * Checks if the provided block type exists in the specified location
	 * @param location The location to check
	 * @param blockClass The class of the block to look for
	 * @return True if the block exists, false if otherwise
	 */
	public static boolean doesSpecifiedBlockExists(Location location, Class blockClass) {
		if (!Block.class.isAssignableFrom(blockClass))
			return false;
		World world = location.getWorld();
		int x = location.getRoundedX();
		int y = location.getRoundedY();
		int z = location.getRoundedZ();
		return !world.isAirBlock(new BlockPos(x, y, z)) &&
				blockClass.isAssignableFrom(world.getBlockState(new BlockPos(x, y, z)).getBlock().getClass());
	}
}
