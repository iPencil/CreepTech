package com.creepgaming.creeptech.fluid;

import com.creepgaming.creeptech.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TMFluidRegistry {

	   private static ResourceLocation slurryFluid = new ResourceLocation(Reference.MODID, "blocks/fluidslurry");
	    public static Fluid fluidSlurry = new Fluid("fluid_slurry", slurryFluid, slurryFluid);

	    public static void initFluids(){
	        fluidSlurry.setUnlocalizedName(Reference.MODID + "." + "liquid_crystal");
	        FluidRegistry.registerFluid(fluidSlurry);
	    }
	
	

	    public static boolean isValidLiquidCrystalStack(FluidStack stack){
	        return getFluidFromStack(stack) == fluidSlurry;
	    }

	    public static Fluid getFluidFromStack(FluidStack stack){
	        return stack == null ? null : stack.getFluid();
	    }

	    public static String getFluidName(FluidStack stack){
	        Fluid fluid = getFluidFromStack(stack);
	        return getFluidName(fluid);
	    }

	    public static String getFluidName(Fluid fluid){
	        return fluid == null ? "null" : fluid.getName();
	    }

	    public static int getAmount(FluidStack stack){
	        return stack == null ? 0 : stack.amount;
	    }
	    
}
