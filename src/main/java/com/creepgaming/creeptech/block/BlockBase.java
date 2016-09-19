package com.creepgaming.creeptech.block;

import com.creepgaming.creeptech.CreepTech;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

	/*
	 * Used as base for all blocks. 
	 * I mean, cmon it's 1 line less code per
	 * block! xD
	 */

	public BlockBase(Material materialIn) {
		super(materialIn);
		this.setCreativeTab(CreepTech.creepTechTab);
	}

}
