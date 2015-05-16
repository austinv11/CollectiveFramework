package com.austinv11.collectiveframework.minecraft.network;

import com.austinv11.collectiveframework.minecraft.config.ConfigReloadEvent;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public class ConfigPacket implements IMessage {
	
	public String configName;
	public String config;
	
	public ConfigPacket()  {
		
	}
	
	public ConfigPacket(String configName, String config) {
		this.configName = configName;
		this.config = config;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		configName = tag.getString("configName");
		config = tag.getString("config");
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("configName", configName);
		tag.setString("config", config);
		ByteBufUtils.writeTag(buf, tag);
	}
	
	public static class ConfigPacketHandler implements IMessageHandler<ConfigPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ConfigPacket message, MessageContext ctx) {
			ConfigReloadEvent.Pre event = new ConfigReloadEvent.Pre();
			event.configName = message.configName;
			event.config = message.config;
			event.isRevert = false;
			MinecraftForge.EVENT_BUS.post(event);
			return null;
		}
	}
}
