package com.austinv11.collectiveframework.minecraft.utils;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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
			world.setBlockState(new BlockPos(x, y, z), block.getDefaultState());
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
		if (!world.isAirBlock(new BlockPos(x, y, z))) {
			if (!world.containsAnyLiquid(new AxisAlignedBB(x, y, z, x+1, y+1, z+1))) {
				if (world.getBlockState(new BlockPos(x, y, z)).getBlock().equals(Blocks.STONE) ||
						world.getBlockState(new BlockPos(x, y, z)).getBlock().equals(Blocks.NETHERRACK) ||
						world.getBlockState(new BlockPos(x, y, z)).getBlock().equals(Blocks.END_STONE))
					return true;
			}
		}
		return false;
	}
}
