package com.austinv11.collectiveframework.minecraft.entities;

import com.austinv11.collectiveframework.minecraft.tiles.TileEntityInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

/**
 * An entity implementation with an inventory
 */
public abstract class EntityInventory extends Entity implements IInventory {

	public NonNullList<ItemStack> items;

	public EntityInventory(World world) {
		super(world);
		items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
	}

	@Override
	protected void entityInit() {}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		NBTTagList itemsNbt = tag.getTagList("items", Constants.NBT.TAG_COMPOUND);
		items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		for (int itemIndex = 0; itemIndex < items.size(); itemIndex++) {
			NBTTagCompound itemNbt = itemsNbt.getCompoundTagAt(itemIndex);
			items.set(itemIndex, new ItemStack(itemNbt));
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		NBTTagList itemsNbt = new NBTTagList();
		for (ItemStack item : items) {
			NBTTagCompound itemNbt = new NBTTagCompound();
			item.writeToNBT(itemNbt);
			itemsNbt.appendTag(itemNbt);
		}
		tag.setTag("items", itemsNbt);
	}

	@Override
	public abstract int getSizeInventory();

	@Override
	public ItemStack getStackInSlot(int slot) {
		return items.get(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return TileEntityInventory.decrStackSizeStatic(this, items, slot, amount);
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		return TileEntityInventory.removeStackFromSlotStatic(items, slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		TileEntityInventory.setInventorySlotContentsStatic(this, items, slot, stack);
	}

	@Override
	public abstract String getName();

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public abstract int getInventoryStackLimit();

	@Override
	public void markDirty() {
		for (int i = 0; i < items.size(); i++)
			if (!items.get(i).isEmpty() && items.get(i).getCount() < 1)
				items.set(i, ItemStack.EMPTY);
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public abstract boolean isItemValidForSlot(int slot, ItemStack stack);

	@Override
	public abstract boolean processInitialInteract(EntityPlayer player, EnumHand hand);

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
