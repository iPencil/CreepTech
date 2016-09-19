package com.creepgaming.creeptech.block.storage.tank;

import javax.annotation.Nullable;

import com.creepgaming.creeptech.Reference;
import com.creepgaming.creeptech.block.BlockBase;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTank extends BlockBase implements ITileEntityProvider {

	public BlockTank() {
		super(Material.GLASS);
		setSoundType(SoundType.GLASS);
		setUnlocalizedName(Reference.MODID + ":blockTank");
		setRegistryName("blockTank");
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
	}

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
	
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileTank();
	}

	private IFluidHandler getFluidHandler(IBlockAccess world, BlockPos pos) {

		return getCapability(world.getTileEntity(pos), CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
	}



	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		final IFluidHandler fluidHandler = getFluidHandler(worldIn, pos);
		if(fluidHandler != null){
				FluidUtil.tryEmptyContainer(stack, fluidHandler, Integer.MAX_VALUE, null, true);
		}
		
		
	}

	public static <T> T getCapability(@Nullable ICapabilityProvider provider, Capability<T> capability,
			@Nullable EnumFacing facing) {
		return provider != null && provider.hasCapability(capability, facing)
				? provider.getCapability(capability, facing) : null;
	}

}
