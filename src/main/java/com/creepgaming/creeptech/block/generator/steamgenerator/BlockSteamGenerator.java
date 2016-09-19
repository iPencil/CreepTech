package com.creepgaming.creeptech.block.generator.steamgenerator;

import com.creepgaming.creeptech.block.BlockBase;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSteamGenerator extends BlockBase implements ITileEntityProvider{

	public BlockSteamGenerator(Material materialIn) {
		super(Material.ROCK);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileSteamGenerator();
	}

}
