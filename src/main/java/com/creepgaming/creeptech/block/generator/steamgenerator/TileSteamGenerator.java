package com.creepgaming.creeptech.block.generator.steamgenerator;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileSteamGenerator extends TileEntity implements IEnergyProvider, IEnergyConnection, ITickable {

	@Override
	public int getEnergyStored(EnumFacing from) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		// TODO Auto-generated method stub
		return 0;
	}

}
