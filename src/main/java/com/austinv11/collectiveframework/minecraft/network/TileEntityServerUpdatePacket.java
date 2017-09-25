package com.austinv11.collectiveframework.minecraft.network;

import com.austinv11.collectiveframework.minecraft.utils.WorldUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Use this packet to sync tile entities (client to server)
 */
public class TileEntityServerUpdatePacket implements IMessage {
	
	public World world;
	public int x, y, z;
	public NBTTagCompound updateData;
	
	public TileEntityServerUpdatePacket() {
		
	}
	
	public TileEntityServerUpdatePacket(World world, int x, int y, int z, NBTTagCompound updateData) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.updateData = updateData;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		world = WorldUtils.getWorldFromDimensionId(tag.getInteger("dim"));
		x = tag.getInteger("x");
		y = tag.getInteger("y");
		z = tag.getInteger("z");
		updateData = tag.getCompoundTag("tag");
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("dim", world.provider.getDimension());
		tag.setInteger("x", x);
		tag.setInteger("y", y);
		tag.setInteger("z", z);
		tag.setTag("tag", updateData);
		ByteBufUtils.writeTag(buf, tag);
	}
	
	public static class TileEntityServerUpdatePacketHandler implements IMessageHandler<TileEntityServerUpdatePacket, IMessage> {
		
		@Override
		public IMessage onMessage(TileEntityServerUpdatePacket message, MessageContext ctx) {
			TileEntity tileEntity = message.world.getTileEntity(new BlockPos(message.x, message.y, message.z));
			if (tileEntity != null)
				tileEntity.readFromNBT(message.updateData);
			return null;
		}
	}
}
