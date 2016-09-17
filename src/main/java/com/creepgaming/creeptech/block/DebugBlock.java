package com.creepgaming.creeptech.block;

import com.creepgaming.creeptech.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class DebugBlock extends BlockBase {

	public DebugBlock() {
		
        super(Material.ROCK);
        setUnlocalizedName(Reference.MODID + ":debug_block");
        setRegistryName("debug_block");
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
	}

}
