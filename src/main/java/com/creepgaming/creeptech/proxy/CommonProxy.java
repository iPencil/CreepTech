package com.creepgaming.creeptech.proxy;

import com.creepgaming.creeptech.block.BlockRegistry;
import com.creepgaming.creeptech.item.ItemRegistry;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {

		ItemRegistry.registerItems();
		BlockRegistry.registerBlocks();

	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {

	}

}
