package com.austinv11.collectiveframework.minecraft;

import com.austinv11.collectiveframework.minecraft.asm.Transformer;
import com.austinv11.collectiveframework.minecraft.client.gui.GuiHandler;
import com.austinv11.collectiveframework.minecraft.compat.modules.Modules;
import com.austinv11.collectiveframework.minecraft.config.ConfigException;
import com.austinv11.collectiveframework.minecraft.config.ConfigRegistry;
import com.austinv11.collectiveframework.minecraft.logging.Logger;
import com.austinv11.collectiveframework.minecraft.network.ConfigPacket;
import com.austinv11.collectiveframework.minecraft.network.TileEntityClientUpdatePacket;
import com.austinv11.collectiveframework.minecraft.network.TileEntityServerUpdatePacket;
import com.austinv11.collectiveframework.minecraft.network.TimeUpdatePacket;
import com.austinv11.collectiveframework.minecraft.proxy.CommonProxy;
import com.austinv11.collectiveframework.minecraft.reference.Config;
import com.austinv11.collectiveframework.minecraft.reference.Reference;
import com.austinv11.collectiveframework.multithreading.SimpleRunnable;
import com.austinv11.collectiveframework.utils.TimeProfiler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;

@Mod(modid= Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptableRemoteVersions = "*"/*, guiFactory = Reference.GUI_FACTORY_CLASS*/)
public class CollectiveFramework {
	
	public static SimpleNetworkWrapper NETWORK;
	
	public static Logger LOGGER = new Logger(Reference.MOD_NAME);
	
	@Mod.Instance(Reference.MOD_ID)
	public static CollectiveFramework instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	/**
	 * If this is true, the mod is loaded in a development environment
	 */
	public static boolean IS_DEV_ENVIRONMENT;
	private static boolean didCheck = false;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		TimeProfiler profiler = new TimeProfiler();
		try {
			ConfigRegistry.registerConfig(new Config());
		} catch (ConfigException e) {
			e.printStackTrace();
		}
		ConfigRegistry.init();
		checkEnvironment();
		proxy.registerEvents();
		Modules.init();
		SimpleRunnable.RESTRICT_THREAD_USAGE = Config.restrictThreadUsage;
		NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.NETWORK_NAME);
		Modules.propagate(event);
		LOGGER.info("Pre-Init took "+profiler.getTime()+"ms");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		TimeProfiler profiler = new TimeProfiler();
		ConfigRegistry.init();
		checkEnvironment();
		proxy.prepareClient();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		Modules.propagate(event);
		LOGGER.info("Init took "+profiler.getTime()+"ms");
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		TimeProfiler profiler = new TimeProfiler();
		ConfigRegistry.init();
		checkEnvironment();
		NETWORK.registerMessage(TileEntityServerUpdatePacket.TileEntityServerUpdatePacketHandler.class, TileEntityServerUpdatePacket.class, 0, Side.SERVER);
		NETWORK.registerMessage(TileEntityClientUpdatePacket.TileEntityClientUpdatePacketHandler.class, TileEntityClientUpdatePacket.class, 1, Side.CLIENT);
		NETWORK.registerMessage(ConfigPacket.ConfigPacketHandler.class, ConfigPacket.class, 2, Side.CLIENT);
		NETWORK.registerMessage(TimeUpdatePacket.TimeUpdatePacketHandler.class, TimeUpdatePacket.class, 3, Side.SERVER);
		Modules.propagate(event);
		LOGGER.info("Post-Init took "+profiler.getTime()+"ms");
	}
	
	private void checkEnvironment() {
		if (Transformer.didCheck && !didCheck) {
			IS_DEV_ENVIRONMENT = Transformer.isDev;
			didCheck = true;
			if (IS_DEV_ENVIRONMENT)
				LOGGER.info("This is running in a dev environment!");
		}
	}
	
	@SubscribeEvent
	public void onServerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		if (FMLCommonHandler.instance().getSide() == Side.SERVER || FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			for (ConfigRegistry.ConfigProxy proxy : ConfigRegistry.configs) {
				if (proxy.doesSync)
					NETWORK.sendTo(new ConfigPacket(proxy.fileName, proxy.handler.convertToString(proxy.config), false), (EntityPlayerMP) event.player);
			}
	}
	
	@SubscribeEvent
	public void onClientDisconnect(PlayerEvent.PlayerLoggedOutEvent event) {
		if (FMLCommonHandler.instance().getSide() == Side.SERVER || FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			for (ConfigRegistry.ConfigProxy proxy : ConfigRegistry.configs) {
				if (proxy.doesSync)
					NETWORK.sendTo(new ConfigPacket(proxy.fileName, proxy.handler.convertToString(proxy.config), true), (EntityPlayerMP) event.player);
			}
	}
}
