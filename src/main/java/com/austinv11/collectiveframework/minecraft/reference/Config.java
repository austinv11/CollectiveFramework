package com.austinv11.collectiveframework.minecraft.reference;

import com.austinv11.collectiveframework.minecraft.config.Description;

@com.austinv11.collectiveframework.minecraft.config.Config(fileName = Reference.MOD_ID+".cfg")
public class Config {
	
	@Description(comment = "Setting this to true will attempt to translate item names which are not localized", category = "Translation", clientSideOnly = true)
	public static boolean translateItems = false;
	
	@Description(comment = "Setting this to true will attempt to translate chat messages", category = "Translation", clientSideOnly = true)
	public static boolean translateChat = false;
	
	@Description(comment = "This causes threads (which utilizes SimpleRunnable) to be reused; this *may* lead to performance improvements on lower end machines", category = "Misc")
	public static boolean restrictThreadUsage = true;
	
	@Description(comment = "If enabled (ONLY EFFECTS YOUR CLIENT) you could use essentials color codes", category = "Misc", clientSideOnly = true)
	public static boolean applyColorPatch = true;
	
	@Description(comment = "When enabled, misc tooltips will be displayed on items. Useful for debugging!", category = "Development", clientSideOnly = true)
	public static boolean debugTooltips = false;
	
	@Description(comment = "This causes /say commands to emit ServerChatEvents, disable this if mods are having issues with chat messages", category = "Tweaks")
	public static boolean commandBroadcastRelay = true;
	
	@Description(comment = "This shows overlay for all recently pressed keys", category = "Development", clientSideOnly = true)
	public static boolean keyOverlay = false;
	
	@Description(comment = "This is whether to allow button presses for changing time, you still need to be opped or on a world with cheats enabled", category = "Misc")
	public static boolean enableButtonTimeChanging = true;
	
	@Description(comment = "This is the rate at which time changes by when pressing a time button", category = "Misc")
	public static int timeChangeRate = 30;
	
	@Description(comment = "You know you wanna enable this ;)", category = "Misc", clientSideOnly = true)
	public static boolean enableCloudToButt = false;
	
	@Description(comment = "Plays a click sound when the main menu opens", category = "Misc", clientSideOnly = true)
	public static boolean clickOnMainMenuOpen = true;
}
