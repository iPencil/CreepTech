package com.creepgaming.creeptech.network.packets;

import com.creepgaming.creeptech.block.storage.energystorage.BlockEnergyStorage;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketReturnEnergyStored implements IMessage{

	
	// A default constructor is always required
	public PacketReturnEnergyStored() {
	}

	private int rfStored;

	public PacketReturnEnergyStored(int rfStored) {
		this.rfStored = rfStored;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// Writes the int into the buf
	    buf.writeInt(rfStored);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		// Reads the int back from the buf. Note that if you have multiple
		// values, you must read in the same order you wrote.
	    rfStored = buf.readInt();
	}
	

	

	public static class PacketReturnEnergyHandler implements IMessageHandler<PacketReturnEnergyStored, IMessage> {
		// Do note that the default constructor is required, but implicitly
		// defined in this case

		@Override
		public IMessage onMessage(PacketReturnEnergyStored message, MessageContext ctx) {

			BlockEnergyStorage.rfStored = message.rfStored;
			return null;
		}


	
}
}