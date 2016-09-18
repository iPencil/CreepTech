package com.creepgaming.creeptech.block;

import com.creepgaming.creeptech.block.windgenerator.BlockWindGenerator;

import net.minecraft.block.Block;

public class BlockRegistry {

	public static Block block_debug, block_wind_generator;

	public static void registerBlocks() {
		block_debug = new BlockDebug();
		block_wind_generator = new BlockWindGenerator();

	}

}
