package com.creepgaming.creeptech.block.machine.crusher.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.creepgaming.creeptech.Reference;
import com.creepgaming.creeptech.block.machine.crusher.ContainerCrusher;
import com.creepgaming.creeptech.block.machine.crusher.TileCrusher;

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
	private static final ResourceLocation texture = new ResourceLocation(Reference.MODID, "textures/gui/machine/guicrusher.png");
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
	final int COOK_BAR_ICON_U = 176;   // texture position of white arrow icon
	final int COOK_BAR_ICON_V = 0;
	final int COOK_BAR_WIDTH = 26;
	final int COOK_BAR_HEIGHT = 17;



	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
		// Bind the image texture
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		// Draw the image
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		// get cook progress as a double between 0 and 1
		double cookProgress = tileEntity.fractionOfCookTimeComplete();
		// draw the cook progress bar
		drawTexturedModalRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_ICON_U, COOK_BAR_ICON_V,
						              (int)(cookProgress * COOK_BAR_WIDTH), COOK_BAR_HEIGHT);

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		final int LABEL_XPOS = 5;
		final int LABEL_YPOS = 5;
		fontRendererObj.drawString(tileEntity.getDisplayName().getUnformattedText(), LABEL_XPOS, LABEL_YPOS, Color.darkGray.getRGB());

		List<String> hoveringText = new ArrayList<String>();

		// If the mouse is over the progress bar add the progress bar hovering text
		if (isInRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_WIDTH, COOK_BAR_HEIGHT, mouseX, mouseY)){
			hoveringText.add("Progress:");
			int cookPercentage =(int)(tileEntity.fractionOfCookTimeComplete() * 100);
			hoveringText.add(cookPercentage + "%");
		}
		
		this.drawHoveringText(hoveringText,  mouseX-guiLeft , mouseY - guiTop);
//		// You must re bind the texture and reset the colour if you still need to use it after drawing a string
//		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
//		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

	}

	// Returns true if the given x,y coordinates are within the given rectangle
	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY){
		return ((mouseX >= x && mouseX <= x+xSize) && (mouseY >= y && mouseY <= y+ySize));
	}
	
}
