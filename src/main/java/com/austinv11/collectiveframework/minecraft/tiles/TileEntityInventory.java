package com.austinv11.collectiveframework.minecraft.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

/**
 * Simple implementation for a Tile Entity to contain an inventory
 */
public abstract class TileEntityInventory extends TileEntity implements IInventory, ITickable {

	public NonNullList<ItemStack> items;
	public String invName = "null";
	private int numUsingPlayers = 0;
	
	public TileEntityInventory() {
		items = NonNullList.withSize(getSize(), ItemStack.EMPTY);
	}
	
	/**
	 * This represents how many slots are in the inventory
	 * @return The number of slots
	 */
	public abstract int getSize();
	
	@Override
	public void update() {

	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		NBTTagList itemsNbt = nbttagcompound.getTagList("items", Constants.NBT.TAG_COMPOUND);
		items = NonNullList.withSize(getSize(), ItemStack.EMPTY);
		for (int itemIndex = 0; itemIndex < items.size(); itemIndex++) {
			NBTTagCompound itemNbt = itemsNbt.getCompoundTagAt(itemIndex);
			items.set(itemIndex, new ItemStack(itemNbt));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		NBTTagList itemsNbt = new NBTTagList();
		for (ItemStack item : items) {
			NBTTagCompound itemNbt = new NBTTagCompound();
			item.writeToNBT(itemNbt);
			itemsNbt.appendTag(itemNbt);
		}
		nbttagcompound.setTag("items", itemsNbt);
		return nbttagcompound;
	}

	@Override
	public int getSizeInventory() {
		return getSize();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (items.size() > slot)
			return items.get(slot);
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return decrStackSizeStatic(this, items, slot, amount);
	}

	public static ItemStack decrStackSizeStatic(IInventory inventory, NonNullList<ItemStack> items, int slot,
												int amount) {
		if (items.size() > slot) {
			if (!items.get(slot).isEmpty()) {
				if (items.get(slot).getCount() <= amount) {
					ItemStack item = items.get(slot);
					items.set(slot, ItemStack.EMPTY);
					inventory.markDirty();
					return item;
				}
				ItemStack item = items.get(slot).splitStack(amount);
				if (items.get(slot).getCount() == 0) {
					items.set(slot, ItemStack.EMPTY);
				}
				inventory.markDirty();
				return item;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		return removeStackFromSlotStatic(items, slot);
	}

	public static ItemStack removeStackFromSlotStatic(NonNullList<ItemStack> items, int slot) {
		if (items.size() > slot) {
			if (!items.get(slot).isEmpty()) {
				ItemStack item = items.get(slot);
				items.set(slot, ItemStack.EMPTY);
				return item;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		setInventorySlotContentsStatic(this, items, slot, stack);
	}

	public static void setInventorySlotContentsStatic(IInventory inventory, NonNullList<ItemStack> items, int slot,
													  ItemStack stack) {
		if (items.size() > slot) {
			items.set(slot, stack);
			if (!stack.isEmpty() && stack.getCount() > inventory.getInventoryStackLimit()) {
				stack.setCount(inventory.getInventoryStackLimit());
			}
			inventory.markDirty();
		}
	}

	@Override
	public String getName() {
		return invName;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (world == null) {
			return true;
		}
		if (world.getTileEntity(getPos()) != this) {
			return false;
		}
		return player.getDistanceSq((double) getPos().getX() + 0.5D, (double) getPos().getY() + 0.5D,
				(double) getPos().getZ() + 0.5D) <= 64D;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		numUsingPlayers++;
		world.addBlockEvent(getPos(), this.getBlockType(), 1, numUsingPlayers);
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		numUsingPlayers--;
		world.addBlockEvent(getPos(), this.getBlockType(), 1, numUsingPlayers);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
	
	@Override
	public void markDirty() {
		for (int i = 0; i < items.size(); i++)
			if (!items.get(i).isEmpty() && items.get(i).getCount() < 1)
				items.set(i, ItemStack.EMPTY);
		super.markDirty();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemStack : items)
			if (!itemStack.isEmpty())
				return false;
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		items.clear();
	}
}
