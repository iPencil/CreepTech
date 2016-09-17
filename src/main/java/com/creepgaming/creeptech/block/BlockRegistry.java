package com.creepgaming.creeptech.block;

import net.minecraft.block.Block;

public class BlockRegistry {

	public static Block debug_block;

	public static void registerBlocks() {
		debug_block = new DebugBlock();

	}

}
