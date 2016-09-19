package com.creepgaming.creeptech.block.storage.energystorage;

import java.text.NumberFormat;
import java.util.List;

import com.creepgaming.creeptech.Reference;
import com.creepgaming.creeptech.block.BlockBase;
import com.creepgaming.creeptech.compat.TOPInfoProvider;
import com.creepgaming.creeptech.compat.WailaInfoProvider;
import com.creepgaming.creeptech.init.PacketRegistry;
import com.creepgaming.creeptech.network.packets.PacketEnergyStored;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEnergyStorage extends BlockBase implements ITileEntityProvider, WailaInfoProvider, TOPInfoProvider {

	public static int rfStored;

	public BlockEnergyStorage() {

		super(Material.ROCK);
		setUnlocalizedName(Reference.MODID + ":blockEnergyStorage");
		setRegistryName("blockEnergyStorage");
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEnergyStorage();
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		TileEntity te = accessor.getTileEntity();
		if (te instanceof TileEnergyStorage) {
			PacketRegistry.INSTANCE.sendToServer(new PacketEnergyStored(accessor.getPosition()));
			String rfToolTip = NumberFormat.getIntegerInstance().format(rfStored);
			currenttip.add(TextFormatting.GRAY + "Energy: " + rfToolTip + "RF");

		}
		return currenttip;
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world,
			IBlockState blockState, IProbeHitData data) {

		TileEntity te = world.getTileEntity(data.getPos());
		if (te instanceof TileEnergyStorage){
			TileEnergyStorage tile = (TileEnergyStorage) te;
			probeInfo.horizontal(probeInfo.defaultLayoutStyle().borderColor(0xffff0000)).progress(tile.showStoredEnergy(), tile.showMaxEnergy(), probeInfo.defaultProgressStyle().suffix("RF"));
		}
		
	}

}
