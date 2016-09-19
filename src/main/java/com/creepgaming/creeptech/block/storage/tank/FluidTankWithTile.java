package com.creepgaming.creeptech.block.storage.tank;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankWithTile extends FluidTank{

	public FluidTankWithTile(TileEntity tileEntity, int capacity){
		super(capacity);
		tile = tileEntity;
		
	}
	
	
	public FluidTankWithTile(TileEntity tileEntity, FluidStack stack, int capacity) {
		super(stack, capacity);
		tile = tileEntity;
	}

	public FluidTankWithTile(TileEntity tileEntity, Fluid fluid, int amount, int capacity) {
		super(fluid, amount, capacity);
		tile = tileEntity;
	}

}
