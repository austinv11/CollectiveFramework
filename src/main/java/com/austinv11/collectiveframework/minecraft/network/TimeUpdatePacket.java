package com.austinv11.collectiveframework.minecraft.network;

import com.austinv11.collectiveframework.minecraft.reference.Config;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class TimeUpdatePacket implements IMessage {
	
	public long startTime;
	public int time;
	
	public TimeUpdatePacket() {}
	
	public TimeUpdatePacket(long startTime, int time) {
		this.startTime = startTime;
		this.time = time;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		startTime = tag.getLong("startTime");
		time = tag.getInteger("time");
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setLong("startTime", startTime);
		tag.setInteger("time", time);
		ByteBufUtils.writeTag(buf, tag);
	}
	
	public static class TimeUpdatePacketHandler implements IMessageHandler<TimeUpdatePacket, IMessage> {
		
		@Override
		public IMessage onMessage(TimeUpdatePacket message, MessageContext ctx) {
			if (Config.enableButtonTimeChanging) {
				for (WorldServer world : MinecraftServer.getServer().worldServers)
					world.setWorldTime(message.startTime+message.time);
			}
			return null;
		}
	}
}
