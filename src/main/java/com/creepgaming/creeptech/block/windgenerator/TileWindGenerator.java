package com.creepgaming.creeptech.tileentity;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileWindGenerator extends TileEntity implements IEnergyProvider, IEnergyConnection, ITickable{

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
		
		if (!worldObj.isRemote && this.getPos().getY() > 63){
			handleEnergy();
			
		}
		
	}
	
	private void handleEnergy(){
		
		for(EnumFacing facing : EnumFacing.values()){
			
			BlockPos pos = getPos().offset(facing);
			TileEntity te = worldObj.getTileEntity(pos);
			EnumFacing opposite = facing.getOpposite();
			if(te instanceof IEnergyHandler && te instanceof IEnergyConnection){
				
				IEnergyConnection connection = (IEnergyConnection)te;
				if (connection.canConnectEnergy(opposite) && te instanceof IEnergyReceiver){
					
					((IEnergyReceiver)te).receiveEnergy(opposite, 5, false);
				}
				
				
			}
			
			
		}
		
	}

	
	
	
}
