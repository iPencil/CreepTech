package com.creepgaming.creeptech.block.generator;

import com.creepgaming.creeptech.Reference;
import com.creepgaming.creeptech.block.BlockBase;
import com.creepgaming.creeptech.tileentity.TileWindGenerator;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockWindGenerator extends BlockBase implements ITileEntityProvider {

	public BlockWindGenerator() {
		super(Material.IRON);
		setUnlocalizedName(Reference.MODID + ":block_wind_generator");
		setRegistryName("block_wind_generator");
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileWindGenerator();
	}
}
