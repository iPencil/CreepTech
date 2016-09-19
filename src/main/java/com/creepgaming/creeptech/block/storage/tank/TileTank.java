package com.creepgaming.creeptech.block.storage.tank;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.capability.TileFluidHandler;

public class TileTank extends TileFluidHandler {

	public static final int CAPACITY = 16 * Fluid.BUCKET_VOLUME;
	
	public TileTank(){
		tank = new FluidTankWithTile(this, CAPACITY);
		
	}
	
	
	
	
}
