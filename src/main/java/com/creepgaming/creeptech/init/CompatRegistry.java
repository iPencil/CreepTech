package com.creepgaming.creeptech.init;

import com.creepgaming.creeptech.compat.TOPCompatibility;
import com.creepgaming.creeptech.compat.WailaCompatibility;

import net.minecraftforge.fml.common.Loader;

public class CompatRegistry {
	public static void registerCompats() {
		if (Loader.isModLoaded("Waila")) {
			WailaCompatibility.register();
		}

		if (Loader.isModLoaded("theoneprobe")) {
			TOPCompatibility.register();
		}

	}

}
