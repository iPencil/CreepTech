package com.creepgaming.creeptech.network.packets;

import com.creepgaming.creeptech.block.storage.energystorage.TileEnergyStorage;
import com.creepgaming.creeptech.init.PacketRegistry;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketEnergyStored implements IMessage {

	// A default constructor is always required
	public PacketEnergyStored() {
	}

	private BlockPos toSend;

	public PacketEnergyStored(BlockPos toSend) {
		this.toSend = toSend;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// Writes the int into the buf
	    buf.writeInt(toSend.getX());
	    buf.writeInt(toSend.getY());
	    buf.writeInt(toSend.getZ());
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		// Reads the int back from the buf. Note that if you have multiple
		// values, you must read in the same order you wrote.
	    toSend = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}
	

	

	public static class PacketEnergyStoredHandler implements IMessageHandler<PacketEnergyStored, IMessage> {
		// Do note that the default constructor is required, but implicitly
		// defined in this case

		@Override
		public IMessage onMessage(PacketEnergyStored message, MessageContext ctx) {

			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketEnergyStored message, MessageContext ctx) {

			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
			// The value that was sent
			BlockPos pos = message.toSend;
			
			TileEnergyStorage tileEntity = (TileEnergyStorage) serverPlayer.worldObj.getTileEntity(pos);
			
		PacketReturnEnergyStored packet = new PacketReturnEnergyStored(tileEntity.showStoredEnergy());
		PacketRegistry.INSTANCE.sendTo(packet, serverPlayer);
		
	}
}
}