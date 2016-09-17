package com.creepgaming.creeptech;

import org.apache.logging.log4j.Logger;

import com.creepgaming.creeptech.proxy.CommonProxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, useMetadata = true)
public class CreepTech {

	@SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.SERVERPROXY)
	public static CommonProxy proxy;

	@Mod.Instance
	public static CreepTech instance;

	public static Logger logger;

	public static CreativeTabs creepTechTab;
	
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
	    creepTechTab = new CreativeTabs("creep_tech_tab") {
	        @Override
	        @SideOnly(Side.CLIENT)
	        public Item getTabIconItem() {
	          return Items.CARROT_ON_A_STICK;
	        }
	      };
		
		logger = event.getModLog();
		proxy.preInit(event);		
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

	

	
	
}
