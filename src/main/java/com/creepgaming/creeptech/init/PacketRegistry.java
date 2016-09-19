package com.creepgaming.creeptech.init;

import com.creepgaming.creeptech.network.packets.PacketEnergyStored;
import com.creepgaming.creeptech.network.packets.PacketEnergyStored.PacketEnergyStoredHandler;
import com.creepgaming.creeptech.network.packets.PacketReturnEnergyStored;
import com.creepgaming.creeptech.network.packets.PacketReturnEnergyStored.PacketReturnEnergyHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketRegistry {
	public static SimpleNetworkWrapper INSTANCE = null;


	private static int packetId = 0;

	public static int nextID() {
		return packetId++;
	}

	public static void registerMessages(String channelName) {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}

	public static void registerMessages() {

		INSTANCE.registerMessage(PacketEnergyStoredHandler.class, PacketEnergyStored.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketReturnEnergyHandler.class, PacketReturnEnergyStored.class, nextID(), Side.CLIENT);
	}
}
