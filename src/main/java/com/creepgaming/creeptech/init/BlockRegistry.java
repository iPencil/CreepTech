package com.creepgaming.creeptech.init;

import com.creepgaming.creeptech.block.generator.windgenerator.BlockWindGenerator;
import com.creepgaming.creeptech.block.storage.energystorage.BlockEnergyStorage;
import com.creepgaming.creeptech.block.storage.tank.BlockTank;

import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRegistry {

	public static Block blockEnergyStorage, blockWindGenerator, blockTank;

	public static void registerBlocks() {
		blockEnergyStorage = new BlockEnergyStorage();
		blockWindGenerator = new BlockWindGenerator();
		blockTank = new BlockTank();

	}

	@SideOnly(Side.CLIENT)
	public static void initModels() {

		((BlockWindGenerator) blockWindGenerator).initModel();
		((BlockEnergyStorage) blockEnergyStorage).initModel();
		((BlockTank) blockTank).initModel();
		
	}

}
