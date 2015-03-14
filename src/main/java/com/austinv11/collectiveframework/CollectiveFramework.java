package com.austinv11.collectiveframework;

import com.austinv11.collectiveframework.books.Book;
import com.austinv11.collectiveframework.books.BookFactory;
import com.austinv11.collectiveframework.books.elements.SimplePage;
import com.austinv11.collectiveframework.client.gui.GuiHandler;
import com.austinv11.collectiveframework.dependencies.DependencyManager;
import com.austinv11.collectiveframework.dependencies.download.BinaryProvider;
import com.austinv11.collectiveframework.dependencies.download.PlainTextProvider;
import com.austinv11.collectiveframework.language.TranslationManager;
import com.austinv11.collectiveframework.language.translation.YandexProvider;
import com.austinv11.collectiveframework.proxy.CommonProxy;
import com.austinv11.collectiveframework.reference.Reference;
import com.austinv11.collectiveframework.utils.ConfigurationHandler;
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
	
	@Mod.Instance(Reference.MOD_ID)
	public static CollectiveFramework instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.NETWORK_NAME);
		TranslationManager.registerTranslationProvider(new YandexProvider());
		DependencyManager.registerDownloadProvider(new PlainTextProvider());
		DependencyManager.registerDownloadProvider(new BinaryProvider());
		registerEvents();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		new BookFactory().setIcon(new ResourceLocation("null")).setName("null").addElement(0, new SimplePage()).build();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		for (Book book : BookFactory.bookRegistry.keySet()) //TODO: Remove, this is for debugging
			GameRegistry.registerItem(book, book.getName());
	}
	
	private void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new TranslationManager());
	}
}
