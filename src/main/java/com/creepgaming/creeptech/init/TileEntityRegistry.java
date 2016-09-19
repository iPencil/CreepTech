package com.creepgaming.creeptech.init;

import com.creepgaming.creeptech.block.generator.steamgenerator.TileSteamGenerator;
import com.creepgaming.creeptech.block.generator.windgenerator.TileWindGenerator;
import com.creepgaming.creeptech.block.storage.debug.TileDebug;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {

	public static void registerTileEntities() {

		
		GameRegistry.registerTileEntity(TileDebug.class, "tile_debug");
		GameRegistry.registerTileEntity(TileWindGenerator.class, "tile_wind_generator");
		GameRegistry.registerTileEntity(TileSteamGenerator.class, "tile_steam_generator");
		
	}

}
