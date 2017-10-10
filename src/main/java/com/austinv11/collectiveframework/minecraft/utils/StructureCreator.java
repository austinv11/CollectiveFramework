package com.austinv11.collectiveframework.minecraft.utils;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

/**
 * A utility to aid in creating world gen structures
 */
public class StructureCreator {
	
	private HashMap<Location, Block> blocks = new HashMap<Location,Block>();
	private HashMap<Block, Integer> metas = new HashMap<Block,Integer>();
	private HashMap<Location, ICheckBlockValidity> errorCheckers = new HashMap<Location,ICheckBlockValidity>();
	
	/**
	 * Adds a block to the structure with the default implementation of {@link com.austinv11.collectiveframework.minecraft.utils.StructureCreator.ICheckBlockValidity}
	 * @param location The location of the block
	 * @param block The block
	 */
	public void addBlock(Location location, Block block) {
		addBlock(location, block, 0);
	}
	
	/**
	 * Adds a block to the structure with the default implementation of {@link com.austinv11.collectiveframework.minecraft.utils.StructureCreator.ICheckBlockValidity}
	 * @param location The location of the block
	 * @param block The block
	 * @param meta The meta for the block
	 */
	public void addBlock(Location location, Block block, int meta) {
		addBlock(location, block, meta, new DefaultValidityChecker());
	}
	
	/**
	 * Adds a block to the structure with a custom implementation of {@link com.austinv11.collectiveframework.minecraft.utils.StructureCreator.ICheckBlockValidity}
	 * @param location The location of the block
	 * @param block The block
	 * @param meta The meta for the block
	 * @param validityChecker The {@link com.austinv11.collectiveframework.minecraft.utils.StructureCreator.ICheckBlockValidity} to use
	 */
	public void addBlock(Location location, Block block, int meta, ICheckBlockValidity validityChecker) {
		if (!blocks.containsKey(location)) {
			blocks.put(location, block);
			errorCheckers.put(location, validityChecker);
			metas.put(block, meta);
		}
	}
	
	/**
	 * Checks if the structure is valid for spawning
	 * @return True if the structure can be generated, false if otherwise
	 */
	public boolean isStructureValid() {
		for (Location l : blocks.keySet()) {
			if (!errorCheckers.get(l).canStructureGenerate(blocks.get(l), metas.get(blocks.get(l)), l))
				return false;
		}
		return true;
	}
	
	/**
	 * Generates the structure, it won't generate if {@link #isStructureValid()} is false
	 */
	public void generateStructure() {
		generateStructure(false);
	}
	
	/**
	 * Generates the structure with the set flags, it won't generate if {@link #isStructureValid()} is false
	 * @param forceOptionalBlocks If true, blocks which don't need to be generated for the structure to spawn will spawn
	 */
	public void generateStructure(boolean forceOptionalBlocks) {
		generateStructure(forceOptionalBlocks, false);
	}
	
	/**
	 * Generates the structure with the set flags
	 * @param forceOptionalBlocks If true, blocks which don't need to be generated for the structure to spawn will spawn
	 * @param forceNonOptionalBlocks If true, the structure will generate regardless of the results of {@link #isStructureValid()}
	 */
	public void generateStructure(boolean forceOptionalBlocks, boolean forceNonOptionalBlocks) {
		if (!forceNonOptionalBlocks && !isStructureValid())
			return;
		for (Location l : blocks.keySet()) {
			if (errorCheckers.get(l).isBlockValid(blocks.get(l), metas.get(blocks.get(l)), l) || forceOptionalBlocks)
				l.getWorld().setBlockState(new BlockPos(l.getRoundedX(), l.getRoundedY(), l.getRoundedZ()),
						blocks.get(l).getDefaultState(), 2);
		}
	}
	
	public static class DefaultValidityChecker implements ICheckBlockValidity {
		
		@Override
		public boolean isBlockValid(Block block, int meta, Location location) {
			return location.getWorld().isAirBlock(
					new BlockPos(location.getRoundedX(), location.getRoundedY(), location.getRoundedZ()))
					|| location.getWorld().getBlockState(
							new BlockPos(location.getRoundedX(), location.getRoundedY(), location.getRoundedZ()))
					.getMaterial().isReplaceable();
		}
		
		@Override
		public boolean canStructureGenerate(Block block, int meta, Location location) {
			return isBlockValid(block, meta, location);
		}
	}
	
	/**
	 * Implement this if you want to check for custom conditions
	 */
	public static interface ICheckBlockValidity {
		
		/**
		 * Checks if the block can be generated in the desired location
		 * @param block The block
		 * @param meta The metadata for the block
		 * @param location The location
		 * @return True if valid
		 */
		public boolean isBlockValid(Block block, int meta, Location location);
		
		/**
		 * Same as {@link #isBlockValid(Block, int, Location)} except if false, the whole structure is prevented from generating.
		 * Most times you just want this method to consist of:
		 * {@code return this.isBlockValid(block, location);}
		 */
		public boolean canStructureGenerate(Block block, int meta, Location location);
	}
}
