package com.austinv11.collectiveframework.minecraft.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;

/**
 * This represents a procreation event, the event is fired in two stages, {@link com.austinv11.collectiveframework.minecraft.event.ProcreationEvent.Pre}
 * and {@link com.austinv11.collectiveframework.minecraft.event.ProcreationEvent.Post}.
 * Pre lets you cancel the event and Post lets you modify characteristics of animals involved.
 * This is posted on the FORGE event bus.
 */
public class ProcreationEvent extends Event {
	
	/**
	 * The first parent
	 */
	public final EntityAnimal parent1;
	/**
	 * The second parent
	 */
	public final EntityAnimal parent2;
	
	public ProcreationEvent(EntityAgeable child, EntityAnimal parent1, EntityAnimal parent2) {
		this.parent1 = parent1;
		this.parent2 = parent2;
	}
	
	@Cancelable
	public static class Pre extends ProcreationEvent {
		
		/**
		 * The expected child, can be null!
		 */
		public EntityAgeable child;
		
		public Pre(EntityAgeable child, EntityAnimal parent1, EntityAnimal parent2) {
			super(child, parent1, parent2);
			this.child = child;
		}
	}
	
	public static class Post extends ProcreationEvent {
		
		/**
		 * The expected child, can be null!
		 */
		public final EntityAgeable child;
		
		public Post(EntityAgeable child, EntityAnimal parent1, EntityAnimal parent2) {
			super(child, parent1, parent2);
			this.child = child;
		}
	}
}
