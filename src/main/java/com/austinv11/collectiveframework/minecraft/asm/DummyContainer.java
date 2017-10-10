package com.austinv11.collectiveframework.minecraft.asm;

import com.austinv11.collectiveframework.minecraft.reference.Reference;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModClassLoader;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;

public class DummyContainer extends DummyModContainer {
	
	public static ModClassLoader loader;
	
	public DummyContainer() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = Reference.MOD_ID+"Core";
		meta.name = Reference.MOD_NAME+" Core";
		meta.version = Reference.VERSION;
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
	
	@Subscribe
	public void modConstruction(FMLConstructionEvent event) {
		loader = event.getModClassLoader();
	}
}
