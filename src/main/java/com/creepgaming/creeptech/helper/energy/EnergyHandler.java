package com.creepgaming.creeptech.helper.energy;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergyHandler {
	
	
	public static void handleSendEnergy(BlockPos pos, World world, int rf){
		
		for(EnumFacing facing : EnumFacing.values()){
			
			EnumFacing opposite = facing.getOpposite();
			BlockPos offset = pos.offset(facing);
			TileEntity te = world.getTileEntity(offset);
			if(te instanceof IEnergyHandler && te instanceof IEnergyConnection){
				
				IEnergyConnection connection = (IEnergyConnection)te;
				if (connection.canConnectEnergy(opposite) && te instanceof IEnergyReceiver){
					
					((IEnergyReceiver)te).receiveEnergy(opposite, rf, false);
				}
				
				
			}
			
			
		}
		
	}

}
