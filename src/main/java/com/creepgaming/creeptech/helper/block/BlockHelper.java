package com.creepgaming.creeptech.helper.block;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockHelper {

	/*
	 * Used to determine the front facing block, so that textures orient where
	 * the player look.
	 * 
	 */

	public static EnumFacing getBlockFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity,
			Boolean onlyHorizontal) {
		EnumFacing face = EnumFacing.getFacingFromVector((float) (entity.posX - clickedBlock.getX()),
				(float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));

		if (onlyHorizontal) {

			if (face == EnumFacing.DOWN || face == EnumFacing.UP) {
				return EnumFacing.NORTH;
			} else {

				return face;

			}
		} else {

			return face;
		}

	}

}
