package com.creepgaming.creeptech.block.machine.crusher.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.creepgaming.creeptech.Reference;
import com.creepgaming.creeptech.block.machine.crusher.ContainerCrusher;
import com.creepgaming.creeptech.block.machine.crusher.TileCrusher;
import com.creepgaming.creeptech.helper.gui.GuiHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCrusher extends GuiContainer {

	// This is the resource location for the background image
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID,
			"textures/gui/machine/guicrusher.png");

	private static final ResourceLocation textureWater = new ResourceLocation(Reference.MODID,
			"textures/gui/machine/water.png");
	
	private TileCrusher tileEntity;

	public GuiCrusher(InventoryPlayer invPlayer, TileCrusher tileCrusher) {
		super(new ContainerCrusher(invPlayer, tileCrusher));

		// Set the width and height of the gui
		xSize = 176;
		ySize = 166;

		this.tileEntity = tileCrusher;
	}

	// some [x,y] coordinates of graphical elements
	final int COOK_BAR_XPOS = 76;
	final int COOK_BAR_YPOS = 35;
	final int COOK_BAR_ICON_U = 176; // texture position of white arrow icon
	final int COOK_BAR_ICON_V = 0;
	final int COOK_BAR_WIDTH = 26;
	final int COOK_BAR_HEIGHT = 17;

	final int TANK_INPUT_EMPTY_POS_X = 75;
	final int TANK_INPUT_EMPTY_POS_Y = 7;
	final int TANK_OUTPUT_EMPTY_POS_X = 115;
	final int TANK_OUTPUT_EMPTY_POS_Y = 30;
	
	
	final int TANK_EMPTY_ICON_X = 202;
	final int TANK_EMPTY_ICON_Y = 0;
	
	final int TANK_FULL_ICON_X = 219;
	final int TANK_FULL_ICON_Y = 25;
	
	final int TANK_TRANSPARENT_OVERLAY_X = 236;
	final int TANK_TRANSPARENT_OVERLAY_Y = 0;
	
	final int TANK_EMPTY_WIDTH = 17;
	final int TANK_EMPTY_HEIGHT = 26;

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {

		// Bind the image texture
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		// Draw the image
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		// get cook progress as a double between 0 and 1
		double cookProgress = tileEntity.fractionOfCookTimeComplete();
		double inputWaterLevel = tileEntity.fractionOfInputWater();
		// draw the cook progress bar
		drawTexturedModalRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_ICON_U, COOK_BAR_ICON_V,
				(int) (cookProgress * COOK_BAR_WIDTH), COOK_BAR_HEIGHT);

		
		//draw empty input tank as background so its not transparent
		
		drawTexturedModalRect(guiLeft + TANK_INPUT_EMPTY_POS_X, guiTop + TANK_INPUT_EMPTY_POS_Y, TANK_EMPTY_ICON_X,
				TANK_EMPTY_ICON_Y, TANK_EMPTY_WIDTH, TANK_EMPTY_HEIGHT);

		//draw water over it, from bottom to top, only drawing % to input water level
		
		
		
		drawTexturedModalRect(guiLeft + TANK_INPUT_EMPTY_POS_X,
				 (int) (guiTop +  TANK_INPUT_EMPTY_POS_Y +1 + (TANK_EMPTY_HEIGHT * (1- inputWaterLevel))), TANK_FULL_ICON_X,
				TANK_FULL_ICON_Y - (int)(TANK_EMPTY_HEIGHT *  inputWaterLevel), TANK_EMPTY_WIDTH,
				TANK_EMPTY_HEIGHT);
		
		
	
		//draw transparent tank overlay
		
		drawTexturedModalRect(guiLeft + TANK_INPUT_EMPTY_POS_X,
				guiTop + TANK_INPUT_EMPTY_POS_Y , TANK_TRANSPARENT_OVERLAY_X,
				TANK_TRANSPARENT_OVERLAY_Y , TANK_EMPTY_WIDTH,
				TANK_EMPTY_HEIGHT);
		
		//draw output tank
		

		drawTexturedModalRect(guiLeft + TANK_OUTPUT_EMPTY_POS_X, guiTop + TANK_OUTPUT_EMPTY_POS_Y, TANK_EMPTY_ICON_X,
				TANK_EMPTY_ICON_Y, TANK_EMPTY_WIDTH, TANK_EMPTY_HEIGHT);

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		final int LABEL_XPOS = 5;
		final int LABEL_YPOS = 5;
		fontRendererObj.drawString(tileEntity.getDisplayName().getUnformattedText(), LABEL_XPOS, LABEL_YPOS,
				Color.darkGray.getRGB());

		List<String> hoveringText = new ArrayList<String>();

		// If the mouse is over the progress bar add the progress bar hovering
		// text
		if (isInRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_WIDTH, COOK_BAR_HEIGHT, mouseX,
				mouseY)) {
			hoveringText.add("Progress:");
			int cookPercentage = (int) (tileEntity.fractionOfCookTimeComplete() * 100);
			hoveringText.add(cookPercentage + "%");
		}
		if (isInRect(guiLeft + TANK_INPUT_EMPTY_POS_X, guiTop + TANK_INPUT_EMPTY_POS_Y, TANK_EMPTY_WIDTH,
				TANK_EMPTY_HEIGHT, mouseX, mouseY)) {
			hoveringText.add("Water: " + tileEntity.inputWater + "mb");
		}

		this.drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop);
		// // You must re bind the texture and reset the colour if you still
		// need to use it after drawing a string
		// Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		// GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

	}

	// Returns true if the given x,y coordinates are within the given rectangle
	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
		return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
	}

}
