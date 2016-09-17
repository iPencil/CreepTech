package com.creepgaming.creeptech;

import org.apache.logging.log4j.Level;

import com.creepgaming.creeptech.proxy.CommonProxy;

import net.minecraftforge.common.config.Configuration;

public class Config {

	private static String CATEGORY_GENERAL = "general";
	private static String CATEGORY_GENERATOR = "generators";

	public static int windGeneratorRF = 5;

	public static void readConfig() {
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
			initGeneratorConfig(cfg);
		} catch (Exception e1) {
			CreepTech.logger.log(Level.ERROR, "Problem loading config file!", e1);
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}

	private static void initGeneralConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General Configuration");
	}

	private static void initGeneratorConfig(Configuration cfg){
		cfg.addCustomCategoryComment(CATEGORY_GENERATOR, "Generator Configuration");
		windGeneratorRF = cfg.getInt("", CATEGORY_GENERATOR, 5, 1, 1000, "Wind Generator RF/t");
	}
	
}
