package com.austinv11.collectiveframework.minecraft.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Set;

/**
 * A slot implementation which allows for slots to have a black/whitelist of items allowed
 */
public class ExclusiveSlot extends Slot {
	
	private Set<Item> exclusive;
	private boolean whitelist = true;
	
	public ExclusiveSlot(IInventory inv, int index, int xPos, int yPos) {
		super(inv, index, xPos, yPos);
	}
	
	/**
	 * Sets the black/whitelist
	 * @param exclusive The set for the black/whitelist
	 * @return This instance, for convenience building
	 */
	public ExclusiveSlot setExclusive(Set<Item> exclusive) {
		this.exclusive = exclusive;
		return this;
	}
	
	/**
	 * Sets whether to do a whitelist or blacklist
	 * @param whitelist Set this to true for a whitelist (default) false for blacklist
	 * @return This instance, for convenience building
	 */
	public ExclusiveSlot setWhitelist(boolean whitelist) {
		this.whitelist = whitelist;
		return this;
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		if (exclusive != null) {
			if (whitelist)
				return exclusive.contains(itemStack.getItem());
			else
				return !exclusive.contains(itemStack.getItem());
		}
		return super.isItemValid(itemStack);
	}
}
