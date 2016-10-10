package com.creepgaming.creeptech.proxy;

import java.io.File;
import com.creepgaming.creeptech.Config;
import com.creepgaming.creeptech.CreepTech;
import com.creepgaming.creeptech.Reference;
import com.creepgaming.creeptech.fluid.TMFluidRegistry;
import com.creepgaming.creeptech.init.BlockRegistry;
import com.creepgaming.creeptech.init.CompatRegistry;
import com.creepgaming.creeptech.init.GuiRegistry;
import com.creepgaming.creeptech.init.ItemRegistry;
import com.creepgaming.creeptech.init.PacketRegistry;
import com.creepgaming.creeptech.init.TileEntityRegistry;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

	public static Configuration config;

	public void preInit(FMLPreInitializationEvent e) {

		File directory = e.getModConfigurationDirectory();
		config = new Configuration(new File(directory.getPath(), "CreepTech.cfg"));
		Config.readConfig();

		ItemRegistry.registerItems();
		BlockRegistry.registerBlocks();
		TileEntityRegistry.registerTileEntities();
		CompatRegistry.registerCompats();
		PacketRegistry.registerMessages(Reference.MODID);
		TMFluidRegistry.initFluids();
	}

	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(CreepTech.instance, new GuiRegistry());
	}

	public void postInit(FMLPostInitializationEvent e) {

		if (config.hasChanged()) {
			config.save();
		}

	}

}
