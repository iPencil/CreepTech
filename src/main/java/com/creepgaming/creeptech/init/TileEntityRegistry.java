package com.creepgaming.creeptech.init;

import com.creepgaming.creeptech.block.generator.steamgenerator.TileSteamGenerator;
import com.creepgaming.creeptech.block.generator.windgenerator.TileWindGenerator;
import com.creepgaming.creeptech.block.storage.energystorage.TileEnergyStorage;
import com.creepgaming.creeptech.block.storage.tank.TileTank;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {

	public static void registerTileEntities() {

		
		GameRegistry.registerTileEntity(TileEnergyStorage.class, "tile_energy_storage");
		GameRegistry.registerTileEntity(TileWindGenerator.class, "tile_wind_generator");
		GameRegistry.registerTileEntity(TileSteamGenerator.class, "tile_steam_generator");
		GameRegistry.registerTileEntity(TileTank.class, "tile_tank");
		
	}

}
