package com.austinv11.collectiveframework.minecraft.hooks;

import com.austinv11.collectiveframework.minecraft.event.ProcreationEvent;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.common.MinecraftForge;

public class CommonHooks {
	
	public static EntityAgeable procreatePre(EntityAgeable child, EntityAnimal parent1, EntityAnimal parent2) {
		ProcreationEvent.Pre event = new ProcreationEvent.Pre(child, parent1, parent2);
		return MinecraftForge.EVENT_BUS.post(event) ? null : event.child;
	}
	
	public static void procreatePost(EntityAgeable child, EntityAnimal parent1, EntityAnimal parent2) {
		ProcreationEvent.Post event = new ProcreationEvent.Post(child, parent1, parent2);
		MinecraftForge.EVENT_BUS.post(event);
	}
}
