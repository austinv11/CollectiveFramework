package com.austinv11.collectiveframework.minecraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

/**
 * An entity implementation with an inventory
 */
public abstract class EntityInventory extends Entity implements IInventory {

	public ItemStack[] items;

	public EntityInventory(World world) {
		super(world);
		items = new ItemStack[getSizeInventory()];
	}

	@Override
	protected void entityInit() {}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		NBTTagList nbttaglist = tag.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		items = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 0xff;
			if (j >= 0 && j < items.length) {
				items[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				items[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		tag.setTag("Items", nbttaglist);
	}

	@Override
	public abstract int getSizeInventory();

	@Override
	public ItemStack getStackInSlot(int slot) {
		return items != null ? items[slot] : null;
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
	public abstract String getInventoryName();

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public abstract int getInventoryStackLimit();

	@Override
	public void markDirty() {
		for (int i = 0; i < items.length; i++)
			if (items[i] != null && items[i].stackSize < 1)
				items[i] = null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public abstract boolean isItemValidForSlot(int slot, ItemStack stack);

	@Override
	public abstract boolean interactFirst(EntityPlayer player);
}
