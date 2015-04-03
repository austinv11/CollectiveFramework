package com.austinv11.collectiveframework.minecraft;

import com.austinv11.collectiveframework.minecraft.asm.EarlyTransformer;
import com.austinv11.collectiveframework.minecraft.books.Book;
import com.austinv11.collectiveframework.minecraft.books.BookFactory;
import com.austinv11.collectiveframework.minecraft.books.elements.SimplePage;
import com.austinv11.collectiveframework.minecraft.client.gui.GuiHandler;
import com.austinv11.collectiveframework.minecraft.compat.modules.Modules;
import com.austinv11.collectiveframework.minecraft.config.ConfigRegistry;
import com.austinv11.collectiveframework.minecraft.event.EventHandler;
import com.austinv11.collectiveframework.minecraft.logging.Logger;
import com.austinv11.collectiveframework.minecraft.proxy.CommonProxy;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import com.austinv11.collectiveframework.minecraft.reference.Reference;
import com.austinv11.collectiveframework.multithreading.SimpleRunnable;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid= Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION/*, guiFactory = Reference.GUI_FACTORY_CLASS*/)
public class CollectiveFramework {
	
	public static SimpleNetworkWrapper NETWORK;
	
	public static Logger LOGGER = new Logger(Reference.MOD_NAME);
	
	@Mod.Instance(Reference.MOD_ID)
	public static CollectiveFramework instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		registerEvents();
		Modules.init();
		SimpleRunnable.RESTRICT_THREAD_USAGE = Config.restrictThreadUsage;
		NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.NETWORK_NAME);
		Modules.propagate(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		ConfigRegistry.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		new BookFactory().setIcon(new ResourceLocation("null")).setName("null").addElement(0, new SimplePage()).build();
		Modules.propagate(event);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		for (Book book : BookFactory.bookRegistry.keySet()) //TODO: Remove, this is for debugging
			GameRegistry.registerItem(book, book.getName());
		Modules.propagate(event);
	}
	
	private void registerEvents() {
		for (String clazz : EarlyTransformer.eventHandlerClasses) {
			try {
				Class<?> handlerClass = Class.forName(clazz);
				EventHandler handler = handlerClass.getAnnotation(EventHandler.class);
				Object toRegister = handler.instanceName().isEmpty() ? handlerClass.newInstance() : handlerClass.getDeclaredField(handler.instanceName());
				if (handler.fmlBus())
					FMLCommonHandler.instance().bus().register(toRegister);
				if (handler.forgeBus())
					MinecraftForge.EVENT_BUS.register(toRegister);
			} catch (Exception e) {
				LOGGER.warn("Problem caught with event handler: "+clazz);
				e.printStackTrace();
			}
		}
	}
}
