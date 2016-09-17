package net.creepgaming.com.creeptech;

import org.apache.logging.log4j.Logger;

import net.creepgaming.com.creeptech.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, useMetadata = true)
public class CreepTech {

	@SidedProxy(clientSide = "com.creepgaming.creeptech.proxy.ClientProxy", serverSide = "com.creepgaming.creeptech.proxy.ServerProxy")
	public static CommonProxy proxy;

	@Mod.Instance
	public static CreepTech instance;

	public static Logger logger;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

}
