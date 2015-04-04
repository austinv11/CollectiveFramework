package com.austinv11.collectiveframework.minecraft.reference;

import com.austinv11.collectiveframework.minecraft.config.Description;

@com.austinv11.collectiveframework.minecraft.config.Config(fileName = Reference.MOD_ID+".cfg", earlyInit = true, exclude = {"test_2"})
public class Config {
	
	@Description(comment = "Setting this to true will attempt to translate item names which are not localized", category = "Translation")
	public static boolean translateItems = false;
	
	@Description(comment = "Setting this to true will attempt to translate chat messages", category = "Translation")
	public static boolean translateChat = false;
	
	@Description(comment = "This causes threads (which utilizes SimpleRunnable) to be reused; this *may* lead to performance improvements on lower end machines", category = "Misc")
	public static boolean restrictThreadUsage = true;
	
	@Description(comment = "If enabled (ONLY EFFECTS YOUR CLIENT) you could use essentials color codes", category = "Misc")
	public static boolean applyColorPatch = true;
}
