package com.creepgaming.creeptech.block.machine.crusher;

import java.util.Arrays;

import javax.annotation.Nullable;

import api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyConnection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileCrusher extends TileEntity implements IEnergyReceiver, IEnergyConnection, ITickable, IInventory{

	public static final int INPUT_SLOTS_COUNT = 1;
	public static final int OUTPUT_SLOTS_COUNT = 1;
	public static final int TOTAL_SLOTS_COUNT =  INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;


	public static final int FIRST_INPUT_SLOT = 0;
	public static final int FIRST_OUTPUT_SLOT = FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT;

	private ItemStack[] itemStacks = new ItemStack[TOTAL_SLOTS_COUNT];
	

	/**The number of ticks the current item has been cooking*/
	private short cookTime;
	/**The number of ticks required to cook an item*/
	private static final short COOK_TIME_FOR_COMPLETION = 200;  // vanilla value is 200 = 10 seconds

	
	public double fractionOfCookTimeComplete()
	{
		double fraction = cookTime / (double)COOK_TIME_FOR_COMPLETION;
		return MathHelper.clamp_double(fraction, 0.0, 1.0);
	}
	
	
	@Override
	public void update() {
		// If there is nothing to smelt or there is no room in the output, reset cookTime and return
		if (canCrush()) {
				cookTime+= 10;


			// If cookTime has reached maxCookTime smelt the item and reset cookTime
			if (cookTime >= COOK_TIME_FOR_COMPLETION) {
				crushItem();
				cookTime = 0;
			}
		}	else {
			cookTime = 0;
		}


	}
	
	private boolean canCrush() {return crushItem(false);}

	/**
	 * Smelt an input item into an output slot, if possible
	 */
	private void crushItem() {crushItem(true);}
	
	
	
	private boolean crushItem(boolean performSmelt)
	{
		Integer firstSuitableInputSlot = null;
		Integer firstSuitableOutputSlot = null;
		ItemStack result = null;

		// finds the first input slot which is smeltable and whose result fits into an output slot (stacking if possible)
		for (int inputSlot = FIRST_INPUT_SLOT; inputSlot < FIRST_INPUT_SLOT + INPUT_SLOTS_COUNT; inputSlot++)	{
			if (itemStacks[inputSlot] != null) {
				result = getCrushResultForItem(itemStacks[inputSlot]);
  			if (result != null) {
					// find the first suitable output slot- either empty, or with identical item that has enough space
					for (int outputSlot = FIRST_OUTPUT_SLOT; outputSlot < FIRST_OUTPUT_SLOT + OUTPUT_SLOTS_COUNT; outputSlot++) {
						ItemStack outputStack = itemStacks[outputSlot];
						if (outputStack == null) {
							firstSuitableInputSlot = inputSlot;
							firstSuitableOutputSlot = outputSlot;
							break;
						}

						if (outputStack.getItem() == result.getItem() && (!outputStack.getHasSubtypes() || outputStack.getMetadata() == outputStack.getMetadata())
										&& ItemStack.areItemStackTagsEqual(outputStack, result)) {
							int combinedSize = itemStacks[outputSlot].stackSize + result.stackSize;
							if (combinedSize <= getInventoryStackLimit() && combinedSize <= itemStacks[outputSlot].getMaxStackSize()) {
								firstSuitableInputSlot = inputSlot;
								firstSuitableOutputSlot = outputSlot;
								break;
							}
						}
					}
					if (firstSuitableInputSlot != null) break;
				}
			}
		}

		if (firstSuitableInputSlot == null) return false;
		if (!performSmelt) return true;

		// alter input and output
		itemStacks[firstSuitableInputSlot].stackSize--;
		if (itemStacks[firstSuitableInputSlot].stackSize <=0) itemStacks[firstSuitableInputSlot] = null;
		if (itemStacks[firstSuitableOutputSlot] == null) {
			itemStacks[firstSuitableOutputSlot] = result.copy(); // Use deep .copy() to avoid altering the recipe
		} else {
			itemStacks[firstSuitableOutputSlot].stackSize += result.stackSize;
		}
		markDirty();
		return true;
	}
	
	
	public static ItemStack getCrushResultForItem(ItemStack stack) { return FurnaceRecipes.instance().getSmeltingResult(stack); }
	
	
	@Override
	public int getSizeInventory() {
		return itemStacks.length;
	}

	// Gets the stack in the given slot
	@Override
	public ItemStack getStackInSlot(int i) {
		return itemStacks[i];
	}
	
	@Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		if (itemStackInSlot == null) return null;

		ItemStack itemStackRemoved;
		if (itemStackInSlot.stackSize <= count) {
			itemStackRemoved = itemStackInSlot;
			setInventorySlotContents(slotIndex, null);
		} else {
			itemStackRemoved = itemStackInSlot.splitStack(count);
			if (itemStackInSlot.stackSize == 0) {
				setInventorySlotContents(slotIndex, null);
			}
		}
		markDirty();
		return itemStackRemoved;
	}

	// overwrites the stack in the given slotIndex with the given stack
	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemstack) {
		itemStacks[slotIndex] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.worldObj.getTileEntity(this.pos) != this) return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}
	
	

		// Return true if the given stack is allowed to be inserted in the given slot
		// Unlike the vanilla furnace, we allow anything to be placed in the fuel slots
		static public boolean isItemValidForInputSlot(ItemStack itemStack)
		{
			return true;
		}

		// Return true if the given stack is allowed to be inserted in the given slot
		// Unlike the vanilla furnace, we allow anything to be placed in the fuel slots
		static public boolean isItemValidForOutputSlot(ItemStack itemStack)
		{
			return false;
		}

		//------------------------------

		// This is where you save any data that you don't want to lose when the tile entity unloads
		// In this case, it saves the state of the furnace (burn time etc) and the itemstacks stored in the fuel, input, and output slots
		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound parentNBTTagCompound)
		{
			super.writeToNBT(parentNBTTagCompound); // The super call is required to save and load the tiles location

//			// Save the stored item stacks

			// to use an analogy with Java, this code generates an array of hashmaps
			// The itemStack in each slot is converted to an NBTTagCompound, which is effectively a hashmap of key->value pairs such
			//   as slot=1, id=2353, count=1, etc
			// Each of these NBTTagCompound are then inserted into NBTTagList, which is similar to an array.
			NBTTagList dataForAllSlots = new NBTTagList();
			for (int i = 0; i < this.itemStacks.length; ++i) {
				if (this.itemStacks[i] != null) {
					NBTTagCompound dataForThisSlot = new NBTTagCompound();
					dataForThisSlot.setByte("Slot", (byte) i);
					this.itemStacks[i].writeToNBT(dataForThisSlot);
					dataForAllSlots.appendTag(dataForThisSlot);
				}
			}
			// the array of hashmaps is then inserted into the parent hashmap for the container
			parentNBTTagCompound.setTag("Items", dataForAllSlots);

			// Save everything else
			parentNBTTagCompound.setShort("CookTime", cookTime);
	    return parentNBTTagCompound;
		}

		// This is where you load the data that you saved in writeToNBT
		@Override
		public void readFromNBT(NBTTagCompound nbtTagCompound)
		{
			super.readFromNBT(nbtTagCompound); // The super call is required to save and load the tiles location
			final byte NBT_TYPE_COMPOUND = 10;       // See NBTBase.createNewByType() for a listing
			NBTTagList dataForAllSlots = nbtTagCompound.getTagList("Items", NBT_TYPE_COMPOUND);

			Arrays.fill(itemStacks, null);           // set all slots to empty
			for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
				NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
				byte slotNumber = dataForOneSlot.getByte("Slot");
				if (slotNumber >= 0 && slotNumber < this.itemStacks.length) {
					this.itemStacks[slotNumber] = ItemStack.loadItemStackFromNBT(dataForOneSlot);
				}
			}

			// Load everything else.  Trim the arrays (or pad with 0) to make sure they have the correct number of elements
			cookTime = nbtTagCompound.getShort("CookTime");
		}
	
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	
	// When the world loads from disk, the server needs to send the TileEntity information to the client
//	//  it uses getUpdatePacket(), getUpdateTag(), onDataPacket(), and handleUpdateTag() to do this
  @Override
  @Nullable
  public SPacketUpdateTileEntity getUpdatePacket()
  {
    NBTTagCompound updateTagDescribingTileEntityState = getUpdateTag();
    final int METADATA = 0;
    return new SPacketUpdateTileEntity(this.pos, METADATA, updateTagDescribingTileEntityState);
  }

  @Override
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    NBTTagCompound updateTagDescribingTileEntityState = pkt.getNbtCompound();
    handleUpdateTag(updateTagDescribingTileEntityState);
  }

  /* Creates a tag containing the TileEntity information, used by vanilla to transmit from server to client
     Warning - although our getUpdatePacket() uses this method, vanilla also calls it directly, so don't remove it.
   */
  @Override
  public NBTTagCompound getUpdateTag()
  {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
    return nbtTagCompound;
  }

  /* Populates this TileEntity with information from the tag, used by vanilla to transmit from server to client
   Warning - although our onDataPacket() uses this method, vanilla also calls it directly, so don't remove it.
 */
  @Override
  public void handleUpdateTag(NBTTagCompound tag)
  {
    this.readFromNBT(tag);
  }
  //------------------------

	// set all slots to empty
	@Override
	public void clear() {
		Arrays.fill(itemStacks, null);
	}

	// will add a key for this container to the lang file so we can name it in the GUI
	@Override
	public String getName() {
		return "container.inventorycrusher.name";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}
	
	@Nullable
	  @Override
	  public ITextComponent getDisplayName() {
			return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
		}

		// Fields are used to send non-inventory information from the server to interested clients
		// The container code caches the fields and sends the client any fields which have changed.
		// The field ID is limited to byte, and the field value is limited to short. (if you use more than this, they get cast down
		//   in the network packets)
		// If you need more than this, or shorts are too small, use a custom packet in your container instead.


	// -----------------------------------------------------------------------------------------------------------
		// The following methods are not needed for this example but are part of IInventory so they must be implemented

		// Unused unless your container specifically uses it.
		// Return true if the given stack is allowed to go in the given slot
		@Override
		public boolean isItemValidForSlot(int slotIndex, ItemStack itemstack) {
			return false;
		}

		/**
		 * This method removes the entire contents of the given slot and returns it.
		 * Used by containers such as crafting tables which return any items in their slots when you close the GUI
		 * @param slotIndex
		 * @return
		 */
		@Override
		public ItemStack removeStackFromSlot(int slotIndex) {
			ItemStack itemStack = getStackInSlot(slotIndex);
			if (itemStack != null) setInventorySlotContents(slotIndex, null);
			return itemStack;
		}

		@Override
		public void openInventory(EntityPlayer player) {}

		@Override
		public void closeInventory(EntityPlayer player) {}

	
	
	
	@Override
	public int getEnergyStored(EnumFacing from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return 0;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}



	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static final byte COOK_FIELD_ID = 0;
	private static final byte NUMBER_OF_FIELDS = 1;
	

	@Override
	public int getField(int id) {
		if (id == COOK_FIELD_ID) return cookTime;
		return 0;
	}


	@Override
	public void setField(int id, int value) {
		if (id == COOK_FIELD_ID) {
			cookTime = (short)value;
		}
	}


	@Override
	public int getFieldCount() {
		return NUMBER_OF_FIELDS;
	}

}
