package com.austinv11.collectiveframework.minecraft.client.gui;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.minecraft.config.ConfigException;
import com.austinv11.collectiveframework.minecraft.config.ConfigRegistry;
import net.minecraft.client.gui.GuiScreen;

public class CfGuiFactory {
    public static GuiScreen createConfigGui(Object config, GuiScreen parentScreen, String modId, String modName) {
        try {
            return new GuiConfig(parentScreen, modId, modName, config);
        } catch (ConfigException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class GuiConfig extends net.minecraftforge.fml.client.config.GuiConfig {
        private Object config;

        GuiConfig(GuiScreen parentScreen, String modid, String title, Object config) throws ConfigException {
            super(parentScreen,
                    ConfigRegistry.getCategories(config, modid),
                    modid,
                    false,
                    false,
                    title);
            titleLine2 = ConfigRegistry.getFilePath(config);
            this.config = config;
        }

        @Override
        public void onGuiClosed() {
            try {
                ConfigRegistry.onGuiClosed(config, entryList.listEntries);
            } catch (ConfigException e) {
                CollectiveFramework.LOGGER.warn("Failed to save config: " + e.getLocalizedMessage());
            }
        }
    }
}
