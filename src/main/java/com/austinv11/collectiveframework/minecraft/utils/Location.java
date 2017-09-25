package com.austinv11.collectiveframework.minecraft.utils;

import com.austinv11.collectiveframework.utils.math.ThreeDimensionalVector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

/**
 * A class which extends upon {@link com.austinv11.collectiveframework.utils.math.ThreeDimensionalVector} in order to provide
 * Minecraft-specific functionality
 */
public class Location extends ThreeDimensionalVector {
	
	private World world;
	
	/**
	 * Default constructor, missing required world variable
	 */
	private Location(double x, double y, double z) {
		super(x, y, z);
	}
	
	/**
	 * Constructs a location object
	 * @param x X coord
	 * @param y Y coord
	 * @param z Z coord
	 * @param world The world
	 */
	public Location(double x, double y, double z, World world) {
		super(x, y, z);
		this.world = world;
	}
	
	/**
	 * Constructs a location object at an entity's location
	 * @param entity The entity
	 */
	public Location(Entity entity) {
		this(entity.posX, entity.posY, entity.posZ, entity.getEntityWorld());
	}
	
	/**
	 * Constructs a location object at a tile entity's location
	 * @param tileEntity The tile entity
	 */
	public Location(TileEntity tileEntity) {
		this(tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(), tileEntity.getWorld());
	}
	
	/**
	 * Clones a location object
	 * @param location The location to clone
	 */
	public Location(Location location) {
		this(location.getX(), location.getY(), location.getZ(), location.getWorld());
	}
	
	/**
	 * Converts ChunkCoordinates to a location
	 * @param coords The coords
	 * @param world The world
	 * @return The location equivalent
	 */
	public static Location locationFromChunkCoords(BlockPos coords, World world) {
		return new Location(coords.getX(), coords.getY(), coords.getZ(), world);
	}
	
	/**
	 * Converts the location into ChunkCoordinates
	 * @return The chunk coordinate equivalent
	 */
	public BlockPos chunkCoordsFromLocation() {
		return new BlockPos(getRoundedX(), getRoundedY(), getRoundedZ());
	}
	
	/**
	 * Gets the world for this object
	 * @return The world
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * Gets the nearby players to this location
	 * @param range The range to search
	 * @return A map, Key = Username, Value = Range
	 */
	public HashMap<String, Double> getPlayers(double range) {
		HashMap<String, Double> map = new HashMap<String,Double>();
		for (EntityPlayer player : world.playerEntities) {
			if (new Location(player).distanceTo(this) <= range) {
				map.put(player.getDisplayNameString(), new Location(player).distanceTo(this));
			}
		}
		return map;
	}

	/**
	 * Converts this into a chunk coordinats object
	 * @return The chunk coordinates
	 */
	public BlockPos toChunkCoordinates() {
		return new BlockPos(getRoundedX(), getRoundedY(), getRoundedZ());
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Location) {
			return ((Location) other).getRoundedX() == getRoundedX() && ((Location) other).getRoundedY() == getRoundedY() 
					&& ((Location) other).getRoundedZ() == getRoundedZ() 
					&& ((Location) other).getWorld().provider.getDimension() == getWorld().provider.getDimension();
		}
		return false;
	}
	
	@Override
	public int hashCode() { //Done to allow for use as keys in HashMaps
		return 0;
	}
}
