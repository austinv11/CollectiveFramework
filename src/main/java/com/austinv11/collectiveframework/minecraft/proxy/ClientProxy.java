package com.austinv11.collectiveframework.minecraft.proxy;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.minecraft.client.gui.KeyOverlay;
import com.austinv11.collectiveframework.minecraft.client.gui.KongaOverlay;
import com.austinv11.collectiveframework.minecraft.event.handler.HooksHandler;
import com.austinv11.collectiveframework.minecraft.event.handler.KeyHandler;
import com.austinv11.collectiveframework.minecraft.event.handler.ClientTickHandler;
import com.austinv11.collectiveframework.minecraft.event.handler.TooltipHandler;
import com.austinv11.collectiveframework.minecraft.init.Keybindings;
import com.austinv11.collectiveframework.minecraft.utils.ModelManager;
import com.austinv11.collectiveframework.minecraft.utils.TextureManager;
import com.austinv11.collectiveframework.minecraft.utils.MinecraftTranslator;
import com.austinv11.collectiveframework.multithreading.SimpleRunnable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import rehost.javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void prepareClient() {
		Keybindings.init();
		
		//This isn't the code you are looking for...
		SimpleRunnable kongaThread = new SimpleRunnable() {
			
			private Player player;
			private boolean closed = false;
			
			@Override
			public void run() {
				if (HooksHandler.kongaTime && player == null) {
					try {
						URL url = new URL("http://www.matmartinez.net/nsfw/konga.mp3");
						URLConnection urlConn = url.openConnection();
						urlConn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
						BufferedInputStream audioSrc = new BufferedInputStream(urlConn.getInputStream());
						CollectiveFramework.LOGGER.info("Prepare to dance!");
						player = new Player(audioSrc);
						SimpleRunnable manager = new SimpleRunnable() {
							
							@Override
							public void run() {
								if (!HooksHandler.kongaTime) {
									player.close();
									closed = true;
									disable(true);
								}
							}
							
							@Override
							public String getName() {
								return "Konga Manager Thread";
							}
						};
						manager.setTicking(1);
						manager.start();
						player.play();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (player != null && (player.isComplete() || closed)) {
					player = null;
					closed = false;
				}
			}
			
			@Override
			public String getName() {
				return "Konga Thread";
			}
		};
		kongaThread.setTicking(1);
		kongaThread.start();
	}
	
	@Override
	public void registerEvents() {
		super.registerEvents();
		MinecraftForge.EVENT_BUS.register(new TextureManager());
		MinecraftForge.EVENT_BUS.register(new ModelManager());
		MinecraftForge.EVENT_BUS.register(new KeyOverlay());
		FMLCommonHandler.instance().bus().register(new KeyHandler());
		MinecraftForge.EVENT_BUS.register(new KongaOverlay());
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
		MinecraftForge.EVENT_BUS.register(new MinecraftTranslator());
		MinecraftForge.EVENT_BUS.register(new TooltipHandler());
	}
	
	@Override
	public Side getSide() {
		return Side.CLIENT;
	}
}
