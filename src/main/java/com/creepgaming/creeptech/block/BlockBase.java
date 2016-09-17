package com.creepgaming.creeptech.block;

import com.creepgaming.creeptech.CreepTech;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block{

	public BlockBase(Material materialIn) {
		super(materialIn);
		this.setCreativeTab(CreepTech.creepTechTab);
	}

}
