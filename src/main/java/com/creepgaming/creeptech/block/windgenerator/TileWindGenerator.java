package com.creepgaming.creeptech.block.windgenerator;

import com.creepgaming.creeptech.Config;
import com.creepgaming.creeptech.helper.energy.EnergyHandler;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileWindGenerator extends TileEntity implements IEnergyProvider, IEnergyConnection, ITickable {

	@Override
	public int getEnergyStored(EnumFacing from) {
		return 5;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return 5;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		
	

		return 5;
		
	}

	@Override
	public void update() {

		if (!worldObj.isRemote && this.getPos().getY() > 63 && worldObj.canSeeSky(this.getPos().offset(EnumFacing.UP))) {

			if (worldObj.isRainingAt(this.getPos())){
			EnergyHandler.handleSendEnergy(this.getPos(), this.worldObj, Config.windGeneratorRainRF);
			}else{
			EnergyHandler.handleSendEnergy(this.getPos(), this.worldObj, Config.windGeneratorRF);	
			}

		}

	}

}
