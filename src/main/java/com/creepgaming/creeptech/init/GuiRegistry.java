package com.creepgaming.creeptech.init;

import com.creepgaming.creeptech.block.machine.crusher.ContainerCrusher;
import com.creepgaming.creeptech.block.machine.crusher.TileCrusher;
import com.creepgaming.creeptech.block.machine.crusher.client.GuiCrusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiRegistry implements IGuiHandler{
	
	public static final int GUI_CRUSHER = 0;
	
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof TileCrusher) {
			TileCrusher tileCrusher = (TileCrusher) tileEntity;
			return new ContainerCrusher(player.inventory, tileCrusher);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof TileCrusher) {
			TileCrusher tileCrusher = (TileCrusher) tileEntity;
			return new GuiCrusher(player.inventory, tileCrusher);
		}
		return null;
	}

}
