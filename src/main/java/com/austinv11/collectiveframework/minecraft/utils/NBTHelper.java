package com.austinv11.collectiveframework.minecraft.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;

import java.util.List;

/**
 * This is a class to ease the use of NBTTags with ItemStacks.
 * All methods will initialize an NBT Tag if none exists on the passed ItemStack
 */
public class NBTHelper {
	
	/**
	 * Checks if the passed ItemStack contains the given tag in its NBTData
	 * @param itemStack The stack
	 * @param keyName The key for the tag
	 * @return True if the stack contains the key
	 */
	public static boolean hasTag(ItemStack itemStack, String keyName){
		return itemStack != null && itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey(keyName);
	}
	
	/**
	 * Removes the specified tag from the stack's NBTData
	 * @param itemStack The stack
	 * @param keyName The key representing the tag
	 */
	public static void removeTag(ItemStack itemStack, String keyName){
		if (itemStack.getTagCompound() != null){
			itemStack.getTagCompound().removeTag(keyName);
		}
	}
	
	private static void initNBTTagCompound(ItemStack itemStack){
		if (itemStack.getTagCompound() == null){
			itemStack.setTagCompound(new NBTTagCompound());
		}
	}
	
	/**
	 * Gets a value for the given key from the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @return The value
	 */
	public static long getLong(ItemStack itemStack, String keyName){
		initNBTTagCompound(itemStack);
		if (!itemStack.getTagCompound().hasKey(keyName)){
			setLong(itemStack, keyName, 0);
		}
		return itemStack.getTagCompound().getLong(keyName);
	}
	
	/**
	 * Sets the value of the provided key on the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key representing the value
	 * @param keyValue The value for the key
	 */
	public static void setLong(ItemStack itemStack, String keyName, long keyValue){
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setLong(keyName, keyValue);
	}
	
	/**
	 * Gets a value for the given key from the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @return The value
	 */
	public static String getString(ItemStack itemStack, String keyName){
		initNBTTagCompound(itemStack);
		if (!itemStack.getTagCompound().hasKey(keyName)){
			setString(itemStack, keyName, "");
		}
		return itemStack.getTagCompound().getString(keyName);
	}
	
	/**
	 * Sets the value of the provided key on the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key representing the value
	 * @param keyValue The value for the key
	 */
	public static void setString(ItemStack itemStack, String keyName, String keyValue){
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setString(keyName, keyValue);
	}
	
	/**
	 * Gets a value for the given key from the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @return The value
	 */
	public static boolean getBoolean(ItemStack itemStack, String keyName){
		initNBTTagCompound(itemStack);
		if (!itemStack.getTagCompound().hasKey(keyName)){
			setBoolean(itemStack, keyName, false);
		}
		return itemStack.getTagCompound().getBoolean(keyName);
	}
	
	/**
	 * Sets the value of the provided key on the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key representing the value
	 * @param keyValue The value for the key
	 */
	public static void setBoolean(ItemStack itemStack, String keyName, boolean keyValue){
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setBoolean(keyName, keyValue);
	}
	
	/**
	 * Gets a value for the given key from the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @return The value
	 */
	public static byte getByte(ItemStack itemStack, String keyName){
		initNBTTagCompound(itemStack);
		if (!itemStack.getTagCompound().hasKey(keyName)){
			setByte(itemStack, keyName, (byte) 0);
		}
		return itemStack.getTagCompound().getByte(keyName);
	}
	
	/**
	 * Sets the value of the provided key on the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key representing the value
	 * @param keyValue The value for the key
	 */
	public static void setByte(ItemStack itemStack, String keyName, byte keyValue){
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setByte(keyName, keyValue);
	}
	
	/**
	 * Gets a value for the given key from the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @return The value
	 */
	public static short getShort(ItemStack itemStack, String keyName){
		initNBTTagCompound(itemStack);
		if (!itemStack.getTagCompound().hasKey(keyName)){
			setShort(itemStack, keyName, (short) 0);
		}
		return itemStack.getTagCompound().getShort(keyName);
	}
	
	/**
	 * Sets the value of the provided key on the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key representing the value
	 * @param keyValue The value for the key
	 */
	public static void setShort(ItemStack itemStack, String keyName, short keyValue){
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setShort(keyName, keyValue);
	}
	
	/**
	 * Gets a value for the given key from the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @return The value
	 */
	public static int getInt(ItemStack itemStack, String keyName){
		initNBTTagCompound(itemStack);
		if (!itemStack.getTagCompound().hasKey(keyName))
		{
			setInteger(itemStack, keyName, 0);
		}
		return itemStack.getTagCompound().getInteger(keyName);
	}
	
	/**
	 * Sets the value of the provided key on the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key representing the value
	 * @param keyValue The value for the key
	 */
	public static void setInteger(ItemStack itemStack, String keyName, int keyValue){
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setInteger(keyName, keyValue);
	}
	
	/**
	 * Gets a value for the given key from the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @return The value
	 */
	public static float getFloat(ItemStack itemStack, String keyName){
		initNBTTagCompound(itemStack);
		if (!itemStack.getTagCompound().hasKey(keyName)){
			setFloat(itemStack, keyName, 0);
		}
		return itemStack.getTagCompound().getFloat(keyName);
	}
	
	/**
	 * Sets the value of the provided key on the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key representing the value
	 * @param keyValue The value for the key
	 */
	public static void setFloat(ItemStack itemStack, String keyName, float keyValue){
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setFloat(keyName, keyValue);
	}
	
	/**
	 * Gets a value for the given key from the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @return The value
	 */
	public static double getDouble(ItemStack itemStack, String keyName){
		initNBTTagCompound(itemStack);
		if (!itemStack.getTagCompound().hasKey(keyName)){
			setDouble(itemStack, keyName, 0);
		}
		return itemStack.getTagCompound().getDouble(keyName);
	}
	
	/**
	 * Sets the value of the provided key on the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key representing the value
	 * @param keyValue The value for the key
	 */
	public static void setDouble(ItemStack itemStack, String keyName, double keyValue){
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setDouble(keyName, keyValue);
	}
	
	/**
	 * Gets the {@link NBTTagList} for the provided key in the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @param type The type of tag list, see {@link net.minecraftforge.common.util.Constants.NBT}
	 * @return The list
	 */
	public static NBTTagList getList(ItemStack itemStack, String keyName, int type) {
		initNBTTagCompound(itemStack);
		if (!itemStack.getTagCompound().hasKey(keyName)){
			setList(itemStack, keyName, new NBTTagList());
		}
		return itemStack.getTagCompound().getTagList(keyName, type);
	}
	
	/**
	 * Sets the {@link NBTTagList} for the provided key in the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @param tag The tag list
	 */
	public static void setList(ItemStack itemStack, String keyName, NBTTagList tag) {
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setTag(keyName, tag);
	}
	
	/**
	 * Appends an item description to the stack
	 * @param itemStack The stack
	 * @param text The text for the description
	 */
	public static void addInfo(ItemStack itemStack, List<String> text) {
		initNBTTagCompound(itemStack);
		if (hasTag(itemStack, "display")) {
			NBTTagCompound display = itemStack.getTagCompound().getCompoundTag("display");
			NBTTagList list = display.getTagList("Lore", Constants.NBT.TAG_STRING);
			for (String s : text)
				list.appendTag(new NBTTagString(s));
			display.setTag("Lore", list);
			itemStack.getTagCompound().setTag("display", display);
		}else {
			NBTTagCompound display = new NBTTagCompound();
			NBTTagList list = new NBTTagList();
			for (String s : text)
				list.appendTag(new NBTTagString(s));
			display.setTag("Lore", list);
			itemStack.getTagCompound().setTag("display", display);
		}
	}
	
	/**
	 * Sets an item description to the stack
	 * @param itemStack The stack
	 * @param text The text for the description
	 */
	public static void setInfo(ItemStack itemStack, List<String> text) {
		initNBTTagCompound(itemStack);
		NBTTagCompound display = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		for (String s : text)
			list.appendTag(new NBTTagString(s));
		display.setTag("Lore", list);
		itemStack.getTagCompound().setTag("display", display);
	}
	
	/**
	 * Removes the item description to the stack
	 * @param itemStack The stack
	 */
	public static void removeInfo(ItemStack itemStack) {
		if (hasTag(itemStack, "display")) {
			NBTTagCompound display = itemStack.getTagCompound().getCompoundTag("display");
			display.removeTag("Lore");
			itemStack.getTagCompound().setTag("display", display);
		}
	}
	
	/**
	 * Sets a tag for the provided key in the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @param tag The tag
	 */
	public static void setTag(ItemStack itemStack, String keyName, NBTBase tag) {
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setTag(keyName, tag);
	}
	
	/**
	 * Gets a tag for the provided key in the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @return The tag
	 */
	public static NBTBase getTag(ItemStack itemStack, String keyName) {
		initNBTTagCompound(itemStack);
		return itemStack.getTagCompound().getTag(keyName);
	}
	
	/**
	 * Sets the value of the provided key on the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key representing the value
	 * @param array The value for the key
	 */
	public static void setIntArray(ItemStack itemStack, String keyName, int[] array) {
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setIntArray(keyName, array);
	}
	
	/**
	 * Gets a value for the given key from the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @return The value
	 */
	public static int[] getIntArray(ItemStack itemStack, String keyName) {
		initNBTTagCompound(itemStack);
		return itemStack.getTagCompound().getIntArray(keyName);
	}
	
	/**
	 * Sets a compound tag for the provided key in the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @param tag The tag
	 */
	public static void setCompoundTag(ItemStack itemStack, String keyName, NBTTagCompound tag) {
		initNBTTagCompound(itemStack);
		itemStack.getTagCompound().setTag(keyName, tag);
	}
	
	/**
	 * Gets a compound tag for the provided key in the passed ItemStack
	 * @param itemStack The stack
	 * @param keyName The key
	 * @return The tag
	 */
	public static NBTTagCompound getCompoundTag(ItemStack itemStack, String keyName) {
		initNBTTagCompound(itemStack);
		return itemStack.getTagCompound().getCompoundTag(keyName);
	}
}
