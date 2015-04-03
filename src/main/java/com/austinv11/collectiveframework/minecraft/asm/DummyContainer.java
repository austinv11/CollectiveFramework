package com.austinv11.collectiveframework.minecraft.asm;

import com.austinv11.collectiveframework.minecraft.reference.Reference;
import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModClassLoader;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DummyContainer extends DummyModContainer {
	
	public static ModClassLoader loader;
	
	public DummyContainer() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = Reference.MOD_ID+"Core";
		meta.name = Reference.MOD_NAME+"Core";
		meta.version = Reference.VERSION;
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
	
	@SubscribeEvent
	public void modConstruction(FMLConstructionEvent event){
		loader = event.getModClassLoader();
	}
}
