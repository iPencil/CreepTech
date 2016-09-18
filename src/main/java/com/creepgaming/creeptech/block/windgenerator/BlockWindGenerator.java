package com.creepgaming.creeptech.block.windgenerator;

import com.creepgaming.creeptech.Reference;
import com.creepgaming.creeptech.block.BlockBase;
import com.creepgaming.creeptech.helper.block.BlockHelper;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockWindGenerator extends BlockBase implements ITileEntityProvider {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockWindGenerator() {
		super(Material.IRON);
		setUnlocalizedName(Reference.MODID + ":block_wind_generator");
		setRegistryName("block_wind_generator");
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileWindGenerator();
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, BlockHelper.getBlockFacingFromEntity(pos, placer, true)),
				2);

	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

}
