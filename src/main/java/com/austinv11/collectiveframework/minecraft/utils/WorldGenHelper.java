package com.austinv11.collectiveframework.minecraft.utils;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * A class to aid in the creation of custom world generation
 */
public class WorldGenHelper {
	
	/**
	 * Ensures that the ore block is spawning correctly in the ground
	 * @param world The world
	 * @param x The x coord
	 * @param y The y coord
	 * @param z The z coord
	 * @param block The block to spawn
	 * @return True if the block spawned
	 */
	public static boolean spawnOreBlock(World world, int x, int y, int z, Block block) {
		if (isLocationSuitableForOre(world, x, y, z)) {
			world.setBlock(x, y, z, block, 0, 2);
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the world location is fit to spawn an ore block
	 * @param world The world
	 * @param x The x coord
	 * @param y The y coord
	 * @param z The z coord
	 * @return True if the block can be spawned
	 */
	public static boolean isLocationSuitableForOre(World world, int x, int y, int z) {
		if (!world.isAirBlock(x, y, z)) {
			if (!world.isAnyLiquid(AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1, z+1))) {
				if (world.getBlock(x, y, z).equals(Blocks.stone) || world.getBlock(x, y, z).equals(Blocks.netherrack) ||
						world.getBlock(x, y, z).equals(Blocks.end_stone))
					return true;
			}
		}
		return false;
	}
}
