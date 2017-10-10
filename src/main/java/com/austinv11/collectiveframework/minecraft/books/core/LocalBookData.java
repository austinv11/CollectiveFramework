package com.austinv11.collectiveframework.minecraft.books.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;

public class LocalBookData extends WorldSavedData {
	
	public static String IDENTIFIER = "CF_LOCAL_BOOK_DATA";
	private static HashMap<String, NBTTagCompound> data = new HashMap<String, NBTTagCompound>();
	private static boolean hasLoaded = false;
	
	public LocalBookData() {
		this(IDENTIFIER);
	}
	
	public LocalBookData(String identifier) {
		super(identifier);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		NBTTagList list = tag.getTagList("data", Constants.NBT.TAG_STRING);
		for (int i = 0; i < list.tagCount(); i++)
			data.put(list.getStringTagAt(i), tag.getCompoundTag(list.getStringTagAt(i)));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		NBTTagList list = new NBTTagList();
		for (String key : data.keySet()) {
			list.appendTag(new NBTTagString(key));
			tag.setTag(key, data.get(key));
		}
		tag.setTag("data", list);
		return tag;
	}
	
	private static LocalBookData loadData() {
		if (!hasLoaded)
			createData();
		return (LocalBookData) DimensionManager.getWorld(0).loadData(LocalBookData.class, IDENTIFIER);
	}
	
	private static void createData() {
		DimensionManager.getWorld(0).setData(IDENTIFIER, new LocalBookData());
		hasLoaded = true;
	}
	
	public static NBTTagCompound retrieveLocalData(String key) {
		loadData();
		return data.get(key);
	}
	
	public static void putLocalData(String key, NBTTagCompound tag) {
		loadData();
		data.put(key, tag);
	}
}
