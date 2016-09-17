package com.creepgaming.creeptech.tileentity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {

	public static void registerTileEntities() {

		
		GameRegistry.registerTileEntity(TileDebug.class, "tile_debug");
		GameRegistry.registerTileEntity(TileWindGenerator.class, "tile_wind_generator");
		
	}

}
