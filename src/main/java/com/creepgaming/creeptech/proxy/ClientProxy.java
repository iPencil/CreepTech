package com.creepgaming.creeptech.proxy;

import com.creepgaming.creeptech.init.BlockRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		BlockRegistry.initModels();
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);

	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);

	}

}
