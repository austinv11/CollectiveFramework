package com.austinv11.collectiveframework.minecraft.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

/**
 * Simple implementation for a Tile Entity to contain an inventory
 */
public abstract class TileEntityInventory extends TileEntity implements IInventory {

	public ItemStack[] items;
	public String invName = "null";
	private int numUsingPlayers = 0;
	
	public TileEntityInventory() {
		items = new ItemStack[getSize()];
	}
	
	/**
	 * This represents how many slots are in the inventory
	 * @return The number of slots
	 */
	public abstract int getSize();
	
	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		items = new ItemStack[getSize()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 0xff;
			if (j >= 0 && j < items.length) {
				items[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				items[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}

	@Override
	public int getSizeInventory() {
		return getSize();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (items.length > slot)
			return items[slot];
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (items.length > slot) {
			if (items[slot] != null) {
				if (items[slot].stackSize <= amount) {
					ItemStack item = items[slot];
					items[slot] = null;
					markDirty();
					return item;
				}
				ItemStack item = items[slot].splitStack(amount);
				if (items[slot].stackSize == 0) {
					items[slot] = null;
				}
				markDirty();
				return item;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (items.length > slot) {
			if (this.items[slot] != null) {
				ItemStack item = this.items[slot];
				this.items[slot] = null;
				return item;
			}
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (items.length > slot) {
			items[slot] = stack;
			if (stack != null && stack.stackSize > getInventoryStackLimit()) {
				stack.stackSize = getInventoryStackLimit();
			}
			markDirty();
		}
	}

	@Override
	public String getInventoryName() {
		return invName;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (worldObj == null) {
			return true;
		}
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
	}

	@Override
	public void openInventory() {
		numUsingPlayers++;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, this.getBlockType(), 1, numUsingPlayers);
	}

	@Override
	public void closeInventory() {
		numUsingPlayers--;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, this.getBlockType(), 1, numUsingPlayers);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
	
	@Override
	public void markDirty() {
		for (int i = 0; i < items.length; i++)
			if (items[i] != null && items[i].stackSize < 1)
				items[i] = null;
		super.markDirty();
	}
}
